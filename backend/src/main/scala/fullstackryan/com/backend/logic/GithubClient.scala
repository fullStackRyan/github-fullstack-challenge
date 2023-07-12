package fullstackryan.com.backend.logic

import cats.effect.Concurrent
import cats.implicits._
import fullstackryan.com.backend.config.Config
import fullstackryan.com.backend.model.{ApiResponseNode, Repository, RepositoryError}
import io.circe.Json
import org.http4s.Method._
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.headers.Authorization
import org.http4s.{AuthScheme, Credentials, Request}


trait GithubClient[F[_]] {
  def searchRepoByName(name: String): F[List[Repository]]
}

object GithubClient {
  def apply[F[_]](implicit ev: GithubClient[F]): GithubClient[F] = ev

  def impl[F[_]: Concurrent](client: Client[F], config: Config): GithubClient[F] = new GithubClient[F] {
    def searchRepoByName(name: String): F[List[Repository]] = {
      val query = Json.obj(
        "query" -> Json.fromString(
          s"""
             |{
             |  search(query: "$name in:name", type: REPOSITORY, first: 100) {
             |    edges {
             |      node {
             |        ... on Repository {
             |          name
             |          owner {
             |            login
             |          }
             |          openGraphImageUrl
             |          stargazerCount
             |          watchers {
             |            totalCount
             |          }
             |        }
             |      }
             |    }
             |  }
             |}
             |""".stripMargin)
      )


      val request = Request[F](POST, config.github.githubUri)
        .withHeaders(Authorization(Credentials.Token(AuthScheme.Bearer, config.github.apiKey.value)))
        .withEntity(query)

      client.expect[Json](request)
        .map(_.hcursor.downField("data").downField("search").downField("edges").as[List[ApiResponseNode]].getOrElse(List.empty))
        .map(apiResponse => Repository.fromApiResponse(apiResponse))
        .adaptError { case t => RepositoryError(t) }
    }
  }
}

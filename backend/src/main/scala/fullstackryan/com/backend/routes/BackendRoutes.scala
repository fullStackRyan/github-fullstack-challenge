package fullstackryan.com.backend.routes

import cats.effect.Sync
import cats.implicits._
import fullstackryan.com.backend.logic.GithubClient
import fullstackryan.com.backend.model.RepoNameQueryParamMatcher
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object BackendRoutes {

  def githubRoutes[F[_]: Sync](githubClient: GithubClient[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "github" :? RepoNameQueryParamMatcher(name) =>
        for {
          results <- githubClient.searchRepoByName(name)
          resp <- Ok(results)
        } yield resp
    }
  }
}
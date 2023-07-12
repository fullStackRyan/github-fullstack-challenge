package fullstackryan.com.backend.logic

import cats.effect.IO
import ciris.Secret
import fullstackryan.com.backend.config.{Config, GithubConfig}
import fullstackryan.com.backend.model._
import io.circe.Json
import munit.CatsEffectSuite
import org.http4s.{HttpApp, Method, Request, Response, Status}
import org.http4s.client.Client
import org.http4s.implicits._
import io.circe.syntax._
import org.http4s.circe._

import scala.concurrent.duration.DurationInt

class GithubClientSpec extends CatsEffectSuite {

  private[this] val config = Config(
    "local",
    GithubConfig(
      uri"https://api.github.com/graphql",
      3,
      1.second,
      Secret("test-key")
    )
  )

  val mockApiResponse: ApiResponseNode = ApiResponseNode(
    ApiResponse(
      "example",
      ApiResponseOwner("test-owner"),
      "https://image.example.com",
      100,
      ApiResponseWatchers(10)
    )
  )

  val mockClient: Client[IO] = Client.fromHttpApp(HttpApp[IO] { case req: Request[IO] =>
    req.method match {
      case Method.POST if req.uri == config.github.githubUri =>
        val response = Json.obj(
          "data" -> Json.obj(
            "search" -> Json.obj(
              "edges" -> Json.fromValues(List.fill(5)(mockApiResponse).map(_.asJson))
            )
          )
        )
        IO.pure(Response[IO](Status.Ok).withEntity(response))

      case _ =>
        IO.pure(Response[IO](Status.NotFound))
    }
  })

  test("GithubClient returns status code 200") {
    val client = GithubClient.impl[IO](mockClient, config)
    assertIO(client.searchRepoByName("example").attempt.map(_.isRight), true)
  }

  test("GithubClient searchRepoByName should return non-empty list") {
    val client = GithubClient.impl[IO](mockClient, config)
    assertIO(client.searchRepoByName("example").map(_.nonEmpty), true)
  }

  test("GithubClient searchRepoByName should return Repository objects") {
    val client = GithubClient.impl[IO](mockClient, config)
    assertIO(client.searchRepoByName("example").map(_.forall(_.isInstanceOf[Repository])), true)
  }

  test("GithubClient searchRepoByName should return correct Repository objects") {
    val client = GithubClient.impl[IO](mockClient, config)
    val expectedRepo = Repository(
      mockApiResponse.node.name,
      mockApiResponse.node.owner.login,
      mockApiResponse.node.openGraphImageUrl,
      mockApiResponse.node.stargazerCount,
      mockApiResponse.node.watchers.totalCount
    )

    assertIO(client.searchRepoByName("example").map(_.headOption.contains(expectedRepo)), true)
  }
}

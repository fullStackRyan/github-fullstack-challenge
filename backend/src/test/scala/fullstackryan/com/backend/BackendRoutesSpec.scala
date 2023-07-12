package fullstackryan.com.backend

import cats.effect.IO
import fullstackryan.com.backend.logic.GithubClient
import fullstackryan.com.backend.model._
import fullstackryan.com.backend.routes.BackendRoutes
import munit.CatsEffectSuite
import org.http4s.implicits._
import org.http4s.{Method, Request}
import org.http4s.circe.CirceEntityCodec._

class BackendRoutesSpec extends CatsEffectSuite {
  private[this] val mockGithubClient = new GithubClient[IO] {
    override def searchRepoByName(name: String): IO[List[Repository]] = {
      if(name == "example") IO.pure(List(Repository("example", "test-owner", "https://image.example.com", 100, 10)))
      else IO.pure(List.empty)
    }
  }

  private[this] val routes = BackendRoutes.githubRoutes[IO](mockGithubClient).orNotFound

  test("BackendRoutes should return 200 OK on valid repo name") {
    val request = Request[IO](Method.GET, uri"/github".withQueryParam("name", "example"))
    assertIO(routes.run(request).map(_.status.isSuccess), true)
  }

  test("BackendRoutes should return empty list on invalid repo name") {
    val request = Request[IO](Method.GET, uri"/github".withQueryParam("name", "invalid"))
    assertIO(routes.run(request).flatMap(_.as[List[Repository]]), List.empty)
  }

  test("BackendRoutes should return correct list on valid repo name") {
    val expectedRepo = Repository("example", "test-owner", "https://image.example.com", 100, 10)
    val request = Request[IO](Method.GET, uri"/github".withQueryParam("name", "example"))
    assertIO(routes.run(request).flatMap(_.as[List[Repository]]), List(expectedRepo))
  }
}


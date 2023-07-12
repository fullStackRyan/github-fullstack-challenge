package fullstackryan.com.backend

import cats.effect.{Async, Resource}
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger
import fullstackryan.com.backend.config.Config
import fullstackryan.com.backend.logic.GithubClient
import fullstackryan.com.backend.routes.BackendRoutes
import org.http4s.server.Server

object BackendServer {

  def run[F[_] : Async]: F[Unit] = {
    val program: Resource[F, Server] = for {
      client <- EmberClientBuilder.default[F].build
      config <- Resource.eval(Config.readFromEnvironment[F])
      githubAlg = GithubClient.impl[F](client, config)

      httpApp = (
          BackendRoutes.githubRoutes[F](githubAlg)
        ).orNotFound

      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      server <-
        EmberServerBuilder.default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
    } yield server

    program.use { _ =>
      Async[F].never.void
    }.handleErrorWith { e =>
      Async[F].delay(println(s"Server failed with error: ${e.getMessage}")).as(())
    }
  }
}

package fullstackryan.com.backend.config

import cats.effect.Async
import ciris._
import org.http4s.Uri
import org.http4s.implicits.http4sLiteralsSyntax
import scala.concurrent.duration.{DurationInt, FiniteDuration}

final case class Config(
                         env: String,
                         github: GithubConfig,
                       )



final case class GithubConfig(
                               githubUri: Uri,
                               retriesPerRequest: Int,
                               initialRetryDelay: FiniteDuration,
                               apiKey: Secret[String]
                             )

object Config {

  def readFromEnvironment[F[_]: Async]: F[Config] =
    (
      for {
        environment <- env("ENV")
        apiKey      <- env("GITHUB_API_KEY").secret
      } yield
        Config(
          environment,
          GithubConfig(
            uri"https://api.github.com/graphql",
            retriesPerRequest = 3,
            initialRetryDelay = 1.seconds,
            apiKey
          )
        )
      ).load[F]

}
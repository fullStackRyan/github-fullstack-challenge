package fullstackryan.com.backend.model

import io.circe.{Encoder, Decoder}
import io.circe.generic.semiauto._
import org.http4s._
import org.http4s.circe._
import cats.effect.Concurrent

final case class Repository(repoName: String, authorName: String, image: String, stars: Int, watchers: Int)

object Repository {
  implicit val repositoryDecoder: Decoder[Repository] = deriveDecoder[Repository]

  implicit def repositoryEntityDecoder[F[_] : Concurrent]: EntityDecoder[F, Repository] =
    jsonOf

  implicit val repositoryEncoder: Encoder[Repository] = deriveEncoder[Repository]

  implicit def repositoryEntityEncoder[F[_]]: EntityEncoder[F, Repository] =
    jsonEncoderOf

  implicit def repositoryListEntityEncoder[F[_]]: EntityEncoder[F, List[Repository]] =
    jsonEncoderOf

  def fromApiResponse(apiResponse: List[ApiResponseNode]): List[Repository] = {
    apiResponse.map(node => Repository(
      node.node.name,
      node.node.owner.login,
      node.node.openGraphImageUrl,
      node.node.stargazerCount,
      node.node.watchers.totalCount
    ))
  }
}

final case class ApiResponseNode(node: ApiResponse)

object ApiResponseNode {
  implicit val apiResponseNodeDecoder: Decoder[ApiResponseNode] = deriveDecoder[ApiResponseNode]
  implicit val apiResponseNodeEncoder: Encoder[ApiResponseNode] = deriveEncoder[ApiResponseNode]
  implicit val apiResponseNodeListDecoder: Decoder[List[ApiResponseNode]] = Decoder.decodeList[ApiResponseNode]
}




final case class ApiResponse(name: String, owner: ApiResponseOwner, openGraphImageUrl: String, stargazerCount: Int, watchers: ApiResponseWatchers)

object ApiResponse {
  implicit val apiResponseDecoder: Decoder[ApiResponse] = deriveDecoder[ApiResponse]
  implicit val apiResponseEncoder: Encoder[ApiResponse] = deriveEncoder[ApiResponse]
}


final case class ApiResponseOwner(login: String)

object ApiResponseOwner {
  implicit val apiResponseOwnerDecoder: Decoder[ApiResponseOwner] = deriveDecoder[ApiResponseOwner]
  implicit val apiResponseOwnerEncoder: Encoder[ApiResponseOwner] = deriveEncoder[ApiResponseOwner]
}

final case class ApiResponseWatchers(totalCount: Int)

object ApiResponseWatchers {
  implicit val apiResponseWatchersDecoder: Decoder[ApiResponseWatchers] = deriveDecoder[ApiResponseWatchers]
  implicit val apiResponseWatchersEncoder: Encoder[ApiResponseWatchers] = deriveEncoder[ApiResponseWatchers]
}
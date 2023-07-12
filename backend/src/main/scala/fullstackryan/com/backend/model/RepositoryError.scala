package fullstackryan.com.backend.model

case class RepositoryError(e: Throwable) extends RuntimeException

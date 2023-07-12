package fullstackryan.com.backend.model

import org.http4s.dsl.impl.QueryParamDecoderMatcher

object RepoNameQueryParamMatcher extends QueryParamDecoderMatcher[String]("name")
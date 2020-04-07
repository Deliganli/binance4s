package com.deliganli.binance4s.rest.request

import java.time.ZonedDateTime

import com.deliganli.binance4s.rest.request.QueryParams.Formatters.dateTimeEncoder
import org.http4s.Query

case class Period(start: Option[ZonedDateTime], end: Option[ZonedDateTime])

object Period {
  val Empty = Period(None, None)

  implicit val query: HasQuery[Period] = HasQuery.create[Period] { period =>
    Query.empty
      .withOptionQueryParam(QueryParams.Keys.startTime, period.start)
      .withOptionQueryParam(QueryParams.Keys.endTime, period.end)
  }
}

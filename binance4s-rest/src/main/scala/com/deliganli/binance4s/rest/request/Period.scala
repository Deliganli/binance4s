package com.deliganli.binance4s.rest.request

import com.deliganli.binance4s.rest.request.QueryParams.Formatters.dateTimeEncoder
import org.http4s.Query
import org.joda.time.DateTime

case class Period(start: Option[DateTime], end: Option[DateTime])

object Period {
  val Empty = Period(None, None)

  implicit val periodQuery: HasQuery[Period] = HasQuery.create[Period] { period =>
    Query.empty
      .withOptionQueryParam(QueryParams.Keys.startTime, period.start)
      .withOptionQueryParam(QueryParams.Keys.endTime, period.end)
  }
}

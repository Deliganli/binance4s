package com.deliganli.binance4s.rest.request

import org.http4s.Query

sealed trait OCOQuery

object OCOQuery {
  case class FromIdQuery(value: Long)    extends OCOQuery
  case class PeriodQuery(period: Period) extends OCOQuery

  implicit val query: HasQuery[OCOQuery] = HasQuery.create[OCOQuery] {
    case FromIdQuery(id)     => Query(QueryParams.Keys.fromId -> Some(id.toString))
    case PeriodQuery(period) => Period.query.query(period)
  }
}

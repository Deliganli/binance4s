package com.deliganli.binance4s.rest.request
import org.http4s.Query

sealed trait OrderId

object OrderId {
  case class ExchangeOrderId(value: Long) extends OrderId
  case class ClientOrderId(value: String) extends OrderId

  implicit val query: HasQuery[OrderId] = HasQuery.create[OrderId] {
    case ExchangeOrderId(id)         => Query(QueryParams.Keys.orderId          -> Some(id.toString))
    case ClientOrderId(clientSideId) => Query(QueryParams.Keys.newClientOrderId -> Some(clientSideId))
  }
}

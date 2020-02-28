package com.deliganli.binance4s.rest.request
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import org.http4s.Query

sealed trait OrderListId

object OrderListId {
  case class ExchangeOrderId(value: Long) extends OrderListId
  case class ClientOrderId(value: String) extends OrderListId

  implicit val query: HasQuery[OrderListId] = {
    case ExchangeOrderId(value) => Query.empty.withQueryParam(Keys.orderListId, value)
    case ClientOrderId(value)   => Query.empty.withQueryParam(Keys.listClientOrderId, value)
  }
}

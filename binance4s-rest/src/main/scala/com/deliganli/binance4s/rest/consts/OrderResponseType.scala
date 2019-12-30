package com.deliganli.binance4s.rest.consts

import com.deliganli.binance4s.rest.request.{HasQuery, QueryParams}
import com.deliganli.binance4s.rest.response.account.NewOrderResponse
import com.deliganli.binance4s.rest.response.account.NewOrderResponse.{OrderAck, OrderFull, OrderResult}
import enumeratum.EnumEntry.Uppercase
import enumeratum._
import org.http4s.Query

sealed trait OrderResponseType[+T <: NewOrderResponse] extends EnumEntry

object OrderResponseType extends Enum[OrderResponseType[NewOrderResponse]] {
  val values = findValues

  case object Ack    extends OrderResponseType[OrderAck] with Uppercase
  case object Result extends OrderResponseType[OrderResult] with Uppercase
  case object Full   extends OrderResponseType[OrderFull] with Uppercase

  implicit def responseType[T <: NewOrderResponse]: HasQuery[OrderResponseType[T]] =
    (v: OrderResponseType[T]) => Query.empty.withQueryParam(QueryParams.Keys.newOrderRespType, v.entryName)
}

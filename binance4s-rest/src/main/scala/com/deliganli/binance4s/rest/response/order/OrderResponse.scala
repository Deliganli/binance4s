package com.deliganli.binance4s.rest.response.order

import com.deliganli.binance4s.common.consts.{OrderSide, OrderStatus, OrderType, TimeInForce}
import com.deliganli.binance4s.rest.request.QueryParams
import com.deliganli.binance4s.rest.response.order.OrderResponse.OrderFull.{Fill, OnlyResult}
import io.circe.{Decoder, HCursor}
import org.http4s.Query

sealed trait OrderResponseQuery[T] {
  def query: Query
}

object OrderResponseQuery {
  def apply[T](implicit ev: OrderResponseQuery[T]): OrderResponseQuery[T] = ev

  implicit val unit = new OrderResponseQuery[Unit] {
    override def query: Query = Query.empty
  }
}

sealed trait OrderResponse

object OrderResponse {

  case class OrderAck(
    symbol: String,
    orderId: Long,
    clientOrderId: String,
    transactTime: Long)
      extends OrderResponse

  object OrderAck {

    implicit val orderAckDecoder: Decoder[OrderAck] = Decoder.forProduct4(
      "symbol",
      "orderId",
      "clientOrderId",
      "transactTime"
    )(OrderAck.apply)

    implicit val query: OrderResponseQuery[OrderAck] = new OrderResponseQuery[OrderAck] {
      override def query: Query = Query.empty.withQueryParam(QueryParams.Keys.newOrderRespType, "OrderAck")
    }
  }

  case class OrderResult(ack: OrderAck, result: OnlyResult) extends OrderResponse

  object OrderResult {

    implicit val orderResultDecoder: Decoder[OrderResult] = (c: HCursor) => {
      for {
        ack    <- OrderAck.orderAckDecoder.apply(c)
        result <- OnlyResult.resultDecoder.apply(c)
      } yield {
        new OrderResult(ack, result)
      }
    }

    implicit val query: OrderResponseQuery[OrderResult] = new OrderResponseQuery[OrderResult] {
      override def query: Query = Query.empty.withQueryParam(QueryParams.Keys.newOrderRespType, "OrderResult")
    }
  }

  case class OrderFull(ack: OrderAck, result: OnlyResult, fills: Seq[Fill]) extends OrderResponse

  object OrderFull {

    implicit val query: OrderResponseQuery[OrderFull] = new OrderResponseQuery[OrderFull] {
      override def query: Query = Query.empty.withQueryParam(QueryParams.Keys.newOrderRespType, "OrderFull")
    }

    implicit val orderFullDecoder: Decoder[OrderFull] = (c: HCursor) => {
      for {
        ack    <- OrderAck.orderAckDecoder.apply(c)
        result <- OnlyResult.resultDecoder.apply(c)
        fills <- c
          .downField("fills")
          .success
          .map(_.as[Seq[Fill]])
          .getOrElse(Right(Seq.empty[Fill]))
      } yield {
        new OrderFull(ack, result, fills)
      }
    }

    case class OnlyResult(
      orderListId: Long,
      price: BigDecimal,
      origQty: BigDecimal,
      executedQty: BigDecimal,
      cummulativeQuoteQty: BigDecimal,
      status: OrderStatus,
      timeInForce: TimeInForce,
      orderType: OrderType,
      side: OrderSide)

    object OnlyResult {

      implicit val resultDecoder: Decoder[OnlyResult] = Decoder.forProduct9(
        "orderListId",
        "price",
        "origQty",
        "executedQty",
        "cummulativeQuoteQty",
        "status",
        "timeInForce",
        "orderType",
        "side"
      )(OnlyResult.apply)
    }

    case class Fill(
      price: BigDecimal,
      qty: BigDecimal,
      commission: BigDecimal,
      commissionAsset: String)

    object Fill {

      implicit val fillDecoder: Decoder[Fill] = Decoder.forProduct4(
        "price",
        "qty",
        "commission",
        "commissionAsset"
      )(Fill.apply)
    }
  }

}

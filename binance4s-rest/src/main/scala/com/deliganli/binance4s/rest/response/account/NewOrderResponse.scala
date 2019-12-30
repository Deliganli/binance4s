package com.deliganli.binance4s.rest.response.account

import com.deliganli.binance4s.common.consts.{OrderSide, OrderStatus, OrderType, TimeInForce}
import com.deliganli.binance4s.rest.response.account.NewOrderResponse.OrderFull.{Fill, OnlyResult}
import io.circe.{Decoder, HCursor}

sealed trait NewOrderResponse

object NewOrderResponse {

  case class OrderAck(
    symbol: String,
    orderId: Long,
    clientOrderId: String,
    transactTime: Long
  ) extends NewOrderResponse

  object OrderAck {
    implicit val orderAckDecoder: Decoder[OrderAck] = Decoder.forProduct4(
      "symbol",
      "orderId",
      "clientOrderId",
      "transactTime"
    )(OrderAck.apply)
  }

  case class OrderResult(
    ack: OrderAck,
    result: OnlyResult
  ) extends NewOrderResponse

  object OrderResult {
    implicit val orderResultDecoder: Decoder[OrderResult] = (c: HCursor) => {
      for {
        ack    <- OrderAck.orderAckDecoder.apply(c)
        result <- OnlyResult.resultDecoder.apply(c)
      } yield {
        new OrderResult(ack, result)
      }
    }
  }

  case class OrderFull(
    ack: OrderAck,
    result: OnlyResult,
    fills: Seq[Fill]
  ) extends NewOrderResponse

  object OrderFull {

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
      price: BigDecimal,
      origQty: BigDecimal,
      executedQty: BigDecimal,
      cummulativeQuoteQty: BigDecimal,
      status: OrderStatus,
      timeInForce: TimeInForce,
      orderType: OrderType,
      side: OrderSide
    )

    object OnlyResult {
      implicit val resultDecoder: Decoder[OnlyResult] = Decoder.forProduct8(
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
      commissionAsset: String
    )

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

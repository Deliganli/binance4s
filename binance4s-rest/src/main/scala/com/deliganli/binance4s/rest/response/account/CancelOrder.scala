package com.deliganli.binance4s.rest.response.account

import com.deliganli.binance4s.common.consts.{OrderSide, OrderStatus, OrderType, TimeInForce}
import io.circe.Decoder

case class CancelOrder(
  symbol: String,
  orderId: Long,
  origClientOrderId: String,
  clientOrderId: String,
  transactTime: Long,
  price: BigDecimal,
  origQty: BigDecimal,
  executedQty: BigDecimal,
  cummulativeQuoteQty: BigDecimal,
  status: OrderStatus,
  timeInForce: TimeInForce,
  orderType: OrderType,
  side: OrderSide
)

object CancelOrder {
  implicit val cancelOrderDecoder = Decoder.forProduct13(
    "symbol",
    "orderId",
    "origClientOrderId",
    "clientOrderId",
    "transactTime",
    "price",
    "origQty",
    "executedQty",
    "cummulativeQuoteQty",
    "status",
    "timeInForce",
    "type",
    "side"
  )(CancelOrder.apply)
}

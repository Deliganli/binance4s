package com.deliganli.binance4s.rest.response.order

import com.deliganli.binance4s.common.consts.{OrderSide, OrderStatus, OrderType, TimeInForce}
import io.circe.Decoder
import org.joda.time.Instant

case class Order(
  symbol: String,
  orderId: Long,
  orderListId: Long,
  clientOrderId: String,
  price: BigDecimal,
  origQty: BigDecimal,
  executedQty: BigDecimal,
  cumulativeQuoteQty: BigDecimal,
  status: OrderStatus,
  timeInForce: TimeInForce,
  orderType: OrderType,
  side: OrderSide,
  stopPrice: BigDecimal,
  icebergQty: BigDecimal,
  time: Instant,
  updateTime: Instant,
  isWorking: Boolean
)

object Order {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
  implicit val d: Decoder[Order] = Decoder.forProduct17(
    "symbol",
    "orderId",
    "orderListId",
    "clientOrderId",
    "price",
    "origQty",
    "executedQty",
    "cummulativeQuoteQty",
    "status",
    "timeInForce",
    "type",
    "side",
    "stopPrice",
    "icebergQty",
    "time",
    "updateTime",
    "isWorking"
  )(Order.apply)
}

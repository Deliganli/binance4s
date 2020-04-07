package com.deliganli.binance4s.rest.response.order

import com.deliganli.binance4s.common.consts._
import com.deliganli.binance4s.rest.response.order.OCOOrderAck.Report
import io.circe.Decoder
import java.time.Instant

case class OCOOrderAck(
  orderListId: Long,
  contingency: Contingency,
  listStatus: OCOStatus,
  listOrderStatus: OCOOrderStatus,
  listClientOrderId: String,
  transactionTime: Instant,
  symbol: String,
  orders: Seq[OCODetail],
  orderReports: Seq[Report])

object OCOOrderAck {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  case class Report(
    symbol: String,
    orderId: Long,
    orderListId: Long,
    clientOrderId: String,
    transactTime: Instant,
    price: BigDecimal,
    origQty: BigDecimal,
    executedQty: BigDecimal,
    cummulativeQuoteQty: BigDecimal,
    status: OrderStatus,
    timeInForce: TimeInForce,
    orderType: OrderType,
    side: OrderSide,
    stopPrice: Option[BigDecimal])

  object Report {
    import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

    implicit val decoder: Decoder[Report] = Decoder.forProduct14(
      "symbol",
      "orderId",
      "orderListId",
      "clientOrderId",
      "transactTime",
      "price",
      "origQty",
      "executedQty",
      "cummulativeQuoteQty",
      "status",
      "timeInForce",
      "type",
      "side",
      "stopPrice"
    )(Report.apply)
  }

  implicit val decoder: Decoder[OCOOrderAck] = Decoder.forProduct9(
    "orderListId",
    "contingencyType",
    "listStatusType",
    "listOrderStatus",
    "listClientOrderId",
    "transactionTime",
    "symbol",
    "orders",
    "orderReports"
  )(OCOOrderAck.apply)
}

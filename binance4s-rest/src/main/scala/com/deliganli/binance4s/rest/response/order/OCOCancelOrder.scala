package com.deliganli.binance4s.rest.response.order

import com.deliganli.binance4s.common.consts._
import com.deliganli.binance4s.rest.response.order.OCOCancelOrder.Report
import io.circe.Decoder
import org.joda.time.Instant

case class OCOCancelOrder(
  orderListId: String,
  contingency: Contingency,
  listStatus: OCOStatus,
  listOrderStatus: OCOOrderStatus,
  listClientOrderId: String,
  transactionTime: Instant,
  symbol: String,
  orders: Seq[OCODetail],
  orderReports: Seq[Report])

object OCOCancelOrder {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val decoder: Decoder[OCOCancelOrder] = Decoder.forProduct9(
    "orderListId",
    "contingencyType",
    "listStatusType",
    "listOrderStatus",
    "listClientOrderId",
    "transactionTime",
    "symbol",
    "orders",
    "orderReports"
  )(OCOCancelOrder.apply)

  case class Report(
    symbol: String,
    orderId: Long,
    orderListId: Long,
    clientOrderId: Option[String],
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
      "orderType",
      "side",
      "stopPrice"
    )(Report.apply)
  }
}

package com.deliganli.binance4s.rest.response.order

import com.deliganli.binance4s.common.consts.{Contingency, OCOOrderStatus, OCOStatus}
import io.circe.Decoder
import java.time.Instant

case class OCOOrder(
  orderListId: Long,
  contingency: Contingency,
  listStatus: OCOStatus,
  listOrderStatus: OCOOrderStatus,
  listClientOrderId: String,
  transactionTime: Instant,
  symbol: String,
  orders: Seq[OCODetail])

object OCOOrder {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val decoder: Decoder[OCOOrder] = Decoder.forProduct8(
    "orderListId",
    "contingencyType",
    "listStatusType",
    "listOrderStatus",
    "listClientOrderId",
    "transactionTime",
    "symbol",
    "orders"
  )(OCOOrder.apply)
}

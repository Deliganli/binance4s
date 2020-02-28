package com.deliganli.binance4s.rest.response.order

import io.circe.Decoder

case class OCODetail(symbol: String, orderId: Long, clientOrderId: Option[String])

object OCODetail {

  implicit val decoder: Decoder[OCODetail] = Decoder.forProduct3(
    "symbol",
    "orderId",
    "clientOrderId"
  )(OCODetail.apply)
}

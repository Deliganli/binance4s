package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class Trade(
  id: Long,
  price: BigDecimal,
  quantity: BigDecimal,
  quoteQuantity: BigDecimal,
  time: Long,
  isBuyerMaker: Boolean,
  isBestMatch: Boolean)

object Trade {

  implicit val tradeDecoder: Decoder[Trade] = Decoder.forProduct7(
    "id",
    "price",
    "qty",
    "quoteQty",
    "time",
    "isBuyerMaker",
    "isBestMatch"
  )(Trade.apply)
}

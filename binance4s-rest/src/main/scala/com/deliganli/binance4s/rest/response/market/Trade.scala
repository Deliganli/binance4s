package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class Trade(
  id: Long,
  price: BigDecimal,
  quantity: BigDecimal,
  time: Long,
  isBuyerMaker: Boolean,
  isBestMatch: Boolean
)

object Trade {
  implicit val tradeDecoder: Decoder[Trade] = Decoder.forProduct6(
    "id",
    "price",
    "qty",
    "time",
    "isBuyerMaker",
    "isBestMatch"
  )(Trade.apply)
}

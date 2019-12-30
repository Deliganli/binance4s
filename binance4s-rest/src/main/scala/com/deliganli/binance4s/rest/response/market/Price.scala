package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class Price(
  symbol: String,
  price: BigDecimal
)

object Price {
  implicit val priceDecoder: Decoder[Price] = Decoder.forProduct2(
    "symbol",
    "price"
  )(Price.apply)
}

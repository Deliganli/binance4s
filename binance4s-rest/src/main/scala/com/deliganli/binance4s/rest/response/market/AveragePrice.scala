package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class AveragePrice(
  mins: Int,
  price: BigDecimal
)

object AveragePrice {
  implicit val averagePriceDecoder = Decoder.forProduct2(
    "mins",
    "price"
  )(AveragePrice.apply)
}

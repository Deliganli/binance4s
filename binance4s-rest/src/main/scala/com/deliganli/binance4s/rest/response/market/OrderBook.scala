package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class OrderBook(
  symbol: String,
  bidPrice: BigDecimal,
  bidQty: BigDecimal,
  askPrice: BigDecimal,
  askQty: BigDecimal
)

object OrderBook {
  implicit val orderBookDecoder = Decoder.forProduct5(
    "symbol",
    "bidPrice",
    "bidQty",
    "askPrice",
    "askQty"
  )(OrderBook.apply)
}

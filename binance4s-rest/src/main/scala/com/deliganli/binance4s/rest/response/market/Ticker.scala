package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class Ticker(
  symbol: String,
  priceChange: BigDecimal,
  priceChangePercent: BigDecimal,
  weightedAvgPrice: BigDecimal,
  prevClosePrice: BigDecimal,
  lastPrice: BigDecimal,
  lastQty: BigDecimal,
  bidPrice: BigDecimal,
  askPrice: BigDecimal,
  openPrice: BigDecimal,
  highPrice: BigDecimal,
  lowPrice: BigDecimal,
  volume: BigDecimal,
  quoteVolume: BigDecimal,
  openTime: Long,
  closeTime: Long,
  firstId: Long, // First tradeId
  lastId: Long, // Last tradeId
  count: Long // Trade count
)

object Ticker {
  implicit val tickerDecoder: Decoder[Ticker] = Decoder.forProduct19(
    "symbol",
    "priceChange",
    "priceChangePercent",
    "weightedAvgPrice",
    "prevClosePrice",
    "lastPrice",
    "lastQty",
    "bidPrice",
    "askPrice",
    "openPrice",
    "highPrice",
    "lowPrice",
    "volume",
    "quoteVolume",
    "openTime",
    "closeTime",
    "firstId",
    "lastId",
    "count"
  )(Ticker.apply)
}

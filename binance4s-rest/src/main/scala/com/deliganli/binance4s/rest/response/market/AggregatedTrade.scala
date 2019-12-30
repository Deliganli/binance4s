package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class AggregatedTrade(
  tradeId: Long,
  price: BigDecimal,
  quantity: BigDecimal,
  firsTradeId: Long,
  lastTradeId: Long,
  timestamp: Long,
  buyerIsMarketMaker: Boolean,
  wasBestPriceMatch: Boolean
)

object AggregatedTrade {

  implicit val d: Decoder[AggregatedTrade] = Decoder.forProduct8(
    "a", // Aggregate tradeId
    "p", // Price
    "q", // Quantity
    "f", // First tradeId
    "l", // Last tradeId
    "T", // Timestamp
    "m", // Was the buyer the maker?
    "M" // Was the trade the bt price match?
  )(AggregatedTrade.apply)
}

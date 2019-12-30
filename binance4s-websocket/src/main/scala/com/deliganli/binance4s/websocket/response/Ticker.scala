package com.deliganli.binance4s.websocket.response

import io.circe.Decoder
import org.joda.time.Instant

case class Ticker(
  eventTime: Instant,                       // Event time
  symbol: String,                           // Symbol
  priceChange: BigDecimal,                  // Price change
  priceChangePercent: BigDecimal,           // Price change percent
  weightedAveragePrice: BigDecimal,         // Weighted average price
  firstTradePrice: BigDecimal,              // First trade(F)-1 price (first trade before the 24hr rolling window)
  lastPrice: BigDecimal,                    // Last price
  lastQuantity: BigDecimal,                 // Last quantity
  bestBidPrice: BigDecimal,                 // Best bid price
  bestBidQuantity: BigDecimal,              // Best bid quantity
  bestAskPrice: BigDecimal,                 // Best ask price
  bestAskQuantity: BigDecimal,              // Best ask quantity
  openPrice: BigDecimal,                    // Open price
  highPrice: BigDecimal,                    // High price
  lowPrice: BigDecimal,                     // Low price
  totalTradedBaseAssetVolume: BigDecimal,   // Total traded base asset volume
  totalTradedQuotedAssetVolume: BigDecimal, // Total traded quote asset volume
  statisticsOpenTime: Instant,              // Statistics open time
  statisticsCloseTime: Instant,             // Statistics close time
  firstTradeId: Long,                       // First trade ID
  lastTradeId: Long,                        // Last trade Id
  totalNumberOfTrades: Long                 // Total number of trades
) extends Event

object Ticker {

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
  implicit val tickerDecoder: Decoder[Ticker] = Decoder.forProduct22(
    "E",
    "s",
    "p",
    "P",
    "w",
    "x",
    "c",
    "Q",
    "b",
    "B",
    "a",
    "A",
    "o",
    "h",
    "l",
    "v",
    "q",
    "O",
    "C",
    "F",
    "L",
    "n"
  )(Ticker.apply)
}

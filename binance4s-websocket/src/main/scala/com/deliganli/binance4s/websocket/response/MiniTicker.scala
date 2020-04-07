package com.deliganli.binance4s.websocket.response

import io.circe.Decoder
import java.time.Instant

case class MiniTicker(
  eventTime: Instant,                     // Event time
  symbol: String,                         // Symbol
  closePrice: BigDecimal,                 // Close price
  openPrice: BigDecimal,                  // Open price
  highPrice: BigDecimal,                  // High price
  lowPrice: BigDecimal,                   // Low price
  totalTradedBaseAssetVolume: BigDecimal, // Total traded base asset volume
  totalTradedQuoteAssetVolume: BigDecimal // Total traded quote asset volume
) extends Event

object MiniTicker {

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
  implicit val miniTickerDecoder: Decoder[MiniTicker] = Decoder.forProduct8(
    "E",
    "s",
    "c",
    "o",
    "h",
    "l",
    "v",
    "q"
  )(MiniTicker.apply)
}

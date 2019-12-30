package com.deliganli.binance4s.websocket.response

import com.deliganli.binance4s.common.consts.KlineInterval
import io.circe.{Decoder, HCursor}
import org.joda.time.Instant

case class Kline(
  eventTime: Instant,                  // Event time
  startTime: Instant,                  // Kline start time
  closeTime: Instant,                  // Kline close time
  symbol: String,                      // Symbol
  interval: KlineInterval,             // Interval
  firstTradeId: Long,                  // First trade ID
  lastTradeId: Long,                   // Last trade ID
  openPrice: BigDecimal,               // Open price
  closePrice: BigDecimal,              // Close price
  highPrice: BigDecimal,               // High price
  lowPrice: BigDecimal,                // Low price
  baseAssetVolume: BigDecimal,         // Base asset volume
  numberOfTrades: Long,                // Number of trades
  isThisKlineClosed: Boolean,          // Is this kline closed?
  quoteAssetVolume: BigDecimal,        // Quote asset volume
  takerBuyBaseAssetVolume: BigDecimal, // Taker buy base asset volume
  takerBuyQuoteAssetVolume: BigDecimal // Taker buy quote asset volume
) extends Event

object Kline {

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val klineDecoder: Decoder[Kline] = (c: HCursor) => {
    for {
      eventTime <- c.downField("E").as[Instant]
      k = c.downField("k")
      startTime                <- k.downField("t").as[Instant]
      closeTime                <- k.downField("T").as[Instant]
      symbol                   <- k.downField("s").as[String]
      interval                 <- k.downField("i").as[KlineInterval]
      firstTradeId             <- k.downField("f").as[Long]
      lastTradeId              <- k.downField("L").as[Long]
      openPrice                <- k.downField("o").as[BigDecimal]
      closePrice               <- k.downField("c").as[BigDecimal]
      highPrice                <- k.downField("h").as[BigDecimal]
      lowPrice                 <- k.downField("l").as[BigDecimal]
      baseAssetVolume          <- k.downField("v").as[BigDecimal]
      numberOfTrades           <- k.downField("n").as[Long]
      isThisKlineClosed        <- k.downField("x").as[Boolean]
      quoteAssetVolume         <- k.downField("q").as[BigDecimal]
      takerBuyBaseAssetVolume  <- k.downField("V").as[BigDecimal]
      takerBuyQuoteAssetVolume <- k.downField("Q").as[BigDecimal]
    } yield Kline(
      eventTime,
      startTime,
      closeTime,
      symbol,
      interval,
      firstTradeId,
      lastTradeId,
      openPrice,
      closePrice,
      highPrice,
      lowPrice,
      baseAssetVolume,
      numberOfTrades,
      isThisKlineClosed,
      quoteAssetVolume,
      takerBuyBaseAssetVolume,
      takerBuyQuoteAssetVolume
    )
  }

}

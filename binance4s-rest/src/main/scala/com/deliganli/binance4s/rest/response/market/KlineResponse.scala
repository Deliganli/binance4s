package com.deliganli.binance4s.rest.response.market

import io.circe.{Decoder, HCursor}
import java.time.Instant

case class KlineResponse(
  openTime: Instant,
  open: BigDecimal,
  high: BigDecimal,
  low: BigDecimal,
  close: BigDecimal,
  volume: BigDecimal,
  closeTime: Instant,
  quoteAssetVolume: BigDecimal,
  numberOfTrades: Long,
  takerBuyBaseAssetVolume: BigDecimal,
  takerBuyQuoteAssetVolume: BigDecimal,
  ignore: String)

object KlineResponse {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  Decoder.forProduct3(
    "lastUpdateId",
    "bids",
    "asks"
  )(PartialDepthUpdate.apply)

  implicit val decodePrice: Decoder[KlineResponse] = (c: HCursor) => {
    for {
      openTime                 <- c.downArray.as[Instant]
      open                     <- c.downN(1).as[BigDecimal]
      high                     <- c.downN(2).as[BigDecimal]
      low                      <- c.downN(3).as[BigDecimal]
      close                    <- c.downN(4).as[BigDecimal]
      volume                   <- c.downN(5).as[BigDecimal]
      closeTime                <- c.downN(6).as[Instant]
      quoteAssetVolume         <- c.downN(7).as[BigDecimal]
      numberOfTrades           <- c.downN(8).as[Long]
      takerBuyBaseAssetVolume  <- c.downN(9).as[BigDecimal]
      takerBuyQuoteAssetVolume <- c.downN(10).as[BigDecimal]
      ignore                   <- c.downN(11).as[String]
    } yield {
      new KlineResponse(
        openTime,
        open,
        high,
        low,
        close,
        volume,
        closeTime,
        quoteAssetVolume,
        numberOfTrades,
        takerBuyBaseAssetVolume,
        takerBuyQuoteAssetVolume,
        ignore
      )
    }
  }
}

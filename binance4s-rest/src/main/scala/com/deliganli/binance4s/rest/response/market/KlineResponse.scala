package com.deliganli.binance4s.rest.response.market

import io.circe.{Decoder, Encoder, HCursor}
import java.time.Instant
import io.circe.Json
import io.circe.syntax._

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
  ignore: String
)

object KlineResponse {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
  import com.deliganli.binance4s.common.formatters.Encoders.encodeInstant

  implicit val decodeKlineResponse: Decoder[KlineResponse] = (c: HCursor) => {
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

  implicit val encodeKlineResponse: Encoder[KlineResponse] =
    Encoder.instance[KlineResponse](
      m =>
        Json.arr(
          m.openTime.asJson,
          m.open.asJson,
          m.high.asJson,
          m.low.asJson,
          m.close.asJson,
          m.volume.asJson,
          m.closeTime.asJson,
          m.quoteAssetVolume.asJson,
          m.numberOfTrades.asJson,
          m.takerBuyBaseAssetVolume.asJson,
          m.takerBuyQuoteAssetVolume.asJson,
          m.ignore.asJson
        )
    )

}

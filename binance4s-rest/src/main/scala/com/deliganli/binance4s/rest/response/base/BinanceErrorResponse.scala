package com.deliganli.binance4s.rest.response.base

import cats.Show
import io.circe.Decoder

case class BinanceErrorResponse(
  code: Int,
  message: String,
  description: Option[String]
)

object BinanceErrorResponse {
  implicit val show: Show[BinanceErrorResponse] = (t: BinanceErrorResponse) => {
    s"${t.code} - ${t.message}: ${t.description.getOrElse("")}"
  }

  implicit val binanceErrorDecoder: Decoder[BinanceErrorResponse] = Decoder.forProduct3(
    "code",
    "msg",
    "dsc"
  )(BinanceErrorResponse.apply)
}

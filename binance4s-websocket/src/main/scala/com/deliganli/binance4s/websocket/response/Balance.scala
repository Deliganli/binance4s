package com.deliganli.binance4s.websocket.response

import io.circe.Decoder

case class Balance(asset: String, freeAmount: BigDecimal, lockedAmount: BigDecimal)

object Balance {

  implicit val balanceDecoder: Decoder[Balance] = Decoder.forProduct3(
    "a",
    "f",
    "l"
  )(Balance.apply)
}

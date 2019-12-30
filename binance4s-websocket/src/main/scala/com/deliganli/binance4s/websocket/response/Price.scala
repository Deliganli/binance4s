package com.deliganli.binance4s.websocket.response

import io.circe.{Decoder, HCursor}

case class Price(level: BigDecimal, quantity: BigDecimal)

object Price {

  implicit val priceDecoder: Decoder[Price] = (c: HCursor) => {
    for {
      level    <- c.downArray.as[BigDecimal]
      quantity <- c.downN(1).as[BigDecimal]
    } yield Price(level, quantity)
  }
}

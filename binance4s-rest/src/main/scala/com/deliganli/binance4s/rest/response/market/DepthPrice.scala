package com.deliganli.binance4s.rest.response.market

import io.circe.{Decoder, HCursor}

case class DepthPrice(level: BigDecimal, quantity: BigDecimal)

object DepthPrice {

  implicit val decodePrice: Decoder[DepthPrice] = (c: HCursor) => {
    for {
      level    <- c.downArray.as[BigDecimal]
      quantity <- c.downN(1).as[BigDecimal]
    } yield {
      new DepthPrice(level, quantity)
    }
  }
}

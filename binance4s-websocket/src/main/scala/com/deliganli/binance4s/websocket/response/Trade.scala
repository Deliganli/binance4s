package com.deliganli.binance4s.websocket.response

import io.circe.Decoder
import java.time.Instant

case class Trade(
  eventTime: Instant, // Event time
  symbol: String,     // Symbol
  tradeId: Long,
  price: BigDecimal,
  quantity: BigDecimal,
  buyerOrderId: Long,
  sellerOrderId: Long,
  tradeTime: Instant,
  buyerIsMarketMaker: Boolean
) extends Event

object Trade {

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val tradeDecoder: Decoder[Trade] = Decoder.forProduct9(
    "E",
    "s",
    "t",
    "p",
    "q",
    "b",
    "a",
    "T",
    "m"
  )(Trade.apply)
}

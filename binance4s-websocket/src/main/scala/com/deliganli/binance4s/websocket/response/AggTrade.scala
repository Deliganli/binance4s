package com.deliganli.binance4s.websocket.response

import io.circe.Decoder
import java.time.Instant

case class AggTrade(
  eventTime: Instant,
  symbol: String,
  aggregateTradeId: Long,
  price: BigDecimal,
  quantity: BigDecimal,
  firsTradeId: Long,
  lastTradeId: Long,
  tradeTime: Instant,
  buyerIsMarketMaker: Boolean
) extends Event

object AggTrade {

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val aggTradeDecoder: Decoder[AggTrade] = Decoder.forProduct9(
    "E",
    "s",
    "a",
    "p",
    "q",
    "f",
    "l",
    "T",
    "m"
  )(AggTrade.apply)
}

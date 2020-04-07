package com.deliganli.binance4s.websocket.response

import io.circe.Decoder
import java.time.Instant

case class DepthUpdate(
  eventTime: Instant,        // Event time
  symbol: String,            // Symbol
  firstUpdateId: Long,       // First update ID in event
  finalUpdateId: Long,       // Final update ID in event
  bidDepthDelta: Seq[Price], // Bids to be updated, quantity of 0 means remove it
  askDepthDelta: Seq[Price]  // Asks to be updated
) extends Event

object DepthUpdate {

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
  implicit val depthUpdateDecoder: Decoder[DepthUpdate] = Decoder.forProduct6(
    "E",
    "s",
    "U",
    "u",
    "b",
    "a"
  )(DepthUpdate.apply)
}

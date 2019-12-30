package com.deliganli.binance4s.websocket.response

import io.circe.Decoder

case class PartialDepthUpdate(
  lastUpdateId: Long,
  bids: Seq[Price],
  asks: Seq[Price]
) extends Event

object PartialDepthUpdate {

  implicit val partialDepthUpdateDecoder: Decoder[PartialDepthUpdate] = Decoder.forProduct3(
    "lastUpdateId",
    "bids",
    "asks"
  )(PartialDepthUpdate.apply)
}

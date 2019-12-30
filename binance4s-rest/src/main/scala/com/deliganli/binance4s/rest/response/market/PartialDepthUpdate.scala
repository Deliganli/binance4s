package com.deliganli.binance4s.rest.response.market

import io.circe.Decoder

case class PartialDepthUpdate(
  lastUpdateId: Long,
  bids: Seq[DepthPrice],
  asks: Seq[DepthPrice]
)

object PartialDepthUpdate {

  import DepthPrice.decodePrice

  implicit val partialDepthUpdateDecoder: Decoder[PartialDepthUpdate] = Decoder.forProduct3(
    "lastUpdateId",
    "bids",
    "asks"
  )(PartialDepthUpdate.apply)
}

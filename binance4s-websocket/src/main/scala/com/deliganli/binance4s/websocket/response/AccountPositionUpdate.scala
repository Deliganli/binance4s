package com.deliganli.binance4s.websocket.response

import io.circe.Decoder
import org.joda.time.Instant

case class AccountPositionUpdate(eventTime: Instant, timeOfLastAccountUpdate: Instant, balances: Seq[Balance])
    extends UserEvent

object AccountPositionUpdate {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val accountUpdateDecoder: Decoder[AccountPositionUpdate] = Decoder.forProduct3(
    "E",
    "u",
    "B"
  )(AccountPositionUpdate.apply)

}

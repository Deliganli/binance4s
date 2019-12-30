package com.deliganli.binance4s.rest.response.general

import io.circe.Decoder
import org.joda.time.Instant

case class Time(serverTime: Instant)

object Time {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
  implicit val d: Decoder[Time] = Decoder.forProduct1("serverTime")(Time.apply)
}

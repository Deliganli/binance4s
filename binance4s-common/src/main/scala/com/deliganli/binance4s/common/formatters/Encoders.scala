package com.deliganli.binance4s.common.formatters

import io.circe.Encoder
import java.time.Instant

object Encoders {
  implicit val encodeInstant: Encoder[Instant] = Encoder.encodeLong.contramap[Instant](_.toEpochMilli())
}

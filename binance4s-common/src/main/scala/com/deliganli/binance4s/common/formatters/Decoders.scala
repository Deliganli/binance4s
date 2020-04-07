package com.deliganli.binance4s.common.formatters

import cats.syntax.either._
import io.circe.Decoder
import java.time.Instant

object Decoders {
  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeLong.emap { lng =>
    Either.catchNonFatal(Instant.ofEpochMilli(lng)).leftMap(e => s"Couldn't parse instant: $e")
  }

}

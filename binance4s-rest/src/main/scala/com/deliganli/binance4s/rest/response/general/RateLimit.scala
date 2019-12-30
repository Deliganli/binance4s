package com.deliganli.binance4s.rest.response.general

import io.circe.{Decoder, Encoder}

case class RateLimit(
  rateLimitType: String,
  interval: String,
  intervalNum: Long,
  limit: Long
)

object RateLimit {
  implicit val d: Decoder[RateLimit] = Decoder.forProduct4(
    "rateLimitType",
    "interval",
    "intervalNum",
    "limit"
  )(RateLimit.apply)

  implicit val e: Encoder[RateLimit] = Encoder.forProduct4(
    "rateLimitType",
    "interval",
    "intervalNum",
    "limit"
  ) { m =>
    (
      m.rateLimitType,
      m.interval,
      m.intervalNum,
      m.limit
    )
  }
}

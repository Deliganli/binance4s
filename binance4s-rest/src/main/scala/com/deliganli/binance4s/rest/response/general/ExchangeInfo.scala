package com.deliganli.binance4s.rest.response.general

import com.deliganli.binance4s.rest.response.filter.ExchangeFilter
import com.deliganli.binance4s.rest.response.market.CurrencySymbol
import io.circe.{Decoder, Encoder}
import org.joda.time.Instant

case class ExchangeInfo(
  timezone: String,
  serverTime: Instant,
  rateLimits: Seq[RateLimit],
  exchangeFilters: Seq[ExchangeFilter],
  symbols: Seq[CurrencySymbol]
)

object ExchangeInfo {

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
  implicit val d: Decoder[ExchangeInfo] = Decoder.forProduct5(
    "timezone",
    "serverTime",
    "rateLimits",
    "exchangeFilters",
    "symbols"
  )(ExchangeInfo.apply)

  import com.deliganli.binance4s.common.formatters.Encoders.encodeInstant
  implicit val e: Encoder[ExchangeInfo] = Encoder.forProduct5(
    "timezone",
    "serverTime",
    "rateLimits",
    "exchangeFilters",
    "symbols"
  ) { m =>
    (
      m.timezone,
      m.serverTime,
      m.rateLimits,
      m.exchangeFilters,
      m.symbols
    )
  }

}

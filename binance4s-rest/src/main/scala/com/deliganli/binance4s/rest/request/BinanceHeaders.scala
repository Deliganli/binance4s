package com.deliganli.binance4s.rest.request

import org.http4s.Header

object BinanceHeaders {
  case class ApiKey(value: String)

  def apiKey(value: String): Header = Header.apply("X-MBX-APIKEY", value)
}

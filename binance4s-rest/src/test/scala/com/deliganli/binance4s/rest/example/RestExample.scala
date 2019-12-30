package com.deliganli.binance4s.rest.example

import cats.effect.ConcurrentEffect
import cats.implicits._
import com.deliganli.binance4s.common.Credentials
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.http.JDKHttpClient
import com.deliganli.binance4s.rest.response.general.ExchangeInfo
import com.typesafe.scalalogging.StrictLogging
import org.http4s.client.Client
import org.http4s.client.jdkhttpclient.JdkHttpClient

class RestExample[F[_]: ConcurrentEffect] extends StrictLogging {

  val httpClient: Client[F] = JdkHttpClient[F](JDKHttpClient.default)

  val secureClient: BinanceRestClient.SecureClient[F] = BinanceRestClient.secure[F](
    httpClient,
    Credentials("<key>", "<secret>")
  )

  // Metered client only requires api key
  val meteredClient: BinanceRestClient.MeteredClient[F] = BinanceRestClient.metered[F](
    httpClient,
    "<key>"
  )

  // Public client doesn't require neither api key nor secret
  val publicClient: BinanceRestClient.PublicClient[F] = BinanceRestClient.public[F](
    httpClient
  )

  val exchangeInfo: F[ExchangeInfo] = publicClient.general.exchangeInfo().map(_.body)
}

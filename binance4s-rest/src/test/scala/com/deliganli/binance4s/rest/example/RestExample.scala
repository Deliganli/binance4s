package com.deliganli.binance4s.rest.example

import cats.effect.{Clock, ConcurrentEffect}
import com.deliganli.binance4s.common.Credentials
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.http.JDKHttpClient
import com.deliganli.binance4s.rest.response.base.{BinanceError, BinanceResponse}
import com.deliganli.binance4s.rest.response.general.ExchangeInfo
import com.typesafe.scalalogging.StrictLogging
import org.http4s.client.Client
import org.http4s.client.jdkhttpclient.JdkHttpClient

class RestExample[F[_]: ConcurrentEffect: Clock] extends StrictLogging {
  val httpClient: Client[F] = JdkHttpClient[F](JDKHttpClient.default)

  // Public client doesn't require neither api key nor secret
  val publicClient: BinanceRestClient.PublicClient[F] = BinanceRestClient.public[F](
    httpClient
  )

  // Metered client only requires api key
  val meteredClient: BinanceRestClient.MeteredClient[F] = BinanceRestClient.metered[F](
    httpClient,
    "<key>"
  )

  // Secure client requires both api key and secret
  val secureClient: BinanceRestClient.SecureClient[F] = BinanceRestClient.secure[F](
    httpClient,
    Credentials("<key>", "<secret>")
  )

  val exchangeInfo: F[BinanceResponse[Either[BinanceError, ExchangeInfo]]] = publicClient.general.exchangeInfo
}

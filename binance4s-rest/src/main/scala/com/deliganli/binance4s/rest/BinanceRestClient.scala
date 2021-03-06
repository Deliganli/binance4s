package com.deliganli.binance4s.rest

import cats.Id
import cats.effect.{Clock, Sync}
import com.deliganli.binance4s.common.Credentials
import com.deliganli.binance4s.rest.client._
import com.deliganli.binance4s.rest.request.BinanceHeaders.ApiKey
import com.deliganli.binance4s.rest.request.{ApiAuth, RequestSigner}
import com.deliganli.binance4s.rest.response.base.BinanceError
import org.http4s.Uri
import org.http4s.client.Client
import tsec.common._
import tsec.mac.jca.HMACSHA256

sealed trait BinanceRestClient[F[_]]

object BinanceRestClient {
  // --------------------------------------------------------------------------------------------------------------

  type Result[T] = Either[BinanceError, T]

  class PublicClient[F[_]] protected (val general: GeneralClient[F], val market: MarketClient[F])
      extends BinanceRestClient[F]

  object PublicClient {

    protected[BinanceRestClient] def apply[F[_]: Sync: Client](api: Uri): PublicClient[F] = {
      val general = new GeneralClient[F](api)
      val ticker  = new TickerClient[F](api)
      val trade   = new TradeClient[F](api)
      val market  = new MarketClient[F](api)(trade, ticker)
      new PublicClient[F](general, market)
    }
  }

  // --------------------------------------------------------------------------------------------------------------

  class MeteredClient[F[_]] protected (
    override val general: GeneralClient[F],
    override val market: MarketClient[F],
    val historical: HistoricalClient[F],
    val user: StreamClient[F])
      extends PublicClient[F](general, market)

  object MeteredClient {

    protected[BinanceRestClient] def apply[F[_]: Sync: ApiAuth](
      api: Uri
    )(
      implicit client: Client[F]
    ): MeteredClient[F] = {
      val derived = PublicClient(api)

      val historical = new HistoricalClient[F](api)
      val user       = new StreamClient[F](api)
      new MeteredClient[F](derived.general, derived.market, historical, user)
    }
  }

  // --------------------------------------------------------------------------------------------------------------

  class SecureClient[F[_]] protected (
    override val general: GeneralClient[F],
    override val market: MarketClient[F],
    override val historical: HistoricalClient[F],
    override val user: StreamClient[F],
    val account: AccountClient[F],
    val order: OrderClient[F])
      extends MeteredClient[F](general, market, historical, user)

  object SecureClient {

    protected[BinanceRestClient] def apply[F[_]: Sync: Clock: ApiAuth: RequestSigner](
      api: Uri
    )(
      implicit client: Client[F]
    ): SecureClient[F] = {
      val derived = MeteredClient(api)
      val order   = new OrderClient[F](api)
      val account = new AccountClient[F](api)

      new SecureClient[F](
        derived.general,
        derived.market,
        derived.historical,
        derived.user,
        account,
        order
      )
    }
  }

  // --------------------------------------------------------------------------------------------------------------

  def public[F[_]: Sync](
    client: Client[F],
    api: Uri = Uri.unsafeFromString("https://api.binance.com/api")
  ): PublicClient[F] = {
    implicit val C: Client[F] = client

    PublicClient(api)
  }

  def metered[F[_]: Sync](
    client: Client[F],
    apiKey: String,
    api: Uri = Uri.unsafeFromString("https://api.binance.com/api")
  ): MeteredClient[F] = {
    implicit val C: Client[F]   = client
    implicit val AD: ApiAuth[F] = ApiAuth.create(ApiKey(apiKey))

    MeteredClient(api)
  }

  def secure[F[_]: Sync: Clock](
    client: Client[F],
    credentials: Credentials,
    api: Uri = Uri.unsafeFromString("https://api.binance.com/api")
  ): SecureClient[F] = {
    val signingKey                    = HMACSHA256.buildKey[Id](credentials.secret.asciiBytes)
    implicit val C: Client[F]         = client
    implicit val AD: ApiAuth[F]       = ApiAuth.create(ApiKey(credentials.key))
    implicit val SD: RequestSigner[F] = RequestSigner.create(signingKey)

    SecureClient(api)
  }
}

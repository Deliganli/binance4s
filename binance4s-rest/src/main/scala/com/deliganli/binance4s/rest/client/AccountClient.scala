package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import cats.implicits._
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.QueryParams.Formatters.instantEncoder
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.request.{KeyAdder, Period, QuerySigner}
import com.deliganli.binance4s.rest.response.account.{AccountInfo, AccountTrade}
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}
import org.joda.time.Instant

class AccountClient[F[_]: Sync: Client: KeyAdder: QuerySigner](api: Uri)(val order: OrderClient[F]) {

  def info(
    recvWindow: Option[Long] = None,
    timestamp: Instant = Instant.now(),
    version: Int = 3
  ): F[BinanceResponse[AccountInfo]] = {
    val query = Query.empty
      .withOptionQueryParam(Keys.recvWindow, recvWindow)
      .withQueryParam(Keys.timestamp, timestamp)

    for {
      request <- api.endpoint("account", version, query).request[F](GET).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[AccountInfo])
    } yield result
  }

  def myTrades(
    symbol: String,
    fromId: Option[Long] = None,
    limit: Option[Int] = None,
    period: Period = Period.Empty,
    recvWindow: Option[Long] = None,
    timestamp: Instant = Instant.now(),
    version: Int = 3
  ): F[BinanceResponse[Seq[AccountTrade]]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withOptionQueryParam(Keys.fromId, fromId)
      .withOptionQueryParam(Keys.limit, limit)
      .withOptionQueryParam(Keys.recvWindow, recvWindow)
      .withQueryParam(Keys.timestamp, timestamp)
      .withSubQuery(period)

    for {
      request <- api.endpoint("myTrades", version, query).request[F](GET).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[Seq[AccountTrade]])
    } yield result
  }
}

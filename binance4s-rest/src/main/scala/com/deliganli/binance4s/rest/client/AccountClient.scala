package com.deliganli.binance4s.rest.client

import java.util.concurrent.TimeUnit

import cats.effect.{Clock, Sync}
import cats.implicits._
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.request.{ApiAuth, Period, RequestSigner}
import com.deliganli.binance4s.rest.response.account.{AccountInfo, AccountTrade}
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class AccountClient[F[_]: Sync: Clock: Client: ApiAuth: RequestSigner](api: Uri) {

  def info(recvWindow: Option[Long] = None): F[BinanceResponse[Result[AccountInfo]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query.empty
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint("account", 3, query).request[F](GET).putKey.sign)
      .flatMap(_.fetch(_.consume[AccountInfo]))
  }

  def myTrades(
    symbol: String,
    fromId: Option[Long] = None,
    limit: Option[Int] = None,
    period: Period = Period.Empty,
    recvWindow: Option[Long] = None
  ): F[BinanceResponse[Result[Seq[AccountTrade]]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withOptionQueryParam(Keys.fromId, fromId)
          .withOptionQueryParam(Keys.limit, limit)
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withQueryParam(Keys.timestamp, timestamp)
          .withSubQuery(period)
      }
      .flatMap(query => api.endpoint("myTrades", 3, query).request[F](GET).putKey.sign)
      .flatMap(_.fetch(_.consume[Seq[AccountTrade]]))
  }
}

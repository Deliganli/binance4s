package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import cats.syntax.flatMap._
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.{OrderBook, Price, Ticker}
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class TickerClient[F[_]: Sync: Client](api: Uri) {

  def daily(symbol: String): F[BinanceResponse[Result[Ticker]]] = {
    Sync[F]
      .pure(Query.fromPairs(Keys.symbol -> symbol))
      .flatMap(query => api.endpoint("ticker/24hr", 3, query).request[F](GET).fetch(_.consume[Ticker]))
  }

  def dailyAll: F[BinanceResponse[Result[Seq[Ticker]]]] = {
    api.endpoint("ticker/24hr", 3).request[F](GET).fetch(_.consume[Seq[Ticker]])
  }

  def price(symbol: String): F[BinanceResponse[Result[Price]]] = {
    Sync[F]
      .pure(Query.fromPairs(Keys.symbol -> symbol))
      .flatMap(query => api.endpoint("ticker/price", 3, query).request[F](GET).fetch(_.consume[Price]))
  }

  def priceAll: F[BinanceResponse[Result[Seq[Price]]]] = {
    api.endpoint("ticker/price", 3).request[F](GET).fetch(_.consume[Seq[Price]])
  }

  def bookAll(symbol: String): F[BinanceResponse[Result[OrderBook]]] = {
    Sync[F]
      .pure(Query.fromPairs(Keys.symbol -> symbol))
      .flatMap(query => api.endpoint("ticker/bookTicker", 3, query).request[F](GET).fetch(_.consume[OrderBook]))
  }

  def book: F[BinanceResponse[Result[Seq[OrderBook]]]] = {
    api.endpoint("ticker/bookTicker", 3).request[F](GET).fetch(_.consume[Seq[OrderBook]])
  }
}

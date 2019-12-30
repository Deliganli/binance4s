package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.{OrderBook, Price, Ticker}
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class TickerClient[F[_]: Sync: Client](api: Uri) {

  def daily(symbol: String, version: Int = 1): F[BinanceResponse[Ticker]] = {
    val query = Query.fromPairs(Keys.symbol -> symbol)

    api.endpoint("ticker/24hr", version, query).request[F](GET).fetch(_.consumeUnsafe[Ticker])
  }

  def dailyAll(version: Int = 1): F[BinanceResponse[Seq[Ticker]]] = {
    api.endpoint("ticker/24hr", version).request[F](GET).fetch(_.consumeUnsafe[Seq[Ticker]])
  }

  def price(symbol: String, version: Int = 3): F[BinanceResponse[Price]] = {
    val query = Query.fromPairs(Keys.symbol -> symbol)

    api.endpoint("ticker/price", version, query).request[F](GET).fetch(_.consumeUnsafe[Price])
  }

  def priceAll(version: Int = 3): F[BinanceResponse[Seq[Price]]] = {
    api.endpoint("ticker/price", version).request[F](GET).fetch(_.consumeUnsafe[Seq[Price]])
  }

  def bookAll(symbol: String, version: Int = 3): F[BinanceResponse[OrderBook]] = {
    val query = Query.fromPairs(Keys.symbol -> symbol)

    api.endpoint("ticker/bookTicker", version, query).request[F](GET).fetch(_.consumeUnsafe[OrderBook])
  }

  def book(version: Int = 3): F[BinanceResponse[Seq[OrderBook]]] = {
    api.endpoint("ticker/bookTicker", version).request[F](GET).fetch(_.consumeUnsafe[Seq[OrderBook]])
  }
}

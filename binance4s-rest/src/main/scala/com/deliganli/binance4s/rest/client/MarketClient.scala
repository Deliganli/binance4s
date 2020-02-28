package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import cats.syntax.flatMap._
import com.deliganli.binance4s.common.consts.{DepthLimit, KlineInterval}
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.Period
import com.deliganli.binance4s.rest.request.QueryParams.Formatters.{depthLimitEncoder, klineIntervalEncoder}
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.{AveragePrice, KlineResponse, PartialDepthUpdate}
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class MarketClient[F[_]: Sync: Client](api: Uri)(val trade: TradeClient[F], val ticker: TickerClient[F]) {

  def depth(symbol: String, limit: DepthLimit = DepthLimit.Hundred): F[BinanceResponse[Result[PartialDepthUpdate]]] = {
    Sync[F]
      .pure {
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withQueryParam(Keys.limit, limit)
      }
      .flatMap { query =>
        api
          .endpoint("depth", 3, query)
          .request[F](GET)
          .fetch(_.consume[PartialDepthUpdate])
      }
  }

  def klines(
    symbol: String,
    interval: KlineInterval,
    limit: Int = 500,
    period: Period = Period.Empty
  ): F[BinanceResponse[Result[Seq[KlineResponse]]]] = {
    Sync[F]
      .pure {
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withQueryParam(Keys.interval, interval)
          .withQueryParam(Keys.limit, limit)
          .withSubQuery(period)
      }
      .flatMap { query =>
        api
          .endpoint("klines", 3, query)
          .request[F](GET)
          .fetch(_.consume[Seq[KlineResponse]])
      }
  }

  def avgPrice(symbol: String): F[BinanceResponse[Result[AveragePrice]]] = {
    Sync[F]
      .pure(Query.fromPairs(Keys.symbol -> symbol))
      .flatMap { query =>
        api
          .endpoint("avgPrice", 3, query)
          .request[F](GET)
          .fetch(_.consume[AveragePrice])
      }
  }
}

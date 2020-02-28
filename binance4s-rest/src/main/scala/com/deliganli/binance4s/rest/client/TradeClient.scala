package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import cats.syntax.flatMap._
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.Period
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.{AggregatedTrade, Trade}
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class TradeClient[F[_]: Sync: Client](api: Uri) {

  def recent(symbol: String, limit: Int = 500): F[BinanceResponse[Result[Seq[Trade]]]] = {
    Sync[F]
      .pure {
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withQueryParam(Keys.limit, limit)
      }
      .flatMap { query =>
        api
          .endpoint("trades", 3, query)
          .request[F](GET)
          .fetch(_.consume[Seq[Trade]])
      }
  }

  def aggregated(
    symbol: String,
    fromId: Option[Long] = None,
    period: Period = Period.Empty,
    limit: Int = 500
  ): F[BinanceResponse[Result[Seq[AggregatedTrade]]]] = {
    Sync[F]
      .pure {
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withOptionQueryParam(Keys.fromId, fromId)
          .withQueryParam(Keys.limit, limit)
          .withSubQuery(period)
      }
      .flatMap { query =>
        api
          .endpoint("aggTrades", 3, query)
          .request[F](GET)
          .fetch(_.consume[Seq[AggregatedTrade]])
      }
  }
}

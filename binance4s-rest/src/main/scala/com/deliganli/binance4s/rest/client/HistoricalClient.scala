package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import cats.syntax.flatMap._
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.ApiAuth
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.Trade
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class HistoricalClient[F[_]: Sync: Client: ApiAuth](api: Uri) {

  def trades(
    symbol: String,
    limit: Option[Int] = None,
    fromId: Option[Long] = None
  ): F[BinanceResponse[Result[Seq[Trade]]]] = {
    Sync[F]
      .pure {
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withOptionQueryParam(Keys.limit, limit)
          .withOptionQueryParam(Keys.fromId, fromId)
      }
      .flatMap { query =>
        api
          .endpoint("historicalTrades", 1, query)
          .request[F](GET)
          .putKey
          .fetch(_.consume[Seq[Trade]])
      }
  }
}

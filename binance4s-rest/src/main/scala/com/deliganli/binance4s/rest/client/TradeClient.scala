package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.Period
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.{AggregatedTrade, Trade}
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class TradeClient[F[_]: Sync: Client](api: Uri) {

  def recent(
    symbol: String,
    limit: Int,
    version: Int = 1
  ): F[BinanceResponse[Seq[Trade]]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withQueryParam(Keys.limit, limit)

    api.endpoint("trades", version, query).request[F](GET).fetch(_.consumeUnsafe[Seq[Trade]])
  }

  def aggregated(
    symbol: String,
    fromId: Option[Long] = None,
    period: Period = Period.Empty,
    limit: Int = 500,
    version: Int = 1
  ): F[BinanceResponse[Seq[AggregatedTrade]]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withOptionQueryParam(Keys.fromId, fromId)
      .withQueryParam(Keys.limit, limit)
      .withSubQuery(period)

    api.endpoint("aggTrades", version, query).request[F](GET).fetch(_.consumeUnsafe[Seq[AggregatedTrade]])
  }
}

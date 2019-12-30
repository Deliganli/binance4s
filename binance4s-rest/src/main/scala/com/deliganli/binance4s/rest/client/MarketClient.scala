package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import com.deliganli.binance4s.common.consts.{DepthLimit, KlineInterval}
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.Period
import com.deliganli.binance4s.rest.request.QueryParams.Formatters.{depthLimitEncoder, klineIntervalEncoder}
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.{AveragePrice, KlineResponse, PartialDepthUpdate}
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class MarketClient[F[_]: Sync: Client](api: Uri)(
  val trade: TradeClient[F],
  val ticker: TickerClient[F]
) {

  def depth(
    symbol: String,
    limit: DepthLimit,
    version: Int = 1
  ): F[BinanceResponse[PartialDepthUpdate]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withQueryParam(Keys.limit, limit)

    api.endpoint("depth", version, query).request[F](GET).fetch(_.consumeUnsafe[PartialDepthUpdate])
  }

  def klines(
    symbol: String,
    interval: KlineInterval,
    limit: Int = 500,
    period: Period = Period.Empty,
    version: Int = 1
  ): F[BinanceResponse[Seq[KlineResponse]]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withQueryParam(Keys.interval, interval)
      .withQueryParam(Keys.limit, limit)
      .withSubQuery(period)

    api.endpoint("klines", version, query).request[F](GET).fetch(_.consumeUnsafe[Seq[KlineResponse]])
  }

  def avgPrice(symbol: String, version: Int = 3): F[BinanceResponse[AveragePrice]] = {
    val query = Query.fromPairs(Keys.symbol -> symbol)

    api.endpoint("avgPrice", version, query).request[F](GET).fetch(_.consumeUnsafe[AveragePrice])
  }
}

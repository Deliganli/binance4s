package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.KeyAdder
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.market.Trade
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class HistoricalClient[F[_]: Sync: Client: KeyAdder](api: Uri) {

  def trades(
    symbol: String,
    limit: Option[Int] = None,
    fromId: Option[Long] = None,
    version: Int = 1
  ): F[BinanceResponse[Seq[Trade]]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withOptionQueryParam(Keys.limit, limit)
      .withOptionQueryParam(Keys.fromId, fromId)

    api.endpoint("historicalTrades", version, query).request[F](GET).putKey.fetch(_.consumeUnsafe[Seq[Trade]])
  }
}

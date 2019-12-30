package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.{KeyAdder, QueryParams}
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.stream.UserDataStream
import org.http4s.Method.{DELETE, POST, PUT}
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class StreamClient[F[_]: Sync: Client: KeyAdder](api: Uri) {
  import StreamClient.path

  def start(version: Int = 1): F[BinanceResponse[UserDataStream]] = {
    api.endpoint(path, version).request[F](POST).putKey.fetch(_.consumeUnsafe[UserDataStream])
  }

  def keepAlive(listenKey: String, version: Int = 1): F[BinanceResponse[Unit]] = {
    val query = Query.fromPairs(QueryParams.Keys.listenKey -> listenKey)
    api.endpoint(path, version, query).request[F](PUT).putKey.fetch(_.consumeUnsafe[Unit])
  }

  def stop(listenKey: String, version: Int = 1): F[BinanceResponse[Unit]] = {
    val query = Query.fromPairs(QueryParams.Keys.listenKey -> listenKey)
    api.endpoint(path, version, query).request[F](DELETE).putKey.fetch(_.consumeUnsafe[Unit])
  }
}

object StreamClient {
  val path = "userDataStream"
}

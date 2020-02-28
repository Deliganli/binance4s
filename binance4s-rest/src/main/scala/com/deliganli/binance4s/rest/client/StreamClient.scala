package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import cats.syntax.flatMap._
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.{ApiAuth, QueryParams}
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.stream.UserDataStream
import org.http4s.Method.{DELETE, POST, PUT}
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class StreamClient[F[_]: Sync: Client: ApiAuth](api: Uri) {
  import StreamClient.path

  def start: F[BinanceResponse[Result[UserDataStream]]] = {
    api.endpoint(path, 1).request[F](POST).putKey.fetch(_.consume[UserDataStream])
  }

  def keepAlive(listenKey: String): F[BinanceResponse[Result[Unit]]] = {
    Sync[F]
      .pure(Query.fromPairs(QueryParams.Keys.listenKey -> listenKey))
      .flatMap(query => api.endpoint(path, 1, query).request[F](PUT).putKey.fetch(_.consume[Unit]))
  }

  def stop(listenKey: String): F[BinanceResponse[Result[Unit]]] = {
    Sync[F]
      .pure(Query.fromPairs(QueryParams.Keys.listenKey -> listenKey))
      .flatMap(query => api.endpoint(path, 1, query).request[F](DELETE).putKey.fetch(_.consume[Unit]))
  }
}

object StreamClient {
  val path = "userDataStream"
}

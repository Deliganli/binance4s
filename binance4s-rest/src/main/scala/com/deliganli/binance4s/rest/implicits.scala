package com.deliganli.binance4s.rest

import cats.effect.Sync
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.request.{ApiAuth, HasQuery, RequestSigner}
import com.deliganli.binance4s.rest.response.base.{BinanceError, BinanceResponse}
import io.circe.Decoder
import org.http4s._
import org.http4s.client.Client

object implicits {

  implicit class QueryOps(query: Query) {

    def withSubQuery[T: HasQuery](subQuery: T): Query = {
      query ++ HasQuery[T].query(subQuery).pairs
    }
  }

  implicit class UriOps(uri: Uri) {

    def endpoint(path: String, version: Int, query: Query = Query.empty): Uri = {
      uri.withPath(s"${uri.path}/v$version/$path").setQueryParams(query.multiParams)
    }

    def request[F[_]](method: Method): Request[F] = Request(method, uri)
  }

  implicit class RequestOps[F[_]: Sync](request: Request[F]) {

    def sign[T](implicit credentials: RequestSigner[F]): F[Request[F]] = {
      credentials.sign(request)
    }

    def putKey[T](implicit key: ApiAuth[F]): Request[F] = {
      key.add(request)
    }

    def fetch[T: Decoder](
      f: Response[F] => F[BinanceResponse[Result[T]]]
    )(
      implicit client: Client[F]
    ): F[BinanceResponse[Result[T]]] = {
      client.fetch(request)(f)
    }
  }

  implicit class ResponseOps[F[_]: Sync](response: Response[F]) {
    def consume[T: Decoder]: F[BinanceResponse[Either[BinanceError, T]]] = BinanceResponse.create(response)
  }
}

package com.deliganli.binance4s.rest

import cats.effect.Sync
import cats.implicits._
import com.deliganli.binance4s.rest.request.{HasQuery, KeyAdder, QuerySigner}
import com.deliganli.binance4s.rest.response.base.{BinanceError, BinanceErrorResponse, BinanceResponse}
import io.circe.Decoder
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
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

    def sign[T](implicit credentials: QuerySigner[F]): F[Request[F]] = {
      credentials.addSignature(request)
    }

    def putKey[T](implicit key: KeyAdder[F]): Request[F] = {
      key.addKey(request)
    }

    def fetch[T: Decoder](
      f: Response[F] => F[BinanceResponse[T]]
    )(implicit client: Client[F]): F[BinanceResponse[T]] = {
      client.fetch[BinanceResponse[T]](request)(f)
    }

  }

  implicit class ResponseOps[F[_]: Sync](response: Response[F]) {

    def consume[T: Decoder]: F[Either[BinanceError, BinanceResponse[T]]] = BinanceResponse.create(response)

    def consumeUnsafe[T: Decoder]: F[BinanceResponse[T]] = consume.flatMap(Sync[F].fromEither)
  }

}

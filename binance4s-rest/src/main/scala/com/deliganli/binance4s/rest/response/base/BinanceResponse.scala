package com.deliganli.binance4s.rest.response.base

import cats.effect.Sync
import cats.implicits._
import com.deliganli.binance4s.rest.response.base.BinanceError.{ApiError, ModelError}
import io.circe.Decoder
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.util.CaseInsensitiveString
import org.http4s.{Headers, Response, Status}

case class BinanceResponse[T](status: Status, body: T, headers: Headers) {
  def usedWeight: Int = headers.get(CaseInsensitiveString("x-mbx-used-weight")).map(_.value.toInt).getOrElse(0)
  def orderCount: Int = headers.get(CaseInsensitiveString("x-mbx-order-count")).map(_.value.toInt).getOrElse(0)
}

object BinanceResponse {

  def apply[F[_], T](response: Response[F], body: T): BinanceResponse[T] = {
    BinanceResponse(response.status, body, response.headers)
  }

  def create[F[_]: Sync, T: Decoder](response: Response[F]): F[BinanceResponse[Either[BinanceError, T]]] =
    response match {
      case Status.Successful(success) =>
        success
          .attemptAs[T]
          .leftMap[BinanceError](ModelError)
          .fold(e => BinanceResponse(success, Left(e)), b => BinanceResponse(success, Right(b)))
      case error =>
        error
          .attemptAs[BinanceErrorResponse]
          .map[BinanceError](ApiError)
          .leftMap[BinanceError](ModelError)
          .merge
          .map(e => BinanceResponse(error, Left(e)))
    }
}

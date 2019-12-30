package com.deliganli.binance4s.rest.response.base

import cats.effect.Sync
import cats.implicits._
import com.deliganli.binance4s.rest.response.base.BinanceError.{ApiError, ModelError}
import io.circe.Decoder
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.util.CaseInsensitiveString
import org.http4s.{DecodeFailure, Headers, Response, Status}

case class BinanceResponse[T](status: Status, body: T, headers: Headers) {
  def usedWeight: Int = headers.get(CaseInsensitiveString("x-mbx-used-weight")).map(_.renderString.toInt).getOrElse(0)
  def orderCount: Int = headers.get(CaseInsensitiveString("x-mbx-order-count")).map(_.renderString.toInt).getOrElse(0)
}

object BinanceResponse {

  def apply[F[_], T](response: Response[F], body: T): BinanceResponse[T] = {
    BinanceResponse(response.status, body, response.headers)
  }

  def create[F[_]: Sync, T: Decoder](response: Response[F]): F[Either[BinanceError, BinanceResponse[T]]] = {
    val meta = ResponseMetaData(response.status, response.headers)

    response match {
      case Status.Successful(success) =>
        success
          .attemptAs[T]
          .map(body => BinanceResponse(success, body))
          .leftMap(err => ModelError(err, meta))
          .leftWiden[BinanceError]
          .value
      case error =>
        error
          .attemptAs[BinanceErrorResponse]
          .map(body => ApiError(BinanceErrorResponse.show.show(body), meta))
          .widen[BinanceError]
          .leftMap(err => ModelError(err, meta))
          .leftWiden[BinanceError]
          .merge
          .map(_.asLeft)
    }
  }
}

case class ResponseMetaData(status: Status, headers: Headers)

sealed abstract class BinanceError(
  metaData: ResponseMetaData,
  message: String,
  cause: Throwable = None.orNull
) extends Exception(message, cause)

object BinanceError {

  case class ApiError(message: String, metaData: ResponseMetaData) extends BinanceError(metaData, message)
  case class ModelError(failure: DecodeFailure, metaData: ResponseMetaData)
      extends BinanceError(metaData, failure.message, failure)

}

package com.deliganli.binance4s.rest.response.base

import org.http4s.DecodeFailure

sealed abstract class BinanceError(message: String, cause: Throwable = None.orNull) extends Exception(message, cause)

object BinanceError {
  case class ApiError(response: BinanceErrorResponse) extends BinanceError(BinanceErrorResponse.show.show(response))

  case class ModelError(failure: DecodeFailure) extends BinanceError(failure.message, failure)
}

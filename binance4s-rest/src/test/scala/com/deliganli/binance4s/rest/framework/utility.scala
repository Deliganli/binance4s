package com.deliganli.binance4s.rest.framework

import cats.effect.IO
import com.deliganli.binance4s.rest.response.base.BinanceResponse

object utility {
  def stdOut[T](response: BinanceResponse[T]) = IO(println(response))
}

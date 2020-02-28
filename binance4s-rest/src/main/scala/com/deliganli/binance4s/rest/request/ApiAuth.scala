package com.deliganli.binance4s.rest.request

import cats.effect.Sync
import com.deliganli.binance4s.rest.request.BinanceHeaders.ApiKey
import org.http4s.Request

trait ApiAuth[F[_]] {
  def add(request: Request[F]): Request[F]
}

object ApiAuth {
  def apply[F[_]](implicit ev: ApiAuth[F]) = ev

  def create[F[_]: Sync](apiKey: ApiKey): ApiAuth[F] = new ApiAuth[F] {
    override def add(request: Request[F]): Request[F] = request.putHeaders(BinanceHeaders.apiKey(apiKey.value))
  }
}

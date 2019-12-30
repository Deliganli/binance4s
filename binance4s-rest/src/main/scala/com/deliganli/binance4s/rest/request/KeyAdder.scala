package com.deliganli.binance4s.rest.request

import cats.effect.Sync
import com.deliganli.binance4s.rest.request.BinanceHeaders.ApiKey
import org.http4s.Request

trait KeyAdder[F[_]] {
  def addKey(request: Request[F]): Request[F]
}

object KeyAdder {
  def apply[F[_]](implicit ev: KeyAdder[F]) = ev

  def create[F[_]: Sync](apiKey: ApiKey): KeyAdder[F] = new KeyAdder[F] {
    override def addKey(request: Request[F]): Request[F] = request.putHeaders(BinanceHeaders.apiKey(apiKey.value))
  }
}

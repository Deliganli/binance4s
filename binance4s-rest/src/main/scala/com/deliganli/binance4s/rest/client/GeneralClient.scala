package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.general.{ExchangeInfo, Time}
import org.http4s.Method.GET
import org.http4s.Uri
import org.http4s.client.Client

class GeneralClient[F[_]: Sync: Client](api: Uri) {

  def ping(version: Int = 1): F[BinanceResponse[Unit]] = {
    api.endpoint("ping", 1).request[F](GET).fetch(_.consumeUnsafe[Unit])
  }

  def time(version: Int = 1): F[BinanceResponse[Time]] = {
    api.endpoint("time", 1).request[F](GET).fetch(_.consumeUnsafe[Time])
  }

  def exchangeInfo(version: Int = 1): F[BinanceResponse[ExchangeInfo]] = {
    api.endpoint("exchangeInfo", 1).request[F](GET).fetch(_.consumeUnsafe[ExchangeInfo])
  }

}

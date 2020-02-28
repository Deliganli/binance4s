package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.general.{ExchangeInfo, Time}
import org.http4s.Method.GET
import org.http4s.Uri
import org.http4s.client.Client

class GeneralClient[F[_]: Sync: Client](api: Uri) {

  def ping: F[BinanceResponse[Result[Unit]]] = {
    api
      .endpoint("ping", 3)
      .request[F](GET)
      .fetch(_.consume[Unit])
  }

  def time: F[BinanceResponse[Result[Time]]] = {
    api
      .endpoint("time", 3)
      .request[F](GET)
      .fetch(_.consume[Time])
  }

  def exchangeInfo: F[BinanceResponse[Result[ExchangeInfo]]] = {
    api
      .endpoint("exchangeInfo", 3)
      .request[F](GET)
      .fetch(_.consume[ExchangeInfo])
  }
}

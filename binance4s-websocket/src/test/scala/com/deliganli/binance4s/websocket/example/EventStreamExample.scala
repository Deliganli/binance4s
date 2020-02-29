package com.deliganli.binance4s.websocket.example

import cats.data.NonEmptySet
import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import com.deliganli.binance4s.common.consts.KlineInterval
import com.deliganli.binance4s.common.http.JDKHttpClient
import com.deliganli.binance4s.websocket.BinanceWSClient
import com.deliganli.binance4s.websocket.request.BinanceRequest
import com.deliganli.binance4s.websocket.response.Event
import com.typesafe.scalalogging.StrictLogging
import fs2.Stream
import io.circe.Error
import org.http4s.client.jdkhttpclient.JdkWSClient

class EventStreamExample[F[_]: ConcurrentEffect: ContextShift: Timer] extends StrictLogging {
  val httpClient                   = JdkWSClient(JDKHttpClient.default)
  val wsClient: BinanceWSClient[F] = BinanceWSClient.create[F](httpClient)

  val requests: NonEmptySet[BinanceRequest] = NonEmptySet.of(
    BinanceRequest.Kline("BTCUSDT", KlineInterval.OneMinute),
    BinanceRequest.Trade("BTCUSDT"),
    BinanceRequest.Ticker("BTCUSDT")
  )

  val eventStream: Stream[F, Either[Error, Event]] = Stream
    .resource(wsClient.connect(requests))
    .flatMap(_.incoming)
}

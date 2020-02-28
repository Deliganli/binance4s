package com.deliganli.binance4s.websocket.test

import java.net.http.HttpClient
import java.net.http.HttpClient.Version
import java.time.Duration

import cats.effect.IO
import com.deliganli.binance4s.common.framework.UnitTest
import com.deliganli.binance4s.websocket.BinanceWSClient
import org.http4s.Uri
import org.http4s.client.jdkhttpclient.JdkWSClient

object WSTestObjects {
  implicit val CS = UnitTest.CS
  implicit val TM  = UnitTest.TM

  val httpClient = HttpClient
    .newBuilder()
    .version(Version.HTTP_2)
    .connectTimeout(Duration.ofSeconds(20))
    .build()

  val ws = BinanceWSClient.create[IO](
    JdkWSClient(httpClient),
    Uri.unsafeFromString("wss://stream.binance.com:9443/ws")
  )
}

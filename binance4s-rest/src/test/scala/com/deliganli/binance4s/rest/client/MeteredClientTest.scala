package com.deliganli.binance4s.rest.client

import cats.effect.IO
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.framework.IntegrationTest
import com.deliganli.binance4s.rest.http.JDKHttpClient
import org.http4s.client.jdkhttpclient.JdkHttpClient

class MeteredClientTest extends IntegrationTest {

  val rest = BinanceRestClient.metered[IO](
    JdkHttpClient(JDKHttpClient.default),
    IntegrationTest.credentials.key
  )

  "historical trades" should "succeed" in {
    noException should be thrownBy rest.historical.trades("BTCUSDT").unsafeRunSync()
  }

  "user stream" should "work" in {
    val streamChain = for {
      dataStream <- rest.user.start
      listenKey  <- IO.fromEither(dataStream.body).map(_.listenKey)
      _          <- rest.user.keepAlive(listenKey)
      _          <- rest.user.stop(listenKey)
    } yield ()

    noException should be thrownBy streamChain.unsafeRunSync()
  }
}

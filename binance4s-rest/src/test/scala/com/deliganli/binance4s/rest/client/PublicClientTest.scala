package com.deliganli.binance4s.rest.client

import cats.effect.IO
import com.deliganli.binance4s.common.consts.{DepthLimit, KlineInterval}
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.framework.IntegrationTest
import com.deliganli.binance4s.rest.http.JDKHttpClient
import org.http4s.client.jdkhttpclient.JdkHttpClient

class PublicClientTest extends IntegrationTest {

  val rest = BinanceRestClient.public[IO](
    JdkHttpClient(JDKHttpClient.default)
  )

  "ping" should "succeed" in {
    noException should be thrownBy rest.general.ping.unsafeRunSync()
  }

  "time" should "succeed" in {
    noException should be thrownBy rest.general.time.unsafeRunSync()
  }

  "exchange info" should "succeed" in {
    noException should be thrownBy rest.general.exchangeInfo.unsafeRunSync()
  }

  val symbol = "BTCUSDT"

  "avg price" should "succeed" in {
    noException should be thrownBy rest.market.avgPrice(symbol).unsafeRunSync()
  }

  "depth" should "succeed" in {
    noException should be thrownBy rest.market.depth(symbol, DepthLimit.Five).unsafeRunSync()
  }

  "klines" should "succeed" in {
    noException should be thrownBy rest.market.klines(symbol, KlineInterval.Daily, 5).unsafeRunSync()
  }

  "book ticker for single currency" should "succeed" in {
    noException should be thrownBy rest.market.ticker.bookAll(symbol).unsafeRunSync()
  }

  "book ticker for all" should "succeed" in {
    noException should be thrownBy rest.market.ticker.book.unsafeRunSync()
  }

  "daily ticker for single currency" should "succeed" in {
    noException should be thrownBy rest.market.ticker.daily(symbol).unsafeRunSync()
  }

  "daily ticker for all" should "succeed" in {
    noException should be thrownBy rest.market.ticker.dailyAll.unsafeRunSync()
  }

  "price ticker for single currency" should "succeed" in {
    noException should be thrownBy rest.market.ticker.price(symbol).unsafeRunSync()
  }

  "price ticker for all" should "succeed" in {
    noException should be thrownBy rest.market.ticker.priceAll.unsafeRunSync()
  }

  "recent trades" should "succeed" in {
    noException should be thrownBy rest.market.trade.recent(symbol, 1).unsafeRunSync()
  }
}

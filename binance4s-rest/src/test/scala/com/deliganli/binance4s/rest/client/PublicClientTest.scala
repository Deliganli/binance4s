package com.deliganli.binance4s.rest.client

import cats.effect.IO
import com.deliganli.binance4s.common.consts.{DepthLimit, KlineInterval}
import com.deliganli.binance4s.common.http.JDKHttpClient
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.framework.IntegrationTest
import org.http4s.client.jdkhttpclient.JdkHttpClient
import com.deliganli.binance4s.rest.response.base.BinanceResponse

class PublicClientTest extends IntegrationTest {

  val rest = BinanceRestClient.public[IO](
    JdkHttpClient(JDKHttpClient.default)
  )

  "ping" should "succeed" in {
    rest.general.ping.unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "time" should "succeed" in {
    rest.general.time.unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "exchange info" should "succeed" in {
    rest.general.exchangeInfo.unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  val symbol = "BTCUSDT"

  "avg price" should "succeed" in {
    rest.market.avgPrice(symbol).unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "depth" should "succeed" in {
    rest.market.depth(symbol, DepthLimit.Five).unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "klines" should "succeed" in {
    rest.market.klines(symbol, KlineInterval.Daily, 5).unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "book ticker for single currency" should "succeed" in {
    rest.market.ticker.bookAll(symbol).unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "book ticker for all" should "succeed" in {
    rest.market.ticker.book.unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "daily ticker for single currency" should "succeed" in {
    rest.market.ticker.daily(symbol).unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "daily ticker for all" should "succeed" in {
    rest.market.ticker.dailyAll.unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "price ticker for single currency" should "succeed" in {
    rest.market.ticker.price(symbol).unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "price ticker for all" should "succeed" in {
    rest.market.ticker.priceAll.unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }

  "recent trades" should "succeed" in {
    rest.market.trade.recent(symbol, 1).unsafeRunSync() should matchPattern { case BinanceResponse(_, Right(_), _) => }
  }
}

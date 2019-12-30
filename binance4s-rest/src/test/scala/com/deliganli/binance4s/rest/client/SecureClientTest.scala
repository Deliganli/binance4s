package com.deliganli.binance4s.rest.client

import cats.effect.IO
import com.deliganli.binance4s.common.consts.{OrderSide, TimeInForce}
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.consts.OrderResponseType
import com.deliganli.binance4s.rest.framework.IntegrationTest
import com.deliganli.binance4s.rest.http.JDKHttpClient
import com.deliganli.binance4s.rest.request.OrderProfile
import com.deliganli.binance4s.rest.request.OrderProfile.MarketQuantity
import org.http4s.client.jdkhttpclient.JdkHttpClient

class SecureClientTest extends IntegrationTest {

  val rest = BinanceRestClient.secure[IO](
    JdkHttpClient(JDKHttpClient.default),
    IntegrationTest.credentials
  )

  "account" should "succeed" in {
    noException should be thrownBy rest.account.info().unsafeRunSync()
  }

  private val symbol = "BTCUSDT"

  "my trades" should "succeed" in {
    noException should be thrownBy rest.account.myTrades(symbol).unsafeRunSync()
  }

  "get all orders" should "succeed" in {
    noException should be thrownBy rest.account.order.getAll(symbol).unsafeRunSync()
  }

  "get open orders" should "succeed" in {
    noException should be thrownBy rest.account.order.getOpen(symbol).unsafeRunSync()
  }

  "open order with limit" should "succeed" in {
    val profile = OrderProfile.Limit(BigDecimal(0.01), BigDecimal(15000), TimeInForce.FOK)
    val result  = rest.account.order.test(symbol, OrderSide.Buy, profile, OrderResponseType.Ack)

    noException should be thrownBy result.unsafeRunSync()
  }

  "open order with market" should "succeed" in {
    val profile = OrderProfile.Market(MarketQuantity.Quantity(BigDecimal(0.01)))
    val result  = rest.account.order.test(symbol, OrderSide.Buy, profile, OrderResponseType.Ack)

    noException should be thrownBy result.unsafeRunSync()
  }

  "open order with stop loss" should "succeed" in {
    val profile = OrderProfile.StopLoss(BigDecimal(0.01), BigDecimal(16000))
    val result  = rest.account.order.test(symbol, OrderSide.Buy, profile, OrderResponseType.Ack)

    noException should be thrownBy result.unsafeRunSync()
  }

  "open order with stop loss limit" should "succeed" in {
    val profile = OrderProfile.StopLossLimit(BigDecimal(0.01), BigDecimal(15000), BigDecimal(16000), TimeInForce.FOK)
    val result  = rest.account.order.test(symbol, OrderSide.Buy, profile, OrderResponseType.Ack)

    noException should be thrownBy result.unsafeRunSync()
  }

  "open order with take profit" should "succeed" in {
    val profile = OrderProfile.TakeProfit(BigDecimal(0.01), BigDecimal(16000))
    val result  = rest.account.order.test(symbol, OrderSide.Buy, profile, OrderResponseType.Ack)

    noException should be thrownBy result.unsafeRunSync()
  }

  "open order with take profit limit" should "succeed" in {
    val profile = OrderProfile.TakeProfitLimit(BigDecimal(0.01), BigDecimal(15000), BigDecimal(15000), TimeInForce.FOK)
    val result  = rest.account.order.test(symbol, OrderSide.Buy, profile, OrderResponseType.Ack)

    noException should be thrownBy result.unsafeRunSync()
  }

  "open order with limit maker" should "succeed" in {
    val profile = OrderProfile.LimitMaker(BigDecimal(0.01), BigDecimal(15000))
    val result  = rest.account.order.test(symbol, OrderSide.Buy, profile, OrderResponseType.Ack)

    noException should be thrownBy result.unsafeRunSync()
  }
}

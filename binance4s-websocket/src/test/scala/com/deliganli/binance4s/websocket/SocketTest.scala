package com.deliganli.binance4s.websocket

import cats.data.NonEmptyList
import com.deliganli.binance4s.common.consts.KlineInterval
import com.deliganli.binance4s.common.framework.UnitTest
import com.deliganli.binance4s.websocket.request.BinanceRequest
import com.deliganli.binance4s.websocket.response._
import com.deliganli.binance4s.websocket.response.consts.DepthLevel
import com.deliganli.binance4s.websocket.test.WSTestObjects
import fs2.Stream
import io.circe.Error

class SocketTest extends UnitTest {

  def connectionOf(
    singleRequest: BinanceRequest,
    interestedMessage: PartialFunction[Either[Error, Event], Event]
  ): Vector[Event] = {
    Stream
      .resource(WSTestObjects.ws.connect(NonEmptyList.of(singleRequest).toNes))
      .flatMap(_.incoming)
      .take(2)
      .collect(interestedMessage)
      .compile
      .toVector
      .unsafeRunSync()
  }

  private val symbol = "BTCUSDT"

  behavior of "Websockets"
  it should "deserialize Klines correct" in {
    val result = connectionOf(
      BinanceRequest.Kline(symbol, KlineInterval.OneMinute),
      { case Right(event: Kline) => event }
    )

    result.size shouldBe 2
  }

  it should "deserialize AggTrades correct" in {
    val result = connectionOf(
      BinanceRequest.AggTrade(symbol),
      { case Right(event: AggTrade) => event }
    )

    result.size shouldBe 2
  }

  it should "deserialize DepthUpdate correct" in {
    val result = connectionOf(
      BinanceRequest.Depth(symbol),
      { case Right(event: DepthUpdate) => event }
    )

    result.size shouldBe 2
  }

  it should "deserialize MiniTicker correct" in {
    val result = connectionOf(
      BinanceRequest.MiniTicker(symbol),
      { case Right(event: MiniTicker) => event }
    )

    result.size shouldBe 2
  }

  it should "deserialize Ticker correct" in {
    val result = connectionOf(
      BinanceRequest.Ticker(symbol),
      { case Right(event: Ticker) => event }
    )

    result.size shouldBe 2
  }

  it should "deserialize Trade correct" in {
    val result = connectionOf(
      BinanceRequest.Trade(symbol),
      { case Right(event: Trade) => event }
    )

    result.size shouldBe 2
  }

  it should "deserialize PartialDepthUpdate correct" in {
    val result = connectionOf(
      BinanceRequest.Depth(symbol, DepthLevel.Five),
      { case Right(event: PartialDepthUpdate) => event }
    )

    result.size shouldBe 2
  }

  //it should "deserialize OrderUpdate correct" in {}
  //it should "deserialize AccountUpdate correct" in {}

}

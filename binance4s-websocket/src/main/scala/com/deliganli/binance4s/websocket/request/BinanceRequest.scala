package com.deliganli.binance4s.websocket.request

import cats.kernel.Order
import com.deliganli.binance4s.common.consts.KlineInterval
import com.deliganli.binance4s.websocket.response.consts.DepthLevel

sealed trait BinanceRequest {

  def streamName: String
}

object BinanceRequest {
  implicit val order: Order[BinanceRequest] = Order.from[BinanceRequest] {
    case (f, s) => f.streamName.compareTo(s.streamName)
  }

  sealed trait SymbolRequest extends BinanceRequest {

    def symbol: String

    def symbolParam: String = symbol.toLowerCase
  }

  case class AggTrade(symbol: String) extends SymbolRequest {
    override val streamName: String = s"$symbolParam@aggTrade"
  }

  case class Trade(symbol: String) extends SymbolRequest {
    override val streamName: String = s"$symbolParam@trade"
  }

  case class Kline(symbol: String, interval: KlineInterval) extends SymbolRequest {
    override val streamName: String = s"$symbolParam@kline_${interval.entryName}"
  }

  case class MiniTicker(symbol: String) extends SymbolRequest {
    override val streamName: String = s"$symbolParam@miniTicker"
  }

  case class AllMiniTicker() extends BinanceRequest {
    override val streamName: String = "!miniTicker@arr"
  }

  case class Ticker(symbol: String) extends SymbolRequest {
    override val streamName: String = s"$symbolParam@ticker"
  }

  case class AllTicker() extends BinanceRequest {
    override val streamName: String = "!ticker@arr"
  }

  case class Depth(symbol: String, level: DepthLevel = DepthLevel.None) extends SymbolRequest {
    override val streamName: String = {
      val suffix = Some(level)
        .filter(_ != DepthLevel.None)
        .map(_.value.toString)
        .getOrElse("")

      s"$symbolParam@depth$suffix"
    }
  }

  case class UserData(listenKey: String) extends BinanceRequest {
    override val streamName: String = listenKey
  }

}

package com.deliganli.binance4s.websocket.response

import cats.implicits._
import com.deliganli.binance4s.websocket.response.consts.{EventCategory, SymbolEventCategory}
import io.circe.Decoder

trait Event

object Event {

  implicit val decoder: Decoder[Event] = Decoder.instance { c =>
    c.downField("e")
      .as[SymbolEventCategory]
      .flatMap {
        case SymbolEventCategory.AggTrade    => c.as[AggTrade]
        case SymbolEventCategory.Trade       => c.as[Trade]
        case SymbolEventCategory.Ticker      => c.as[Ticker]
        case SymbolEventCategory.MiniTicker  => c.as[MiniTicker]
        case SymbolEventCategory.Kline       => c.as[Kline]
        case SymbolEventCategory.Depth       => c.as[DepthUpdate]
        case SymbolEventCategory.OrderUpdate => c.as[OrderUpdate]
      }
      .orElse(
        c.downField("e")
          .as[EventCategory]
          .flatMap {
            case EventCategory.AccountInfoUpdate     => c.as[AccountInfoUpdate]
            case EventCategory.AccountPositionUpdate => c.as[AccountPositionUpdate]
          }
      )
      .orElse(c.as[PartialDepthUpdate])
  }
}

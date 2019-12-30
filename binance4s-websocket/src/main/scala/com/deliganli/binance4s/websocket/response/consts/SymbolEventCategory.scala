package com.deliganli.binance4s.websocket.response.consts

import enumeratum.{CirceEnum, Enum, EnumEntry}

sealed abstract class SymbolEventCategory(override val entryName: String) extends EnumEntry

object SymbolEventCategory extends Enum[SymbolEventCategory] with CirceEnum[SymbolEventCategory] {

  case object AggTrade      extends SymbolEventCategory("aggTrade")
  case object Trade         extends SymbolEventCategory("trade")
  case object Kline         extends SymbolEventCategory("kline")
  case object Ticker        extends SymbolEventCategory("24hrTicker")
  case object MiniTicker    extends SymbolEventCategory("24hrMiniTicker")
  case object Depth         extends SymbolEventCategory("depthUpdate")
  case object OrderUpdate   extends SymbolEventCategory("executionReport")

  val values = findValues
}


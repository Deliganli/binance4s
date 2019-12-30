package com.deliganli.binance4s.common.consts

import enumeratum._

sealed trait SymbolType extends EnumEntry

object SymbolType extends Enum[SymbolType] {
  case object SPOT extends SymbolType

  val values = findValues
}

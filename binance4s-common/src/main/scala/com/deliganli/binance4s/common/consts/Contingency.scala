package com.deliganli.binance4s.common.consts

import enumeratum.{CirceEnum, Enum, EnumEntry}

sealed trait Contingency extends EnumEntry

object Contingency extends Enum[Contingency] with CirceEnum[Contingency] {
  case object OCO extends Contingency

  val values = findValues
}

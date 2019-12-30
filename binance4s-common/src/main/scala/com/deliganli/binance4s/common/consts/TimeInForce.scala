package com.deliganli.binance4s.common.consts

import enumeratum.{CirceEnum, Enum, EnumEntry}

sealed trait TimeInForce extends EnumEntry

object TimeInForce extends Enum[TimeInForce] with CirceEnum[TimeInForce] {

  case object GTC extends TimeInForce
  case object IOC extends TimeInForce
  case object FOK extends TimeInForce

  val values = findValues
}

package com.deliganli.binance4s.common.consts

import enumeratum.EnumEntry.UpperSnakecase
import enumeratum._

sealed trait OrderSide extends EnumEntry

object OrderSide extends Enum[OrderSide] with CirceEnum[OrderSide] {
  case object Buy  extends OrderSide with UpperSnakecase
  case object Sell extends OrderSide with UpperSnakecase

  val values = findValues
}

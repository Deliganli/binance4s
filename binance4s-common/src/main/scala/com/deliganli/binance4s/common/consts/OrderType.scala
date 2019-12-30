package com.deliganli.binance4s.common.consts

import enumeratum.EnumEntry.UpperSnakecase
import enumeratum._

sealed trait OrderType extends EnumEntry

object OrderType extends Enum[OrderType] with CirceEnum[OrderType] {
  case object Limit           extends OrderType with UpperSnakecase
  case object Market          extends OrderType with UpperSnakecase
  case object StopLoss        extends OrderType with UpperSnakecase
  case object StopLossLimit   extends OrderType with UpperSnakecase
  case object TakeProfit      extends OrderType with UpperSnakecase
  case object TakeProfitLimit extends OrderType with UpperSnakecase
  case object LimitMaker      extends OrderType with UpperSnakecase

  val values = findValues
}

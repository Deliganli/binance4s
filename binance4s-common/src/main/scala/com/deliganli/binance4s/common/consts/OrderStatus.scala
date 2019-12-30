package com.deliganli.binance4s.common.consts

import enumeratum.EnumEntry.UpperSnakecase
import enumeratum._

sealed trait OrderStatus extends EnumEntry

object OrderStatus extends Enum[OrderStatus] with CirceEnum[OrderStatus] {
  case object New             extends OrderStatus with UpperSnakecase
  case object PartiallyFilled extends OrderStatus with UpperSnakecase
  case object Filled          extends OrderStatus with UpperSnakecase
  case object Canceled        extends OrderStatus with UpperSnakecase
  case object PendingCancel   extends OrderStatus with UpperSnakecase
  case object Rejected        extends OrderStatus with UpperSnakecase
  case object Expired         extends OrderStatus with UpperSnakecase

  val values = findValues
}

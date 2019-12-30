package com.deliganli.binance4s.common.consts

import enumeratum.EnumEntry.UpperSnakecase
import enumeratum.{CirceEnum, Enum, EnumEntry}

sealed trait OCOOrderStatus extends EnumEntry

object OCOOrderStatus extends Enum[OCOOrderStatus] with CirceEnum[OCOOrderStatus] {
  case object Executing extends OCOOrderStatus with UpperSnakecase
  case object AllDone   extends OCOOrderStatus with UpperSnakecase
  case object Reject    extends OCOOrderStatus with UpperSnakecase

  val values = findValues
}

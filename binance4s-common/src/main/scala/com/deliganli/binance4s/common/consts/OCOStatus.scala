package com.deliganli.binance4s.common.consts

import enumeratum.EnumEntry.UpperSnakecase
import enumeratum.{CirceEnum, Enum, EnumEntry}

sealed trait OCOStatus extends EnumEntry

object OCOStatus extends Enum[OCOStatus] with CirceEnum[OCOStatus] {
  case object Response    extends OCOStatus with UpperSnakecase
  case object ExecStarted extends OCOStatus with UpperSnakecase
  case object AllDone     extends OCOStatus with UpperSnakecase

  val values = findValues
}

package com.deliganli.binance4s.common.consts

import enumeratum.values._

sealed abstract class DepthLimit(override val value: Int, weight: Int) extends IntEnumEntry

object DepthLimit extends IntEnum[DepthLimit] {
  case object Five        extends DepthLimit(5, 1)
  case object Ten         extends DepthLimit(10, 1)
  case object Twenty      extends DepthLimit(20, 1)
  case object Fifty       extends DepthLimit(50, 1)
  case object Hundred     extends DepthLimit(100, 1)
  case object FiveHundred extends DepthLimit(500, 5)
  case object Thousand    extends DepthLimit(1000, 10)

  val values = findValues
}

package com.deliganli.binance4s.rest.response.market

import enumeratum._

sealed abstract class Permissions extends EnumEntry

object Permissions extends Enum[Permissions] with CirceEnum[Permissions] {
  case object SPOT      extends Permissions
  case object MARGIN    extends Permissions
  case object LEVERAGED extends Permissions

  val values = findValues
}

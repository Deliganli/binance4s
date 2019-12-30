package com.deliganli.binance4s.common.consts

import enumeratum._

sealed abstract class KlineInterval(override val entryName: String) extends EnumEntry

object KlineInterval extends Enum[KlineInterval] with CirceEnum[KlineInterval] {

  case object OneMinute      extends KlineInterval("1m")
  case object ThreeMinutes   extends KlineInterval("3m")
  case object FiveMinutes    extends KlineInterval("5m")
  case object FifteenMinutes extends KlineInterval("15m")
  case object HalfHour       extends KlineInterval("30m")
  case object Hourly         extends KlineInterval("1h")
  case object TwoHourly      extends KlineInterval("2h")
  case object FourHourly     extends KlineInterval("4h")
  case object SixHourly      extends KlineInterval("6h")
  case object EightHourly    extends KlineInterval("8h")
  case object TwelveHourly   extends KlineInterval("12h")
  case object Daily          extends KlineInterval("1d")
  case object ThreeDaily     extends KlineInterval("3d")
  case object Weekly         extends KlineInterval("1w")
  case object Monthly        extends KlineInterval("1M")

  val values = findValues
}

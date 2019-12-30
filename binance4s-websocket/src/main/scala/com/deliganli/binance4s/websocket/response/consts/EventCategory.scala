package com.deliganli.binance4s.websocket.response.consts

import enumeratum.{EnumEntry, _}

sealed abstract class EventCategory(override val entryName: String) extends EnumEntry

object EventCategory extends Enum[EventCategory] with CirceEnum[EventCategory] {

  case object AccountInfoUpdate     extends EventCategory("outboundAccountInfo")
  case object AccountPositionUpdate extends EventCategory("outboundAccountPosition")

  val values = findValues
}

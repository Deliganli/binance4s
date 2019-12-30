package com.deliganli.binance4s.websocket.response.consts

import enumeratum.values._

sealed abstract class DepthLevel(val value: Int) extends IntEnumEntry

object DepthLevel extends IntEnum[DepthLevel] with IntCirceEnum[DepthLevel] {

  case object None   extends DepthLevel(0)
  case object Five   extends DepthLevel(5)
  case object Ten    extends DepthLevel(10)
  case object Twenty extends DepthLevel(20)

  val values = findValues
}

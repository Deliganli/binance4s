package com.deliganli.binance4s.common.consts

import enumeratum.EnumEntry.UpperSnakecase
import enumeratum._

sealed trait SymbolStatus extends EnumEntry

object SymbolStatus extends Enum[SymbolStatus] with CirceEnum[SymbolStatus] {
  case object PreTrading   extends SymbolStatus with UpperSnakecase
  case object Trading      extends SymbolStatus with UpperSnakecase
  case object PostTrading  extends SymbolStatus with UpperSnakecase
  case object EndOfDay     extends SymbolStatus with UpperSnakecase
  case object Halt         extends SymbolStatus with UpperSnakecase
  case object AuctionMatch extends SymbolStatus with UpperSnakecase
  case object Break        extends SymbolStatus with UpperSnakecase

  val values = findValues
}

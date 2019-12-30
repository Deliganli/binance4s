package com.deliganli.binance4s.websocket.response

import io.circe.Decoder
import org.joda.time.Instant

case class AccountInfoUpdate(
  eventTime: Instant,
  makerCommissionRate: BigDecimal,
  takerCommissionRate: BigDecimal,
  buyerCommissionRate: BigDecimal,
  sellerCommissionRate: BigDecimal,
  canTrade: Boolean,
  canWithdraw: Boolean,
  canDeposit: Boolean,
  timeOfLasAccountUpdate: Instant,
  balances: Seq[Balance])
    extends UserEvent

object AccountInfoUpdate {
  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val accountUpdateDecoder: Decoder[AccountInfoUpdate] = Decoder.forProduct10(
    "E",
    "m",
    "t",
    "b",
    "s",
    "T",
    "W",
    "D",
    "u",
    "B"
  )(AccountInfoUpdate.apply)
}

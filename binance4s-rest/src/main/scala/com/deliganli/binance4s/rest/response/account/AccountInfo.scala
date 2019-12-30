package com.deliganli.binance4s.rest.response.account

import com.deliganli.binance4s.rest.response.account.AccountInfo.Balance
import io.circe.Decoder

case class AccountInfo(
  makerCommission: Int,
  takerCommission: Int,
  buyerCommission: Int,
  sellerCommission: Int,
  canTrade: Boolean,
  canWithdraw: Boolean,
  canDeposit: Boolean,
  updateTime: Long,
  balances: Seq[Balance]
)

object AccountInfo {

  case class Balance(
    asset: String,
    free: BigDecimal,
    locked: BigDecimal
  )

  object Balance {
    implicit val balanceDecoder: Decoder[Balance] = Decoder.forProduct3(
      "asset",
      "free",
      "locked"
    )(Balance.apply)
  }

  implicit val accountInfoDecoder: Decoder[AccountInfo] = Decoder.forProduct9(
    "makerCommission",
    "takerCommission",
    "buyerCommission",
    "sellerCommission",
    "canTrade",
    "canWithdraw",
    "canDeposit",
    "updateTime",
    "balances"
  )(AccountInfo.apply)
}

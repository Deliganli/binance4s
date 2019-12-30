package com.deliganli.binance4s.rest.response.account

import io.circe.Decoder

case class AccountTrade(
  symbol: String,
  id: Long,
  orderId: Long,
  price: BigDecimal,
  qty: BigDecimal,
  quoteQty: BigDecimal,
  commission: BigDecimal,
  commissionAsset: String,
  time: Long,
  isBuyer: Boolean,
  isMaker: Boolean,
  isBestMatch: Boolean
)

object AccountTrade {
  implicit val accountTradeDecoder = Decoder.forProduct12(
    "symbol",
    "id",
    "orderId",
    "price",
    "qty",
    "quoteQty",
    "commission",
    "commissionAsset",
    "time",
    "isBuyer",
    "isMaker",
    "isBestMatch"
  )(AccountTrade.apply)
}

package com.deliganli.binance4s.rest.response.market

import com.deliganli.binance4s.common.consts.{OrderType, SymbolStatus}
import com.deliganli.binance4s.rest.response.filter.SymbolFilter
import io.circe.{Decoder, Encoder}

case class CurrencySymbol(
  symbol: String,
  status: SymbolStatus,
  baseAsset: String,
  baseAssetPrecision: Int,
  quoteAsset: String,
  quotePrecision: Int,
  orderTypes: Seq[OrderType],
  icebergAllowed: Boolean,
  isSpotTradingAllowed: Boolean,
  isMarginTradingAllowed: Boolean,
  filters: Seq[SymbolFilter]
)

object CurrencySymbol {
  implicit val d: Decoder[CurrencySymbol] = Decoder.forProduct11(
    "symbol",
    "status",
    "baseAsset",
    "baseAssetPrecision",
    "quoteAsset",
    "quotePrecision",
    "orderTypes",
    "icebergAllowed",
    "isSpotTradingAllowed",
    "isMarginTradingAllowed",
    "filters"
  )(CurrencySymbol.apply)

  implicit val e: Encoder[CurrencySymbol] = Encoder.forProduct11(
    "symbol",
    "status",
    "baseAsset",
    "baseAssetPrecision",
    "quoteAsset",
    "quotePrecision",
    "orderTypes",
    "icebergAllowed",
    "isSpotTradingAllowed",
    "isMarginTradingAllowed",
    "filters"
  ) { m =>
    (
      m.symbol,
      m.status,
      m.baseAsset,
      m.baseAssetPrecision,
      m.quoteAsset,
      m.quotePrecision,
      m.orderTypes,
      m.icebergAllowed,
      m.isSpotTradingAllowed,
      m.isMarginTradingAllowed,
      m.filters
    )
  }
}

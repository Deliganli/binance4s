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
  quoteAssetPrecision: Int,
  baseCommissionPrecision: Int,
  quoteCommissionPrecision: Int,
  orderTypes: Seq[OrderType],
  icebergAllowed: Boolean,
  ocoAllowed: Boolean,
  quoteOrderQtyMarketAllowed: Boolean,
  isSpotTradingAllowed: Boolean,
  isMarginTradingAllowed: Boolean,
  filters: Seq[SymbolFilter],
  permissions: Seq[Permissions])

object CurrencySymbol {

  implicit val d: Decoder[CurrencySymbol] = Decoder.forProduct17(
    "symbol",
    "status",
    "baseAsset",
    "baseAssetPrecision",
    "quoteAsset",
    "quotePrecision",
    "quoteAssetPrecision",
    "baseCommissionPrecision",
    "quoteCommissionPrecision",
    "orderTypes",
    "icebergAllowed",
    "ocoAllowed",
    "quoteOrderQtyMarketAllowed",
    "isSpotTradingAllowed",
    "isMarginTradingAllowed",
    "filters",
    "permissions"
  )(CurrencySymbol.apply)

  implicit val e: Encoder[CurrencySymbol] = Encoder.forProduct17(
    "symbol",
    "status",
    "baseAsset",
    "baseAssetPrecision",
    "quoteAsset",
    "quotePrecision",
    "quoteAssetPrecision",
    "baseCommissionPrecision",
    "quoteCommissionPrecision",
    "orderTypes",
    "icebergAllowed",
    "ocoAllowed",
    "quoteOrderQtyMarketAllowed",
    "isSpotTradingAllowed",
    "isMarginTradingAllowed",
    "filters",
    "permissions"
  ) { m =>
    (
      m.symbol,
      m.status,
      m.baseAsset,
      m.baseAssetPrecision,
      m.quoteAsset,
      m.quotePrecision,
      m.quoteAssetPrecision,
      m.baseCommissionPrecision,
      m.quoteCommissionPrecision,
      m.orderTypes,
      m.icebergAllowed,
      m.ocoAllowed,
      m.quoteOrderQtyMarketAllowed,
      m.isSpotTradingAllowed,
      m.isMarginTradingAllowed,
      m.filters,
      m.permissions
    )
  }
}

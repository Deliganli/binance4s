package com.deliganli.binance4s.rest.response.filter

import io.circe.syntax._
import io.circe.{Decoder, Encoder}

sealed trait SymbolFilter

object SymbolFilter {
  implicit val d: Decoder[SymbolFilter] = Decoder.instance { c =>
    c.downField("filterType").as[String].flatMap {
      case "PRICE_FILTER"           => c.as[PriceFilter]
      case "PERCENT_PRICE"          => c.as[PercentPriceFilter]
      case "LOT_SIZE"               => c.as[LotSizeFilter]
      case "MIN_NOTIONAL"           => c.as[MinNotional]
      case "ICEBERG_PARTS"          => c.as[IcebergParts]
      case "MARKET_LOT_SIZE"        => c.as[MarketLotSize]
      case "MAX_NUM_ORDERS"         => c.as[MaxNumOrders]
      case "MAX_NUM_ALGO_ORDERS"    => c.as[MaxNumAlgoOrders]
      case "MAX_NUM_ICEBERG_ORDERS" => c.as[MaxNumIcebergOrders]
    }
  }

  implicit val e: Encoder[SymbolFilter] = Encoder.instance {
    case e: PriceFilter         => e.asJson
    case e: PercentPriceFilter  => e.asJson
    case e: LotSizeFilter       => e.asJson
    case e: MinNotional         => e.asJson
    case e: IcebergParts        => e.asJson
    case e: MarketLotSize       => e.asJson
    case e: MaxNumOrders        => e.asJson
    case e: MaxNumAlgoOrders    => e.asJson
    case e: MaxNumIcebergOrders => e.asJson
  }

  case class PriceFilter(
    filterType: String,
    minPrice: BigDecimal,
    maxPrice: BigDecimal,
    tickSize: Double
  ) extends SymbolFilter

  object PriceFilter {
    implicit val d: Decoder[PriceFilter] = Decoder.forProduct4(
      "filterType",
      "minPrice",
      "maxPrice",
      "tickSize"
    )(PriceFilter.apply)

    implicit val e: Encoder[PriceFilter] = Encoder.forProduct4(
      "filterType",
      "minPrice",
      "maxPrice",
      "tickSize"
    ) { m =>
      (
        m.filterType,
        m.minPrice,
        m.maxPrice,
        m.tickSize
      )
    }
  }

  case class PercentPriceFilter(
    filterType: String,
    multiplierUp: Double,
    multiplierDown: Double,
    avgPriceMins: Int
  ) extends SymbolFilter

  object PercentPriceFilter {
    implicit val d: Decoder[PercentPriceFilter] = Decoder.forProduct4(
      "filterType",
      "multiplierUp",
      "multiplierDown",
      "avgPriceMins"
    )(PercentPriceFilter.apply)

    implicit val e: Encoder[PercentPriceFilter] = Encoder.forProduct4(
      "filterType",
      "multiplierUp",
      "multiplierDown",
      "avgPriceMins"
    ) { m =>
      (
        m.filterType,
        m.multiplierUp,
        m.multiplierDown,
        m.avgPriceMins
      )
    }
  }

  case class LotSizeFilter(
    filterType: String,
    minQty: BigDecimal,
    maxQty: BigDecimal,
    stepSize: Double
  ) extends SymbolFilter

  object LotSizeFilter {
    implicit val d: Decoder[LotSizeFilter] = Decoder.forProduct4(
      "filterType",
      "minQty",
      "maxQty",
      "stepSize"
    )(LotSizeFilter.apply)

    implicit val e: Encoder[LotSizeFilter] = Encoder.forProduct4(
      "filterType",
      "minQty",
      "maxQty",
      "stepSize"
    ) { m =>
      (
        m.filterType,
        m.minQty,
        m.maxQty,
        m.stepSize
      )
    }
  }

  case class MinNotional(
    filterType: String,
    minNotional: BigDecimal,
    applyToMarket: Boolean,
    avgPriceMins: Int
  ) extends SymbolFilter

  object MinNotional {
    implicit val d: Decoder[MinNotional] = Decoder.forProduct4(
      "filterType",
      "minNotional",
      "applyToMarket",
      "avgPriceMins"
    )(MinNotional.apply)

    implicit val e: Encoder[MinNotional] = Encoder.forProduct4(
      "filterType",
      "minNotional",
      "applyToMarket",
      "avgPriceMins"
    ) { m =>
      (
        m.filterType,
        m.minNotional,
        m.applyToMarket,
        m.avgPriceMins
      )
    }
  }

  case class IcebergParts(
    filterType: String,
    limit: Int
  ) extends SymbolFilter

  object IcebergParts {
    implicit val d: Decoder[IcebergParts] = Decoder.forProduct2(
      "filterType",
      "limit"
    )(IcebergParts.apply)

    implicit val e: Encoder[IcebergParts] = Encoder.forProduct2(
      "filterType",
      "limit"
    ) { m =>
      (
        m.filterType,
        m.limit
      )
    }
  }

  case class MarketLotSize(
    filterType: String,
    minQty: BigDecimal,
    maxQty: BigDecimal,
    stepSize: Double
  ) extends SymbolFilter

  object MarketLotSize {
    implicit val d: Decoder[MarketLotSize] = Decoder.forProduct4(
      "filterType",
      "minQty",
      "maxQty",
      "stepSize"
    )(MarketLotSize.apply)

    implicit val e: Encoder[MarketLotSize] = Encoder.forProduct4(
      "filterType",
      "minQty",
      "maxQty",
      "stepSize"
    ) { m =>
      (
        m.filterType,
        m.minQty,
        m.maxQty,
        m.stepSize
      )
    }
  }

  case class MaxNumOrders(
    filterType: String,
    limit: Int
  ) extends SymbolFilter

  object MaxNumOrders {
    implicit val d: Decoder[MaxNumOrders] = Decoder.forProduct2(
      "filterType",
      "limit"
    )(MaxNumOrders.apply)

    implicit val e: Encoder[MaxNumOrders] = Encoder.forProduct2(
      "filterType",
      "limit"
    ) { m =>
      (
        m.filterType,
        m.limit
      )
    }
  }

  case class MaxNumAlgoOrders(
    filterType: String,
    maxNumAlgoOrders: Int
  ) extends SymbolFilter

  object MaxNumAlgoOrders {
    implicit val d: Decoder[MaxNumAlgoOrders] = Decoder.forProduct2(
      "filterType",
      "maxNumAlgoOrders"
    )(MaxNumAlgoOrders.apply)

    implicit val e: Encoder[MaxNumAlgoOrders] = Encoder.forProduct2(
      "filterType",
      "maxNumAlgoOrders"
    ) { m =>
      (
        m.filterType,
        m.maxNumAlgoOrders
      )
    }
  }

  case class MaxNumIcebergOrders(
    filterType: String,
    maxNumIcebergOrders: Int
  ) extends SymbolFilter

  object MaxNumIcebergOrders {
    implicit val d: Decoder[MaxNumIcebergOrders] = Decoder.forProduct2(
      "filterType",
      "maxNumIcebergOrders"
    )(MaxNumIcebergOrders.apply)

    implicit val e: Encoder[MaxNumIcebergOrders] = Encoder.forProduct2(
      "filterType",
      "maxNumIcebergOrders"
    ) { m =>
      (
        m.filterType,
        m.maxNumIcebergOrders
      )
    }
  }

}

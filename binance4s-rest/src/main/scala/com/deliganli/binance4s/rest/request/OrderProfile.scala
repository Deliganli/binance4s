package com.deliganli.binance4s.rest.request

import com.deliganli.binance4s.common.consts.{OrderType, TimeInForce}
import com.deliganli.binance4s.rest.request.QueryParams.Formatters._
import org.http4s.Query

sealed trait OrderProfile

object OrderProfile {

  sealed trait MarketQuantity

  object MarketQuantity {
    case class Quantity(value: BigDecimal)           extends MarketQuantity
    case class QuoteOrderQuantity(value: BigDecimal) extends MarketQuantity
  }

  // format: off
  case class Limit(quantity: BigDecimal, price: BigDecimal, timeInForce: TimeInForce)                                        extends OrderProfile
  case class Market(quantity: MarketQuantity)                                                                                extends OrderProfile
  case class StopLoss(quantity: BigDecimal, stopPrice: BigDecimal)                                                           extends OrderProfile
  case class StopLossLimit(quantity: BigDecimal, price: BigDecimal, stopPrice: BigDecimal, timeInForce: TimeInForce)         extends OrderProfile
  case class TakeProfit(quantity: BigDecimal, stopPrice: BigDecimal)                                                         extends OrderProfile
  case class TakeProfitLimit(quantity: BigDecimal, price: BigDecimal, stopPrice: BigDecimal, timeInForce: TimeInForce)       extends OrderProfile
  case class LimitMaker(quantity: BigDecimal, price: BigDecimal)                                                             extends OrderProfile
  // format: on

  object Limit {

    implicit val limitQuery: HasQuery[Limit] = HasQuery.create[Limit] { profile =>
      Query.empty
        .withQueryParam(QueryParams.Keys.quantity, profile.quantity)
        .withQueryParam(QueryParams.Keys.orderType, OrderType.Limit.entryName)
        .withQueryParam(QueryParams.Keys.price, profile.price)
        .withQueryParam(QueryParams.Keys.timeInForce, profile.timeInForce)
    }
  }

  object Market {

    implicit val marketQuery: HasQuery[Market] = HasQuery.create[Market] { profile =>
      val quantityPair = profile.quantity match {
        case MarketQuantity.Quantity(value)           => QueryParams.Keys.quantity      -> value
        case MarketQuantity.QuoteOrderQuantity(value) => QueryParams.Keys.quoteOrderQty -> value
      }

      Query
        .fromPairs(QueryParams.Keys.orderType -> OrderType.Market.entryName)
        .withQueryParam(quantityPair._1, quantityPair._2)
    }
  }

  object StopLoss {

    implicit val stopLossQuery: HasQuery[StopLoss] = HasQuery.create[StopLoss] { profile =>
      Query.empty
        .withQueryParam(QueryParams.Keys.quantity, profile.quantity)
        .withQueryParam(QueryParams.Keys.orderType, OrderType.StopLoss.entryName)
        .withQueryParam(QueryParams.Keys.stopPrice, profile.stopPrice)
    }
  }

  object StopLossLimit {

    implicit val stopLossLimitQuery: HasQuery[StopLossLimit] = HasQuery.create[StopLossLimit] { profile =>
      Query.empty
        .withQueryParam(QueryParams.Keys.quantity, profile.quantity)
        .withQueryParam(QueryParams.Keys.orderType, OrderType.StopLossLimit.entryName)
        .withQueryParam(QueryParams.Keys.timeInForce, profile.timeInForce)
        .withQueryParam(QueryParams.Keys.price, profile.price)
        .withQueryParam(QueryParams.Keys.stopPrice, profile.stopPrice)
    }
  }

  object TakeProfit {

    implicit val takeProfitQuery: HasQuery[TakeProfit] = HasQuery.create[TakeProfit] { profile =>
      Query.empty
        .withQueryParam(QueryParams.Keys.quantity, profile.quantity)
        .withQueryParam(QueryParams.Keys.orderType, OrderType.TakeProfit.entryName)
        .withQueryParam(QueryParams.Keys.stopPrice, profile.stopPrice)
    }
  }

  object TakeProfitLimit {

    implicit val takeProfitLimitQuery: HasQuery[TakeProfitLimit] = HasQuery.create[TakeProfitLimit] { profile =>
      Query.empty
        .withQueryParam(QueryParams.Keys.quantity, profile.quantity)
        .withQueryParam(QueryParams.Keys.orderType, OrderType.TakeProfitLimit.entryName)
        .withQueryParam(QueryParams.Keys.timeInForce, profile.timeInForce)
        .withQueryParam(QueryParams.Keys.price, profile.price)
        .withQueryParam(QueryParams.Keys.stopPrice, profile.stopPrice)
    }
  }

  object LimitMaker {

    implicit val limitMakerQuery: HasQuery[LimitMaker] = HasQuery.create[LimitMaker] { profile =>
      Query.empty
        .withQueryParam(QueryParams.Keys.quantity, profile.quantity)
        .withQueryParam(QueryParams.Keys.orderType, OrderType.LimitMaker.entryName)
        .withQueryParam(QueryParams.Keys.price, profile.price)
    }
  }
}

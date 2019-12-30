package com.deliganli.binance4s.rest.response.filter

import io.circe.syntax._
import io.circe.{Decoder, Encoder}

sealed trait ExchangeFilter

object ExchangeFilter {
  implicit val d: Decoder[ExchangeFilter] = Decoder.instance { c =>
    c.downField("filterType").as[String].flatMap {
      case "EXCHANGE_MAX_NUM_ORDERS"  => c.as[ExchangeMaxNumOrders]
      case "EXCHANGE_MAX_ALGO_ORDERS" => c.as[ExchangeMaxNumAlgoOrders]
    }
  }

  implicit val e: Encoder[ExchangeFilter] = Encoder.instance {
    case e: ExchangeMaxNumOrders     => e.asJson
    case e: ExchangeMaxNumAlgoOrders => e.asJson
  }

  case class ExchangeMaxNumOrders(
    filterType: String,
    maxNumOrders: Int
  ) extends ExchangeFilter

  object ExchangeMaxNumOrders {
    implicit val d: Decoder[ExchangeMaxNumOrders] = Decoder.forProduct2(
      "filterType",
      "maxNumOrders"
    )(ExchangeMaxNumOrders.apply)

    implicit val e: Encoder[ExchangeMaxNumOrders] = Encoder.forProduct2(
      "filterType",
      "maxNumOrders"
    ) { m =>
      (m.filterType, m.maxNumOrders)
    }
  }

  case class ExchangeMaxNumAlgoOrders(
    filterType: String,
    maxNumAlgoOrders: Int
  ) extends ExchangeFilter

  object ExchangeMaxNumAlgoOrders {
    implicit val d: Decoder[ExchangeMaxNumAlgoOrders] = Decoder.forProduct2(
      "filterType",
      "maxNumAlgoOrders"
    )(ExchangeMaxNumAlgoOrders.apply)

    implicit val e: Encoder[ExchangeMaxNumAlgoOrders] = Encoder.forProduct2(
      "filterType",
      "maxNumAlgoOrders"
    ) { m =>
      (m.filterType, m.maxNumAlgoOrders)
    }
  }
}

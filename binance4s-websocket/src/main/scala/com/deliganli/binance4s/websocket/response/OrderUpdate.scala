package com.deliganli.binance4s.websocket.response

import com.deliganli.binance4s.common.consts.{OrderSide, OrderStatus, OrderType, TimeInForce}
import com.deliganli.binance4s.websocket.response.OrderUpdate.{Commission, Cumulative, Current, Last, OrderIds}
import io.circe.{Decoder, HCursor}
import java.time.Instant

case class OrderUpdate(
  eventTime: Instant,
  symbol: String,
  orderIds: OrderIds,
  side: OrderSide,
  orderType: OrderType,
  timeInForce: TimeInForce,
  orderQuantity: BigDecimal,
  orderPrice: BigDecimal,
  stopPrice: BigDecimal,
  icebergQuantity: BigDecimal,
  cumulative: Cumulative,
  orderRejectReason: Option[String],
  current: Current,
  last: Last,
  commission: Commission,
  transactionTime: Instant,
  tradeId: Long,
  isTheOrderWorking: Boolean,
  isThisTradeMakerSide: Boolean,
  orderCreationTime: Instant,
  g: Long,
  I: Long,
  M: Boolean)
    extends UserEvent

object OrderUpdate {

  case class OrderIds(orderId: Long, clientOrderId: String, originalClientOrderId: Option[String])

  case class Current(executionType: OrderStatus, orderStatus: OrderStatus)

  case class Last(executedQuantity: BigDecimal, executedPrice: BigDecimal, quoteAssetTransactedQuantity: BigDecimal)

  case class Commission(amount: BigDecimal, asset: Option[String])

  case class Cumulative(filledQuantity: BigDecimal, quoteAssetTransactedQuantity: BigDecimal)

  import com.deliganli.binance4s.common.formatters.Decoders.decodeInstant

  implicit val d: Decoder[OrderUpdate] = (c: HCursor) => {
    for {
      eventTime                              <- c.downField("E").as[Instant]
      symbol                                 <- c.downField("s").as[String]
      clientOrderId                          <- c.downField("c").as[String]
      side                                   <- c.downField("S").as[OrderSide]
      orderType                              <- c.downField("o").as[OrderType]
      timeInForce                            <- c.downField("f").as[TimeInForce]
      orderQuantity                          <- c.downField("q").as[BigDecimal]
      orderPrice                             <- c.downField("p").as[BigDecimal]
      stopPrice                              <- c.downField("P").as[BigDecimal]
      icebergQuantity                        <- c.downField("F").as[BigDecimal]
      ignore_g                               <- c.downField("g").as[Long]
      originalClientOrderId                  <- c.downField("C").as[String].map(v => Option(v).filterNot(_ == "null"))
      currentExecutionType                   <- c.downField("x").as[OrderStatus]
      currentOrderStatus                     <- c.downField("X").as[OrderStatus]
      orderRejectReason                      <- c.downField("r").as[String].map(v => Option(v).filterNot(_ == "NONE")) // TODO: refine type
      orderId                                <- c.downField("i").as[Long]
      lastExecutedQuantity                   <- c.downField("l").as[BigDecimal]
      cumulativeFilledQuantity               <- c.downField("z").as[BigDecimal]
      lastExecutedPrice                      <- c.downField("L").as[BigDecimal]
      commissionAmount                       <- c.downField("n").as[BigDecimal]
      commissionAsset                        <- c.downField("N").as[Option[String]] // TODO: can it be null?
      transactionTime                        <- c.downField("T").as[Instant]
      tradeId                                <- c.downField("t").as[Long]
      ignore_I                               <- c.downField("I").as[Long]
      isTheOrderWorking                      <- c.downField("w").as[Boolean]
      isThisTradeMakerSide                   <- c.downField("m").as[Boolean]
      ignore_M                               <- c.downField("M").as[Boolean]
      orderCreationTime                      <- c.downField("O").as[Instant]
      cumulativeQuoteAssetTransactedQuantity <- c.downField("Z").as[BigDecimal]
      lastQuoteAssetTransactedQuantity       <- c.downField("Y").as[BigDecimal]
    } yield {
      new OrderUpdate(
        eventTime = eventTime,
        symbol = symbol,
        orderIds = OrderIds(
          orderId,
          clientOrderId,
          originalClientOrderId
        ),
        side = side,
        orderType = orderType,
        timeInForce = timeInForce,
        orderQuantity,
        orderPrice,
        stopPrice,
        icebergQuantity,
        orderRejectReason = orderRejectReason,
        cumulative = Cumulative(
          cumulativeFilledQuantity,
          cumulativeQuoteAssetTransactedQuantity
        ),
        current = Current(
          currentExecutionType,
          currentOrderStatus
        ),
        last = Last(
          lastExecutedQuantity,
          lastExecutedPrice,
          lastQuoteAssetTransactedQuantity
        ),
        commission = Commission(
          commissionAmount,
          commissionAsset
        ),
        transactionTime = transactionTime,
        tradeId = tradeId,
        isTheOrderWorking = isTheOrderWorking,
        isThisTradeMakerSide = isThisTradeMakerSide,
        orderCreationTime = orderCreationTime,
        g = ignore_g,
        I = ignore_I,
        M = ignore_M
      )
    }
  }
}

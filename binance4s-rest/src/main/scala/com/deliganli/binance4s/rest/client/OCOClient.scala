package com.deliganli.binance4s.rest.client

import java.util.concurrent.TimeUnit

import cats.effect.{Clock, Sync}
import cats.implicits._
import com.deliganli.binance4s.common.consts.{OrderSide, TimeInForce}
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.QueryParams.Formatters._
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.request.{ApiAuth, OCOQuery, OrderListId, RequestSigner}
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import com.deliganli.binance4s.rest.response.order.{OCOOrder, OCOOrderAck, OrderResponseQuery}
import io.circe.Decoder
import org.http4s.Method.{DELETE, GET, POST}
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class OCOClient[F[_]: Sync: Client: Clock: ApiAuth: RequestSigner](api: Uri) {

  def create[T: OrderResponseQuery: Decoder](
    symbol: String,
    side: OrderSide,
    quantity: BigDecimal,
    price: BigDecimal,
    stopPrice: BigDecimal,
    listClientOrderId: String,
    limitClientOrderId: String,
    limitIcebergQty: BigDecimal,
    stopClientOrderId: String,
    stopLimitPrice: BigDecimal,
    stopIcebergQty: BigDecimal,
    stopLimitTimeInForce: TimeInForce,
    recvWindow: Option[Long] = None
  ): F[BinanceResponse[Result[T]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        OrderResponseQuery[T].query
          .withQueryParam(Keys.symbol, symbol)
          .withQueryParam(Keys.side, side)
          .withQueryParam(Keys.price, price)
          .withQueryParam(Keys.stopPrice, stopPrice)
          .withQueryParam(Keys.listClientOrderId, listClientOrderId)
          .withQueryParam(Keys.limitClientOrderId, limitClientOrderId)
          .withQueryParam(Keys.limitIcebergQty, limitIcebergQty)
          .withQueryParam(Keys.stopClientOrderId, stopClientOrderId)
          .withQueryParam(Keys.stopLimitPrice, stopLimitPrice)
          .withQueryParam(Keys.stopIcebergQty, stopIcebergQty)
          .withQueryParam(Keys.stopLimitTimeInForce, stopLimitTimeInForce)
          .withQueryParam(Keys.timestamp, timestamp)
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
      }
      .flatMap(query => api.endpoint("order/oco", 3, query).request[F](POST).putKey.sign)
      .flatMap(_.fetch(_.consume[T]))
  }

  def cancel(
    symbol: String,
    orderListId: OrderListId,
    newClientOrderId: Option[String],
    recvWindow: Option[Long] = None
  ): F[BinanceResponse[Result[OCOOrderAck]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query.empty
          .withQueryParam(Keys.symbol, symbol)
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withSubQuery(orderListId)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint("orderList", 3, query).request[F](DELETE).putKey.sign)
      .flatMap(_.fetch(_.consume[OCOOrderAck]))
  }

  def get(orderListId: OrderListId, recvWindow: Option[Long] = None): F[BinanceResponse[Result[OCOOrderAck]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query.empty
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withSubQuery(orderListId)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint("orderList", 3, query).request[F](GET).putKey.sign)
      .flatMap(_.fetch(_.consume[OCOOrderAck]))
  }

  def getAll(
    query: OCOQuery,
    limit: Option[Int] = None,
    recvWindow: Option[Long] = None
  ): F[BinanceResponse[Result[Seq[OCOOrder]]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query.empty
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withSubQuery(query)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint("allOrderList", 3, query).request[F](GET).putKey.sign)
      .flatMap(_.fetch(_.consume[Seq[OCOOrder]]))
  }
}

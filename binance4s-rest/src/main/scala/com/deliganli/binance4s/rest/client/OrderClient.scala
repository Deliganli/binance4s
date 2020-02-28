package com.deliganli.binance4s.rest.client

import java.util.concurrent.TimeUnit

import cats.effect.{Clock, Sync}
import cats.implicits._
import com.deliganli.binance4s.common.consts.OrderSide
import com.deliganli.binance4s.rest.BinanceRestClient.Result
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.QueryParams.Formatters.orderSideEncoder
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.request._
import com.deliganli.binance4s.rest.response.base.{BinanceError, BinanceResponse}
import com.deliganli.binance4s.rest.response.order.{CancelOrder, Order, OrderResponseQuery}
import io.circe.Decoder
import org.http4s.Method.{DELETE, GET, POST}
import org.http4s.client.Client
import org.http4s.{Query, Uri}

class OrderClient[F[_]: Sync: Clock: Client: ApiAuth: RequestSigner](api: Uri) {

  def create[T: OrderResponseQuery: Decoder](
    symbol: String,
    side: OrderSide,
    profile: OrderProfile
  ): F[BinanceResponse[Result[T]]] = call[T]("order")(symbol, side, profile)

  def test(symbol: String, side: OrderSide, profile: OrderProfile): F[BinanceResponse[Result[Unit]]] =
    call[Unit]("order/test")(symbol, side, profile)

  private def call[T: OrderResponseQuery: Decoder](
    endpoint: String
  )(
    symbol: String,
    side: OrderSide,
    profile: OrderProfile
  ) = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        OrderResponseQuery[T].query
          .withSubQuery(profile)
          .withQueryParam(Keys.symbol, symbol)
          .withQueryParam(Keys.side, side)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint(endpoint, 3, query).request[F](POST).putKey.sign)
      .flatMap(_.fetch(_.consume[T]))
  }

  def get(symbol: String, orderId: OrderId, recvWindow: Option[Long]): F[BinanceResponse[Result[Order]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withSubQuery(orderId)
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint("order", 3, query).request[F](GET).putKey.sign)
      .flatMap(_.fetch(_.consume[Order]))
  }

  def cancel(
    symbol: String,
    orderId: OrderId,
    newClientOrderId: Option[String],
    recvWindow: Option[Long] = None
  ): F[BinanceResponse[Either[BinanceError, CancelOrder]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withSubQuery(orderId)
          .withOptionQueryParam(Keys.newClientOrderId, newClientOrderId)
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint("order", 3, query).request[F](DELETE).putKey.sign)
      .flatMap(_.fetch(_.consume[CancelOrder]))
  }

  def getOpen(symbol: String, recvWindow: Option[Long] = None): F[BinanceResponse[Result[Seq[Order]]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withQueryParam(Keys.timestamp, timestamp)
      }
      .flatMap(query => api.endpoint("openOrders", 3, query).request[F](GET).putKey.sign)
      .flatMap(_.fetch(_.consume[Seq[Order]]))
  }

  def getAll(
    symbol: String,
    orderId: Option[Long] = None,
    limit: Option[Int] = None,
    period: Period = Period.Empty,
    recvWindow: Option[Long] = None
  ): F[BinanceResponse[Result[Seq[Order]]]] = {
    Clock[F]
      .realTime(TimeUnit.MILLISECONDS)
      .map { timestamp =>
        Query
          .fromPairs(Keys.symbol -> symbol)
          .withOptionQueryParam(Keys.orderId, orderId)
          .withOptionQueryParam(Keys.limit, limit)
          .withOptionQueryParam(Keys.recvWindow, recvWindow)
          .withQueryParam(Keys.timestamp, timestamp)
          .withSubQuery(period)
      }
      .flatMap(query => api.endpoint("allOrders", 3, query).request[F](GET).putKey.sign)
      .flatMap(_.fetch(_.consume[Seq[Order]]))
  }
}

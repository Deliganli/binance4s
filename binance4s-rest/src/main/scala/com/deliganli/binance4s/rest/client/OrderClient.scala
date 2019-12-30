package com.deliganli.binance4s.rest.client

import cats.effect.Sync
import cats.implicits._
import com.deliganli.binance4s.common.consts.OrderSide
import com.deliganli.binance4s.rest.consts.OrderResponseType
import com.deliganli.binance4s.rest.implicits._
import com.deliganli.binance4s.rest.request.QueryParams.Formatters.{instantEncoder, orderSideEncoder}
import com.deliganli.binance4s.rest.request.QueryParams.Keys
import com.deliganli.binance4s.rest.request._
import com.deliganli.binance4s.rest.response.account.{CancelOrder, NewOrderResponse, Order}
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import io.circe.Decoder
import org.http4s.Method.{DELETE, GET, POST}
import org.http4s.client.Client
import org.http4s.{Query, Uri}
import org.joda.time.Instant

class OrderClient[F[_]: Sync: Client: KeyAdder: QuerySigner](api: Uri) {

  def create[T <: NewOrderResponse: Decoder, P <: OrderProfile: HasQuery](
    symbol: String,
    side: OrderSide,
    profile: P,
    orderResponseType: OrderResponseType[T],
    timestamp: Instant = Instant.now(),
    version: Int = 3
  ): F[BinanceResponse[T]] = {
    val query = Query.empty
      .withSubQuery(profile)
      .withSubQuery(orderResponseType)
      .withQueryParam(Keys.symbol, symbol)
      .withQueryParam(Keys.side, side)
      .withQueryParam(Keys.timestamp, timestamp)

    for {
      request <- api.endpoint(s"order", version, query).request[F](POST).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[T])
    } yield result
  }

  def test[T <: NewOrderResponse: Decoder, P <: OrderProfile: HasQuery](
    symbol: String,
    side: OrderSide,
    profile: P,
    orderResponseType: OrderResponseType[T],
    timestamp: Instant = Instant.now(),
    version: Int = 3
  ): F[BinanceResponse[Unit]] = {
    val query = Query.empty
      .withSubQuery(profile)
      .withSubQuery(orderResponseType)
      .withQueryParam(Keys.symbol, symbol)
      .withQueryParam(Keys.side, side)
      .withQueryParam(Keys.timestamp, timestamp)

    for {
      request <- api.endpoint(s"order/test", version, query).request[F](POST).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[Unit])
    } yield result
  }

  def get(
    symbol: String,
    orderId: OrderId,
    recvWindow: Option[Long],
    timestamp: Instant = Instant.now(),
    version: Int = 3
  ): F[BinanceResponse[Order]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withSubQuery(orderId)
      .withOptionQueryParam(Keys.recvWindow, recvWindow)
      .withQueryParam(Keys.timestamp, timestamp)

    for {
      request <- api.endpoint("order", version, query).request[F](GET).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[Order])
    } yield result
  }

  def cancel(
    symbol: String,
    orderId: OrderId,
    newClientOrderId: Option[String],
    recvWindow: Option[Long] = None,
    timestamp: Instant = Instant.now(),
    version: Int = 1
  ): F[BinanceResponse[CancelOrder]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withSubQuery(orderId)
      .withOptionQueryParam(Keys.newClientOrderId, newClientOrderId)
      .withOptionQueryParam(Keys.recvWindow, recvWindow)
      .withQueryParam(Keys.timestamp, timestamp)

    for {
      request <- api.endpoint("order", version, query).request[F](DELETE).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[CancelOrder])
    } yield result
  }

  def getOpen(
    symbol: String,
    recvWindow: Option[Long] = None,
    timestamp: Instant = Instant.now(),
    version: Int = 3
  ): F[BinanceResponse[Seq[Order]]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withOptionQueryParam(Keys.recvWindow, recvWindow)
      .withQueryParam(Keys.timestamp, timestamp)

    for {
      request <- api.endpoint("openOrders", version, query).request[F](GET).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[Seq[Order]])
    } yield result
  }

  def getAll(
    symbol: String,
    orderId: Option[Long] = None,
    limit: Option[Int] = None,
    period: Period = Period.Empty,
    recvWindow: Option[Long] = None,
    timestamp: Instant = Instant.now(),
    version: Int = 3
  ): F[BinanceResponse[Seq[Order]]] = {
    val query = Query
      .fromPairs(Keys.symbol -> symbol)
      .withOptionQueryParam(Keys.orderId, orderId)
      .withOptionQueryParam(Keys.limit, limit)
      .withOptionQueryParam(Keys.recvWindow, recvWindow)
      .withQueryParam(Keys.timestamp, timestamp)
      .withSubQuery(period)

    for {
      request <- api.endpoint("allOrders", version, query).request[F](GET).putKey.sign
      result  <- request.fetch(_.consumeUnsafe[Seq[Order]])
    } yield result
  }
}

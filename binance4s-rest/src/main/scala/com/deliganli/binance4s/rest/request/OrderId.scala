package com.deliganli.binance4s.rest.request
import org.http4s.Query

class OrderId private (val underlier: Either[String, Long]) {
  def clientSide: Option[String] = underlier.left.toOption

  def id: Option[Long] = underlier.toOption
}

object OrderId {
  def apply(id: Long): OrderId = new OrderId(Right(id))

  def apply(clientSide: String): OrderId = new OrderId(Left(clientSide))

  implicit val orderIdQuery = HasQuery.create[OrderId](_.underlier match {
    case Right(id)          => Query(QueryParams.Keys.orderId          -> Some(id.toString))
    case Left(clientSideId) => Query(QueryParams.Keys.newClientOrderId -> Some(clientSideId))
  })
}

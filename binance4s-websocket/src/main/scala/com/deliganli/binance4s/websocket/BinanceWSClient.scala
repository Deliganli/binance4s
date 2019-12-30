package com.deliganli.binance4s.websocket

import cats.data.NonEmptySet
import cats.effect.{Concurrent, Resource, Timer}
import cats.implicits._
import com.deliganli.binance4s.websocket.request.BinanceRequest
import com.deliganli.binance4s.websocket.response.Event
import com.typesafe.scalalogging.StrictLogging
import fs2.{RaiseThrowable, Stream}
import io.circe.parser.parse
import io.circe.{Decoder, Error, Json, ParsingFailure}
import org.http4s.Uri
import org.http4s.client.jdkhttpclient.{WSClient, WSFrame, WSRequest}

import scala.concurrent.duration._

trait BinanceConnection[F[_]] {
  def incoming: Stream[F, Either[Error, Event]]
}

trait BinanceWSClient[F[_]] {
  def connect(requests: NonEmptySet[BinanceRequest]): Resource[F, BinanceConnection[F]]
}

object BinanceWSClient extends StrictLogging {

  def default[F[_]: Concurrent: Timer: RaiseThrowable](
    client: WSClient[F],
    base: Uri = Uri.unsafeFromString("wss://stream.binance.com:9443/ws"),
    pingInterval: FiniteDuration = 20.seconds
  ): BinanceWSClient[F] = (requests: NonEmptySet[BinanceRequest]) => {
    val streams = requests.map(_.streamName).toList
    val uri     = streams.foldLeft(base)((uri, path) => uri / path)
    val request = WSRequest(uri)

    client.connectHighLevel(request).map { connection =>
      new BinanceConnection[F] {
        override def incoming: Stream[F, Either[Error, Event]] = {
          val pingStream = Stream
            .awakeDelay[F](pingInterval)
            .evalTap(_ => connection.sendPing())

          val incomingStream = connection.receiveStream
            .collect { case f: WSFrame.Text => f.data }
            .map(toArray)
            .flatMap(unbatch[F, Event])

          incomingStream.concurrently(pingStream)
        }
      }
    }
  }

  def unbatch[F[_], T: Decoder](
    batch: Either[ParsingFailure, Vector[Json]]
  ): Stream[F, Either[Error, T]] = batch match {
    case Left(error)  => Stream.emit(Left(error))
    case Right(jsons) => Stream.emits(jsons).map(_.as[T])
  }

  def toArray(value: String): Either[ParsingFailure, Vector[Json]] = {
    parse(value).map { json =>
      if (json.isArray) json.asArray.get
      else Vector(json)
    }
  }
}

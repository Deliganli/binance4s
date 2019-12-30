package com.deliganli.binance4s.websocket

import cats.effect.{Concurrent, Sync, Timer}
import cats.implicits._
import com.deliganli.binance4s.websocket.BinanceWSError.ConnectionExpiry
import com.typesafe.scalalogging.StrictLogging
import fs2.{INothing, RaiseThrowable, Stream}
import io.circe.{Decoder, Error}

import scala.concurrent.duration._

object utility extends StrictLogging {

  def errorsDiscarded[F[_]: Sync, T: Decoder](result: Either[Error, T]): F[Option[T]] = result match {
    case Left(error)  => Sync[F].delay(logger.error("Couldn't pre process message", error)).map(_ => Option.empty[T])
    case Right(value) => Sync[F].pure(value.some)
  }

  /**
    * Throws ConnectionExpiry exception after 24 hour mark.
    * @return A stream that is failing after 24 hour
    */
  def dailyThreshold[F[_]: Concurrent: Timer: RaiseThrowable]: Stream[F, INothing] = {
    Stream.never[F].interruptAfter(24.hours) ++ Stream.raiseError(ConnectionExpiry())
  }
}

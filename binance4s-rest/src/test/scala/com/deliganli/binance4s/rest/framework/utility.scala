package com.deliganli.binance4s.rest.framework

import java.io.PrintWriter
import java.nio.file.Paths

import cats.effect.{IO, Resource}
import com.deliganli.binance4s.rest.response.base.BinanceResponse
import io.circe.Encoder

object utility {

  def persistToFile[T: Encoder](response: BinanceResponse[T]) = IO {
    import io.circe.syntax._

    val responsePath = Paths.get("target", "responses").toAbsolutePath
    val className    = response.body.getClass.getSimpleName
    val target       = responsePath.resolve(s"$className.json")

    for {
      _ <- IO(target.toFile.getParentFile.mkdirs())
      _ <- Resource
        .make(IO(new PrintWriter(target.toFile)))(writer => IO(writer.close()))
        .use(writer => IO(writer.write(response.body.asJson.spaces2)))
    } yield ()
  }

  def stdOut[T](response: BinanceResponse[T]) = IO(println(response))

}

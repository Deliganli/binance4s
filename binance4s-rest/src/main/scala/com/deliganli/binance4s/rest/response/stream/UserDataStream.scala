package com.deliganli.binance4s.rest.response.stream

import io.circe.Decoder

case class UserDataStream(
  listenKey: String
)

object UserDataStream {
  implicit val userDataStreamDecoder: Decoder[UserDataStream] = Decoder.forProduct1(
    "listenKey"
  )(UserDataStream.apply)
}

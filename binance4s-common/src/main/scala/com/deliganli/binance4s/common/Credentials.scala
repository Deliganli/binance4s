package com.deliganli.binance4s.common

import io.circe.Decoder

case class Credentials(key: String, secret: String)

object Credentials {
  implicit val credentialsDecoder: Decoder[Credentials] = Decoder.forProduct2(
    "key",
    "secret"
  )(Credentials.apply)
}

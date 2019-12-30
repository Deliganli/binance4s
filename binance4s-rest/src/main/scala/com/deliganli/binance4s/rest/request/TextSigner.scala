package com.deliganli.binance4s.rest.request

import cats.effect.Sync
import cats.implicits._
import tsec.common._
import tsec.mac.jca.{HMACSHA256, MacSigningKey}

object TextSigner {

  def sign[F[_]: Sync](signingKey: MacSigningKey[HMACSHA256])(plainText: String): F[String] = {
    HMACSHA256.sign[F](plainText.asciiBytes, signingKey).map(_.toHexString)
  }
}

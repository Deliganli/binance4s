package com.deliganli.binance4s.rest.request

import cats.effect.Sync
import cats.implicits._
import org.http4s.Request
import tsec.common._
import tsec.mac.jca.{HMACSHA256, MacSigningKey}

trait RequestSigner[F[_]] {
  def sign(request: Request[F]): F[Request[F]]
}

object RequestSigner {
  def apply[F[_], T](implicit ev: RequestSigner[F]): RequestSigner[F] = ev

  def create[F[_]: Sync](signingKey: MacSigningKey[HMACSHA256]): RequestSigner[F] = (request: Request[F]) => {
    sign(request.queryString, signingKey)
      .map(signature => request.withUri(request.uri.withQueryParam(QueryParams.Keys.signature, signature)))
  }

  def sign[F[_]: Sync](plainText: String, signingKey: MacSigningKey[HMACSHA256]) =
    HMACSHA256.sign[F](plainText.asciiBytes, signingKey).map(_.toHexString)
}

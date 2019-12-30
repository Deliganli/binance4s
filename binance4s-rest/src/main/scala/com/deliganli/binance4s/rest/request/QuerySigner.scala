package com.deliganli.binance4s.rest.request

import cats.effect.Sync
import cats.implicits._
import org.http4s.Request
import tsec.mac.jca.{HMACSHA256, MacSigningKey}

trait QuerySigner[F[_]] {
  def addSignature(request: Request[F]): F[Request[F]]
}

object QuerySigner {
  type ALG = MacSigningKey[HMACSHA256]

  def apply[F[_], T](implicit ev: QuerySigner[F]) = ev

  def create[F[_]: Sync](signingKey: ALG): QuerySigner[F] = (request: Request[F]) => {
    TextSigner
      .sign(signingKey)(request.queryString)
      .map(signature => request.withUri(request.uri.withQueryParam(QueryParams.Keys.signature, signature)))
  }
}

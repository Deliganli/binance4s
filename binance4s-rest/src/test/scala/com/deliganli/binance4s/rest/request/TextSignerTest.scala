package com.deliganli.binance4s.rest.request

import cats.Id
import cats.effect.IO
import com.deliganli.binance4s.common.framework.UnitTest
import com.deliganli.binance4s.rest.framework.IntegrationTest
import tsec.common._
import tsec.mac.jca.HMACSHA256

class TextSignerTest extends UnitTest {

  val signingKey = HMACSHA256.buildKey[Id](IntegrationTest.credentials.secret.asciiBytes)

  "Signature calculated" should "match the official example" in {
    val queryString =
      "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559"
    val signature = TextSigner.sign[IO](signingKey)(queryString)

    signature.unsafeRunSync() == "c8db56825ae71d6d79447849e617115f4a920fa2acdcab2b053c4b2838bd6b71"
  }
}

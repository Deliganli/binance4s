package com.deliganli.binance4s.rest.request

import java.time.{Clock, Instant, ZoneOffset, ZonedDateTime}

import com.deliganli.binance4s.common.consts.KlineInterval
import com.deliganli.binance4s.common.framework.UnitTest

class QueryParamsTest extends UnitTest {
  "DateTime encoder" should "encode epoch millis" in {
    val instant  = Instant.now()
    val millis   = instant.toEpochMilli
    val dateTime = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)

    val expected = millis.toString
    val result   = QueryParams.Formatters.dateTimeEncoder.encode(dateTime).value

    result shouldBe expected
  }

  "Kline interval encoder" should "encode short interval names" in {
    val result = QueryParams.Formatters.klineIntervalEncoder.encode(KlineInterval.OneMinute).value

    result shouldBe "1m"
  }
}

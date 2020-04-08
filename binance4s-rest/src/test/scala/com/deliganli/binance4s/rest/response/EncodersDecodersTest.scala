package com.deliganli.binance4s.rest.response

import com.deliganli.binance4s.common.framework.UnitTest
import com.deliganli.binance4s.rest.response.market.KlineResponse
import java.time.Instant
import com.deliganli.binance4s.rest.response.general.ExchangeInfo
import io.circe.Encoder
import io.circe.Decoder
import com.deliganli.binance4s.rest.response.filter.ExchangeFilter.ExchangeMaxNumOrders
import com.deliganli.binance4s.rest.response.filter.ExchangeFilter.ExchangeMaxNumAlgoOrders
import com.deliganli.binance4s.rest.response.market.CurrencySymbol
import com.deliganli.binance4s.common.consts.OrderType
import com.deliganli.binance4s.common.consts.SymbolStatus
import com.deliganli.binance4s.rest.response.filter.SymbolFilter.PriceFilter
import com.deliganli.binance4s.rest.response.general.RateLimit

class EncodersDecodersTest extends UnitTest {

  import io.circe.syntax._

  def test[T: Encoder: Decoder](initial: T) =
    initial.asJson.as[T].right.value shouldBe initial

  "Instant" should "encoder / decoder" in {
    val dec = com.deliganli.binance4s.common.formatters.Decoders.decodeInstant
    val enc = com.deliganli.binance4s.common.formatters.Encoders.encodeInstant
    test(Instant.ofEpochMilli(100))
  }

  "ExchangeInfo" should "encode / decode" in {
    test(
      ExchangeInfo(
        timezone = "utc",
        serverTime = Instant.ofEpochMilli(100),
        rateLimits = Seq(RateLimit("a", "b", 1L, 2L)),
        exchangeFilters = Seq(ExchangeMaxNumOrders("EXCHANGE_MAX_NUM_ORDERS", 1)),
        symbols = Seq(
          CurrencySymbol(
            symbol = "BTCUSD",
            status = SymbolStatus.Trading,
            baseAsset = "BTC",
            baseAssetPrecision = 1,
            quoteAsset = "USD",
            quotePrecision = 2,
            baseCommissionPrecision = 3,
            quoteCommissionPrecision = 4,
            orderTypes = Seq(OrderType.Market),
            icebergAllowed = true,
            ocoAllowed = true,
            isSpotTradingAllowed = true,
            isMarginTradingAllowed = true,
            filters = Seq(PriceFilter("PRICE_FILTER", BigDecimal(1), BigDecimal(2), 2D))
          )
        )
      )
    )
  }

  "KlineResponse" should "encode / decode" in {
    test(
      KlineResponse(
        Instant.ofEpochMilli(100),
        BigDecimal(1),
        BigDecimal(2),
        BigDecimal(3),
        BigDecimal(4),
        BigDecimal(5),
        Instant.ofEpochMilli(200),
        BigDecimal(6),
        7L,
        BigDecimal(8),
        BigDecimal(9),
        "0"
      )
    )
  }

}

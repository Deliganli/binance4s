# Binance4s
| Snapshot | Release |
| --- | --- |
| [![Sonatype Nexus (Releases)][Badge-SonatypeReleases]][Link-SonatypeReleases] | [![Sonatype Nexus (Snapshots)][Badge-SonatypeSnapshots]][Link-SonatypeSnapshots] |

Binance4s is a lightweight functional scala library acts as a rest and websocket client for [Binance][Link-binance]

Both websocket and rest clients utilizes  [http4s][Link-http4s], [cats][Link-cats], [enumeratum][Link-enumeratum], [circe][Link-circe]

## Install
Add the following to your `build.sbt` file. This will add both `rest` and `websocket` modules. 

Builds are available for scala `2.13.x` and `2.12.x`
```sbt
libraryDependencies ++= Seq(
  "com.deliganli" %% "binance4s-websocket" % "0.1.0",
  "com.deliganli" %% "binance4s-rest" % "0.1.0"
)
```
## Examples

### Rest

```scala
import cats.effect.{Clock, ConcurrentEffect}
import com.deliganli.binance4s.common.Credentials
import com.deliganli.binance4s.common.http.JDKHttpClient
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.response.base.{BinanceError, BinanceResponse}
import com.deliganli.binance4s.rest.response.general.ExchangeInfo
import com.typesafe.scalalogging.StrictLogging
import org.http4s.client.Client
import org.http4s.client.jdkhttpclient.JdkHttpClient

class RestExample[F[_]: ConcurrentEffect: Clock] extends StrictLogging {
  val httpClient: Client[F] = JdkHttpClient[F](JDKHttpClient.default)

  // Public client doesn't require neither api key nor secret
  val publicClient: BinanceRestClient.PublicClient[F] = BinanceRestClient.public[F](
    httpClient
  )

  // Metered client only requires api key
  val meteredClient: BinanceRestClient.MeteredClient[F] = BinanceRestClient.metered[F](
    httpClient,
    "<key>"
  )

  // Secure client requires both api key and secret
  val secureClient: BinanceRestClient.SecureClient[F] = BinanceRestClient.secure[F](
    httpClient,
    Credentials("<key>", "<secret>")
  )

  val exchangeInfo: F[BinanceResponse[Either[BinanceError, ExchangeInfo]]] = publicClient.general.exchangeInfo
}
```

### WebSocket

Uses [fs2][Link-fs2]

```scala
import cats.data.NonEmptySet
import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import com.deliganli.binance4s.common.consts.KlineInterval
import com.deliganli.binance4s.common.http.JDKHttpClient
import com.deliganli.binance4s.websocket.BinanceWSClient
import com.deliganli.binance4s.websocket.request.BinanceRequest
import com.deliganli.binance4s.websocket.response.Event
import com.typesafe.scalalogging.StrictLogging
import fs2.Stream
import io.circe.Error
import org.http4s.client.jdkhttpclient.JdkWSClient

class EventStreamExample[F[_]: ConcurrentEffect: ContextShift: Timer] extends StrictLogging {
  val httpClient                   = JdkWSClient(JDKHttpClient.default)
  val wsClient: BinanceWSClient[F] = BinanceWSClient.create[F](httpClient)

  val requests: NonEmptySet[BinanceRequest] = NonEmptySet.of(
    BinanceRequest.Kline("BTCUSDT", KlineInterval.OneMinute),
    BinanceRequest.Trade("BTCUSDT"),
    BinanceRequest.Ticker("BTCUSDT")
  )

  val eventStream: Stream[F, Either[Error, Event]] = Stream
    .resource(wsClient.connect(requests))
    .flatMap(_.incoming)
}
```
[Badge-SonatypeReleases]: https://img.shields.io/nexus/s/com.deliganli/binance4s-websocket_2.13?server=https%3A%2F%2Foss.sonatype.org "Sonatype Releases"
[Link-SonatypeReleases]: https://oss.sonatype.org/content/repositories/snapshots/com/deliganli/binance4s-websocket_2.13/ "Sonatype Releases"

[Badge-SonatypeSnapshots]: https://img.shields.io/nexus/r/https/oss.sonatype.org/com.deliganli/binance4s-websocket_2.13.svg "Sonatype Releases"
[Link-SonatypeSnapshots]: https://oss.sonatype.org/content/repositories/snapshots/com/deliganli/binance4s-websocket_2.13/ "Sonatype Releases"

[Link-binance]: https://github.com/binance-exchange/binance-official-api-docs
[Link-http4s]: https://github.com/http4s/http4s
[Link-cats]: https://github.com/typelevel/cats
[Link-enumeratum]: https://github.com/lloydmeta/enumeratum
[Link-circe]: https://github.com/circe/circe
[Link-fs2]: https://github.com/functional-streams-for-scala/fs2

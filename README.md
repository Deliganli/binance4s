# Binance4s
[![Sonatype Nexus (Snapshots)][Badge-SonatypeReleases]][Link-SonatypeReleases]

Binance4s is a lightweight functional scala library acts as a rest and websocket client for [Binance][Link-binance]

Both websocket and rest clients utilizes  [http4s][Link-http4s], [cats][Link-cats], [enumeratum][Link-enumeratum], [circe][Link-circe]

## Install
Add the following to your `build.sbt` file. This will add both `rest` and `websocket` modules. 

Builds are available for scala `2.13.x` and `2.12.x`
```sbt
resolvers += Opts.resolver.sonatypeSnapshots
libraryDependencies ++= Seq(
  "com.deliganli" %% "binance4s-websocket" % "0.1.0-SNAPSHOT",
  "com.deliganli" %% "binance4s-rest" % "0.1.0-SNAPSHOT"
)
```
## Examples

### Rest

```scala
import cats.effect.ConcurrentEffect
import cats.implicits._
import com.deliganli.binance4s.common.Credentials
import com.deliganli.binance4s.rest.BinanceRestClient
import com.deliganli.binance4s.rest.http.JDKHttpClient
import com.deliganli.binance4s.rest.response.general.ExchangeInfo
import com.typesafe.scalalogging.StrictLogging
import org.http4s.Uri
import org.http4s.client.Client
import org.http4s.client.jdkhttpclient.JdkHttpClient

class RestExample[F[_]: ConcurrentEffect] extends StrictLogging {

  val httpClient: Client[F] = JdkHttpClient[F](JDKHttpClient.default)

  val secureClient: BinanceRestClient.SecureClient[F] = BinanceRestClient.secure[F](
    httpClient,
    Credentials("<key>", "<secret>")
  )

  // Metered client only requires api key
  val meteredClient: BinanceRestClient.MeteredClient[F] = BinanceRestClient.metered[F](
    httpClient,
    "<key>"
  )

  // Public client doesn't require neither api key nor secret
  val publicClient: BinanceRestClient.PublicClient[F] = BinanceRestClient.public[F](
    httpClient
  )

  val exchangeInfo: F[ExchangeInfo] = publicClient.general.exchangeInfo().map(_.body)
}
```

### WebSocket

Uses [fs2][Link-fs2]

```scala
import cats.data.NonEmptySet
import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import com.deliganli.binance4s.common.consts.KlineInterval
import com.deliganli.binance4s.rest.http.JDKHttpClient
import com.deliganli.binance4s.websocket.BinanceWSClient
import com.deliganli.binance4s.websocket.request.BinanceRequest
import com.deliganli.binance4s.websocket.response.Message
import com.typesafe.scalalogging.StrictLogging
import fs2.Stream
import io.circe.Error
import org.http4s.Uri
import org.http4s.client.jdkhttpclient.JdkWSClient

class EventStreamExample[F[_]: ConcurrentEffect: ContextShift: Timer] extends StrictLogging {
  val httpClient                   = JdkWSClient(JDKHttpClient.default)
  val wsClient: BinanceWSClient[F] = BinanceWSClient.default[F](httpClient)

  val requests: NonEmptySet[BinanceRequest] = NonEmptySet.of(
    BinanceRequest.Kline("BTCUSDT", KlineInterval.OneMinute),
    BinanceRequest.Trade("BTCUSDT"),
    BinanceRequest.Ticker("BTCUSDT")
  )

  val eventStream: Stream[F, Either[Error, Message]] = Stream
    .resource(wsClient.connect(requests))
    .flatMap(_.incoming)
}
```
[Badge-SonatypeReleases]: https://img.shields.io/nexus/r/https/oss.sonatype.org/com.deliganli/binance4s-websocket_2.13.svg "Sonatype Releases"
[Link-SonatypeReleases]: https://oss.sonatype.org/content/repositories/releases/com/deliganli/binance4s-websocket_2.13/ "Sonatype Releases"

[Link-binance]: https://github.com/binance-exchange/binance-official-api-docs
[Link-http4s]: https://github.com/http4s/http4s
[Link-cats]: https://github.com/typelevel/cats
[Link-enumeratum]: https://github.com/lloydmeta/enumeratum
[Link-circe]: https://github.com/circe/circe
[Link-fs2]: https://github.com/functional-streams-for-scala/fs2

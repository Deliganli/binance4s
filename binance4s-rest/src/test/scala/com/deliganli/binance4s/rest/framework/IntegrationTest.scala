package com.deliganli.binance4s.rest.framework

import java.nio.file.Paths

import com.deliganli.binance4s.common.Credentials
import com.deliganli.binance4s.common.framework.UnitTest
import com.typesafe.config.ConfigFactory
import io.circe.config.parser

abstract class IntegrationTest extends UnitTest

object IntegrationTest {

  // Example credentials from binance documentation
  // https://github.com/binance-exchange/binance-official-api-docs/blob/master/rest-api.md#signed-endpoint-examples-for-post-apiv3order
  val exampleCredentials = Credentials(
    "vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A",
    "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j"
  )

  val credentials = sys.env
    .get("HOME")
    .map(home => Paths.get(home, "creds", "binance.credentials").toFile)
    .flatMap(file => parser.decode[Credentials](ConfigFactory.parseFile(file)).toOption)
    .getOrElse(exampleCredentials)
}

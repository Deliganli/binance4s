package com.deliganli.binance4s.websocket

sealed trait BinanceWSError {}

object BinanceWSError {
  case class ConnectionExpiry() extends Exception with BinanceWSError
}

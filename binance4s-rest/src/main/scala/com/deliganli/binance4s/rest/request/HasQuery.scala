package com.deliganli.binance4s.rest.request

import org.http4s.Query

trait HasQuery[T] {
  def query(v: T): Query
}

object HasQuery {
  def apply[T](implicit ev: HasQuery[T]): HasQuery[T] = ev

  def create[T](f: T => Query): HasQuery[T] = (profile: T) => f(profile)
}

package net.mobocracy.starter

import scala.util.Random

import java.net.InetSocketAddress
import java.util.concurrent.Callable

import com.twitter.conversions._
import com.twitter.finagle.{ClientConnection, Filter, Service, SimpleFilter}
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.stats.OstrichStatsReceiver
import com.twitter.logging.Logger
import com.twitter.ostrich.stats.Stats
import com.twitter.util.{Future, FutureTask}

trait StarterService {
  val port: Int
  val name: String
  var server: Option[Server] = None

  val log = Logger.get(getClass)
  val MAX_SLEEP = 5000L // Random sleep in getStringHash
  val MIN_SLEEP = 1000L

  // Don't initialize until after mixed in by another class
  lazy val serverSpec = ServerBuilder()
    .codec(StringCodec())
    .bindTo(new InetSocketAddress(port))
    .name(name)
    .reportTo(new OstrichStatsReceiver)

  def quitCheck(client: ClientConnection) = new SimpleFilter[String, String] {
    def apply(request: String, service: Service[String, String]): Future[String] = {
      request match {
        case "quit" =>
          if (client != null) client.close()
          Future.value("noop") // no clients see this
        case _ =>
          service(request)
      }
    }
  }

  val exceptionCheck = new SimpleFilter[String,String] {
    def apply(request: String, service: Service[String, String]): Future[String] = {
      request match {
        case "please throw an exception" =>
          Future.exception(new Exception("Asked to throw, don't blame me"))
        case _ =>
          service(request)
      }
    }
  }

  val hashRequest = new Filter[String, String, RequestHash, String] {
    def apply(request: String, service: Service[RequestHash, String]): Future[String] = {
      val sleepTime = (math.abs(Random.nextLong()) % (MAX_SLEEP - MIN_SLEEP)) + MIN_SLEEP

      log.debug("Sleeping %d milliseconds for %s", sleepTime, request)

      val hash = Stats.time("request_hash") {
        Thread.sleep(sleepTime)
        RequestHash(request)
      }
      service(hash)
    }
  }

  val serviceImpl = new Service[RequestHash, String] {
    def apply(request: RequestHash): Future[String] = {
      log.debug("Got request %s", request)
      Stats.incr("service_request_count")
      Future.value(request.original + "\t" + request.hashed + "\n")
    }
  }

  val service = new Function1[ClientConnection, Service[String, String]] {
    val underlying: Service[String, String] = exceptionCheck andThen hashRequest andThen serviceImpl
    def apply(client: ClientConnection): Service[String, String] = {
      quitCheck(client) andThen underlying
    }
  }

}

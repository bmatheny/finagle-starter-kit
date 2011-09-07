package net.mobocracy.starter

import java.net.InetSocketAddress
import java.security.MessageDigest

import com.twitter.finagle.Service
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.stats.OstrichStatsReceiver
import com.twitter.logging.Logger
import com.twitter.ostrich.stats.Stats
import com.twitter.util.Future

trait StarterService {
  val port: Int
  val name: String
  var server: Option[Server] = None

  val log = Logger.get(getClass)

  // Don't initialize until after mixed in by another class
  lazy val serverSpec = ServerBuilder()
    .codec(StringCodec())
    .bindTo(new InetSocketAddress(port))
    .name(name)
    .reportTo(new OstrichStatsReceiver)

  val service = new Service[String, String] {
    def apply(request: String) = {
      log.debug("Got request")
      Stats.incr("service_request_count")

      val hashedRequest = Stats.time("request_hash") {
        getStringHash(request)
      }

      request match {
        case "please throw an exception" =>
          Future.exception(new Exception("Asked to throw, don't blame me"))
        case _ =>
          Future.value(request + "\t" + hashedRequest + "\n")
      }
    }
  }

  protected[this] def getStringHash(s: String): String = {
    val hasher = MessageDigest.getInstance("SHA-512")
    hasher.update(s.getBytes("UTF-8"))
    hasher.digest.map { "%02x" format _ }.mkString
  }
}

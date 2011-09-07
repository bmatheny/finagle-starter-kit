package net.mobocracy.starter

import java.net.InetSocketAddress

import com.twitter.finagle.Service
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.util.Future

trait StarterService {
  val port: Int
  val name: String
  var server: Option[Server] = None

  val service = new Service[String, String] {
    def apply(request: String) = Future.value(request)
  }

  val serverSpec = ServerBuilder()
    .codec(StringCodec())
    .bindTo(new InetSocketAddress(port))
    .name(name)
}

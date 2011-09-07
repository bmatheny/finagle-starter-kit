package net.mobocracy.starter

import config.StarterServiceConfig

import com.twitter.finagle.builder.Server
import com.twitter.conversions.time._

import org.specs.Specification
import org.specs.mock._

class StarterSpec extends Specification with JMocker {

  var service: StarterService = _

  "StarterService" should {
    doBefore {
      service = new {
        val port = 10000
        val name = "Test Server"
      } with StarterService;
    }
    "accept a string" >> {
      val future = service.service("hello")
      val reply = future()
      reply must beMatching("hello.*")
    }
    "throw an exception on magic string" >> {
      val future = service.service("please throw an exception")
      val reply = future() must throwA[Exception].like { case e:Exception => e.getMessage.contains("don't blame") }
    }
  }

  "StarterServiceServer" should {
    "throw an exception if" >> {
      "no port is specified" >> {
        val config = new StarterServiceConfig {port = -1; name = "Hello"}
        new StarterServiceServer(config) must throwA[IllegalArgumentException].like { case e: Exception => e.getMessage.contains("port") }
      }
      "no name is specified" >> {
        val config = new StarterServiceConfig {port = 1025; name = ""}
        new StarterServiceServer(config) must throwA[IllegalArgumentException].like { case e: Exception => e.getMessage.contains("service name") }
      }
    }
    "shutdown properly" >> {
      val config = new StarterServiceConfig()
      val mockServer: Server = mock[Server]
      expect {
        one(mockServer).close(0.seconds)
      }
      val service = new StarterServiceServer(config) {
        server = Some(mockServer)
      }
      service.shutdown()
    }
  } // StarterServiceServer


}

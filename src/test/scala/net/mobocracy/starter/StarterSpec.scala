package net.mobocracy.starter

import config.StarterServiceConfig

import com.twitter.finagle.{ClientConnection, Service}
import com.twitter.finagle.builder.Server
import com.twitter.conversions.time._
import com.twitter.util.Await

import org.specs.mock._

class StarterSpec extends AbstractSpec with JMocker {

  var testService: StarterService = _
  val mockClientConnection = mock[ClientConnection]

  def exContains(ss: String): PartialFunction[Exception, Boolean] = {
    case ex: Exception => ex.getMessage.toLowerCase.contains(ss.toLowerCase)
  }

  def getService: Service[String, String] = {
    Await.result(testService.service(mockClientConnection))
  }

  "StarterService" should {
    doBefore {
      testService = new {
        val port = 10000
        val name = "Test Server"
      } with StarterService;
    }

    "accept a string" >> {
      val future = getService("hello")
      val reply = Await.result(future)
      reply must beMatching("hello.*")
    }

    "handle a quit" >> {
      expect {
        one(mockClientConnection).close()
      }
      val future = getService("quit")
      val reply = Await.result(future)
      reply mustEqual("noop")
    }

    "throw an exception on magic string" >> {
      val future = getService("please throw an exception")
      val reply = Await.result(future) must throwA[Exception].like(exContains("don't blame"))
    }
  }

  "StarterServiceServer" should {
    "throw an exception if" >> {
      "no port is specified" >> {
        val config = new StarterServiceConfig {port = -1; name = "Hello"}
        new StarterServiceServer(config) must throwA[IllegalArgumentException].like(exContains("port"))
      }
      "no name is specified" >> {
        val config = new StarterServiceConfig {port = 1025; name = ""}
        new StarterServiceServer(config) must throwA[IllegalArgumentException].like(exContains("service name"))
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

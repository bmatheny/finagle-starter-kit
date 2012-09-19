package net.mobocracy.starter
package client

import com.twitter.conversions.time._
import com.twitter.finagle.RequestTimeoutException
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.util.{Future, JavaTimer, Time, TimeoutException}

object Main {
  def main(args: Array[String]) {
    val options = parseOptions(Map(), args.toList)
    require(options.get("value").isDefined, "--value must be specified")
    val concurrent = options.getOrElse("concurrent", 4).asInstanceOf[Int]
    val port = options.getOrElse("port", 4242).asInstanceOf[Int]
    val progress = options.getOrElse("progress", true).asInstanceOf[Boolean]
    val requestCount = options.getOrElse("requests", 25).asInstanceOf[Int]
    val value = options("value").asInstanceOf[String]
    val timeout = options.getOrElse("timeout", 4).asInstanceOf[Int].seconds
    val totalTimeout = options.getOrElse("totalTimeout", 30).asInstanceOf[Int].seconds

    val client = ClientBuilder()
                  .codec(StringCodec())
                  .hosts("localhost:%d".format(port))
                  .name("StarterClient")
                  .hostConnectionLimit(concurrent)
                  .requestTimeout(timeout)
                  .build()

    val requests = (0 until requestCount) map { i =>
      val request = value + " " + i
      client(request + "\n") onSuccess { result =>
        if (progress) printf("Received success result for %s: %s\n", request, result.stripLineEnd)
      } onFailure { error =>
        if (progress) printf("Received error result for %s: %s\n", request, error.getClass.toString)
      } handle { case e: RequestTimeoutException => "Request Timeout for %s".format(request) }
    }

    Future.collect(requests).get(totalTimeout) onSuccess { list =>
      println("\nResults\n")
      println(list.map { _.stripLineEnd } mkString("\n"))
    } onFailure { err =>
      println("\nResults\n")
      println("Error processing list: " + err)
    } ensure {
      client.release()
    }
  }

  // Inspired by http://goo.gl/ksaA5
  protected def parseOptions(parsed: Map[String, Any], list: List[String]): Map[String, Any] = list match {
    case Nil => parsed
    case "--concurrent" :: value :: tail =>
      parseOptions(parsed ++ Map("concurrent" -> value.toInt), tail)
    case "--noprogress" :: tail =>
      parseOptions(parsed ++ Map("progress" -> false), tail)
    case "--port" :: value :: tail =>
      parseOptions(parsed ++ Map("port" -> value.toInt), tail)
    case "--requests" :: value :: tail =>
      parseOptions(parsed ++ Map("requests" -> value.toInt), tail)
    case "--timeout" :: value :: tail =>
      parseOptions(parsed ++ Map("timeout" -> value.toInt), tail)
    case "--totalTimeout" :: value :: tail =>
      parseOptions(parsed ++ Map("totalTimeout" -> value.toInt), tail)
    case "--value" :: value :: tail =>
      parseOptions(parsed ++ Map("value" -> value), tail)
    case unknownOption :: tail =>
      println("Unknown option " + unknownOption)
      println("--concurrent INT   - Max number of server connections, 4")
      println("--noprogress       - Whether to display progress or not, true")
      println("--port INT         - Port to connect to, 4242")
      println("--requests INT     - Number of requests to issue, 25")
      println("--timeout INT      - Seconds to wait before request timeout, 4")
      println("--totalTimeout INT - Seconds to wait before all requests are timed out, 30")
      println("--value STRING     - Echo value to use, must be specified")
      exit(1)
  }
}

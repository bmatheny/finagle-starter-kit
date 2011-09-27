package net.mobocracy.starter
package client

import java.util.concurrent.atomic.AtomicInteger

import com.twitter.conversions.time._
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.util.{Future, JavaTimer, Time}

object Main {
  def main(args: Array[String]) {
    val options = parseOptions(Map(), args.toList)
    require(options.get("value").isDefined, "--value must be specified")
    val concurrent = options.getOrElse("concurrent", 4).asInstanceOf[Int]
    val port = options.getOrElse("port", 4242).asInstanceOf[Int]
    val requestCount = options.getOrElse("requests", 25).asInstanceOf[Int]
    val value = options("value").asInstanceOf[String]
    val timeout = options.getOrElse("timeout", 4).asInstanceOf[Int].seconds
    val count = new AtomicInteger(0)

    val client = ClientBuilder()
                  .codec(StringCodec())
                  .hosts("localhost:%d".format(port))
                  .name("StarterClient")
                  .hostConnectionLimit(concurrent)
                  .requestTimeout(timeout)
                  .build()


    val requests = (0 until requestCount) map { i =>
      val request = value + " " + i + "\n"
      count.incrementAndGet
      client(request) onSuccess { result =>
        printf("Received success result for %s: %s\n", request.stripLineEnd, result.stripLineEnd)
        count.decrementAndGet
      } onFailure { error =>
        printf("Received error result for %s: %s\n", request.stripLineEnd, error.getClass.toString)
        count.decrementAndGet
      }
    }

    val success = Future.collect(requests).isReturn
    while (count.get > 0) {
      Thread.sleep(1000)
    }
    println("\nSummary\n")
    success match {
      case true => println("All requests completed successfully")
      case false => println("An error occurred")
    }
    client.release()
  }

  // Inspired by http://goo.gl/ksaA5
  protected def parseOptions(parsed: Map[String, Any], list: List[String]): Map[String, Any] = list match {
    case Nil => parsed
    case "--concurrent" :: value :: tail =>
      parseOptions(parsed ++ Map("concurrent" -> value.toInt), tail)
    case "--port" :: value :: tail =>
      parseOptions(parsed ++ Map("port" -> value.toInt), tail)
    case "--requests" :: value :: tail =>
      parseOptions(parsed ++ Map("requests" -> value.toInt), tail)
    case "--timeout" :: value :: tail =>
      parseOptions(parsed ++ Map("timeout" -> value.toInt), tail)
    case "--value" :: value :: tail =>
      parseOptions(parsed ++ Map("value" -> value), tail)
    case unknownOption :: tail =>
      error("Unknown option " + unknownOption)
      exit(1)
  }
}

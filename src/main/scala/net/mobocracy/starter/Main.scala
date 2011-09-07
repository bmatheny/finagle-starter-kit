package net.mobocracy.starter

import com.twitter.logging.Logger
import com.twitter.ostrich.admin.{RuntimeEnvironment, ServiceTracker}

object Main {
  val log = Logger.get(getClass)

  def main(args: Array[String]) {
    val runtime = RuntimeEnvironment(this, args)
    val server = runtime.loadRuntimeConfig[StarterServiceServer]
    try {
      log.info("Starting service")
      server.start()
    } catch {
      case e: Exception =>
        log.error(e, "Failed starting service, exiting")
        ServiceTracker.shutdown()
        System.exit(1)
    }
  }
}

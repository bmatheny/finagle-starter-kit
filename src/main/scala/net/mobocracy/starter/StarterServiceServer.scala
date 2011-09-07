package net.mobocracy.starter

import config.StarterServiceConfig

import com.twitter.conversions.time._
import com.twitter.logging.Logger
import com.twitter.ostrich.admin.{Service => OstrichService}

class StarterServiceServer(config: StarterServiceConfig) extends OstrichService with StarterService {
  require(config.port > 0, "Need a port to listen on")
  require(config.name != null && config.name.length > 0, "Need a service name")

  val log = Logger.get(getClass)
  val port = config.port
  val name = config.name

  override def start() {
    log.info("Starting StarterServiceServer")
    server = Some(serverSpec.build(service))
  }

  override def shutdown() {
    log.info("Shutting requested")
    server match {
      case None =>
        log.info("Server not started, refusing to shutdown")
      case Some(server) =>
        try {
          server.close(0.seconds)
          log.info("Shutdown complete")
        } catch {
          case e: Exception =>
            log.error(e, "Error shutting down server")
        }
    } // server match
  }

  override def reload() {
    log.info("Reload requested, doing nothing but I could re-read the config or something")
  }
}

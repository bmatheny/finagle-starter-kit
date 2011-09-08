package net.mobocracy.starter

import com.twitter.logging.Logger
import com.twitter.logging.config._

import org.specs.Specification

abstract class AbstractSpec extends Specification {

  def setLogLevel(_level: Level) {
    Logger.configure(new LoggerConfig {
      level = _level
      handlers = new ConsoleHandlerConfig()
    })
  }

  setLogLevel(Level.WARNING)
}

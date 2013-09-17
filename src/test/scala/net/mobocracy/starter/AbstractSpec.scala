package net.mobocracy.starter

import com.twitter.logging.{ConsoleHandler, Logger, LoggerFactory}
import com.twitter.logging.config._

import org.specs.Specification

abstract class AbstractSpec extends Specification {

  def setLogLevel(_level: Level) {
    Logger.configure(List(LoggerFactory(
      level = Some(_level),
      handlers = List(ConsoleHandler())
    )))
  }

  setLogLevel(Level.WARNING)
}

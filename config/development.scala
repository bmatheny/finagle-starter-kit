import net.mobocracy.starter.config._
import com.twitter.conversions.time._
import com.twitter.logging.config._
import com.twitter.ostrich.admin.config._

new StarterServiceConfig {
  port = 4242
  name = "StarterService"

  admin.httpPort = 9900

  admin.statsNodes = new StatsConfig {
    reporters = new JsonStatsLoggerConfig {
      loggerName = "stats"
      serviceName = name
    } :: new TimeSeriesCollectorConfig
  }

  loggers =
    new LoggerConfig {
      level = Level.TRACE
      handlers = new ConsoleHandlerConfig {
        formatter = new FormatterConfig {
          useFullPackageNames = true
          prefix = "%s [<yyyyMMdd-HH:mm:ss.SSS>] %s: "
        }
      }
    } :: new LoggerConfig {
      node = "stats"
      level = Level.FATAL
      useParents = false
      handlers = new ConsoleHandlerConfig
    }

}

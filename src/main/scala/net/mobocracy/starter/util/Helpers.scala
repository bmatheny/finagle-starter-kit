package net.mobocracy.starter
package util

object Helpers {
  def isPortAvailable(port: Int): Boolean = {
    require(port > 0, "Port must be greater than 0")
    import java.net.{ConnectException, Socket}

    try {
      val socket = new Socket("localhost", port)
      socket.close()
      false
    } catch {
      case e:ConnectException =>
        true
      case e:Exception =>
        false
    }

  }
}

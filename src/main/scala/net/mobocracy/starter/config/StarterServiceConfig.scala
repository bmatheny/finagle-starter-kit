package net.mobocracy.starter
package config

import util.Helpers._

import com.twitter.ostrich.admin.RuntimeEnvironment
import com.twitter.ostrich.admin.config.ServerConfig

class StarterServiceConfig extends ServerConfig[StarterServiceServer] {
  var port: Int = 3009
  var name: String = "StarterService"

  var runtime: RuntimeEnvironment = null

  def apply(_runtime: RuntimeEnvironment) = {
    require(port > 0, "Port must be specified and greater than 0")
    require(isPortAvailable(port) == true, "Already listening on port " + port)
    require(_runtime != null, "Need a runtime")
    require(name != null && name.length > 0, "Need a service name")

    runtime = _runtime

    new StarterServiceServer(this)
  }
}

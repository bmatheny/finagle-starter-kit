package net.mobocracy.starter

import java.security.MessageDigest

case class RequestHash(original: String, hash: String = "SHA-512") {
  require(original != null, "Can not hash null string")
  private val hasher = MessageDigest.getInstance(hash)
  val hashed = {
    hasher.update(original.getBytes("UTF-8"))
    hasher.digest.map { "%02x" format _ }.mkString
  }
}

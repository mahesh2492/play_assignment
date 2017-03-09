package Models

import services.UserService

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 5/3/17.
  */
object Operations {
  def addUser(user:User) = UserService.listOfUser += user

  def getUsers:ListBuffer[User]= UserService.listOfUser

  val listOfUsersNames = new ListBuffer[String]()


  def hash(s: String) = {
    val m = java.security.MessageDigest.getInstance("MD5")
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

}

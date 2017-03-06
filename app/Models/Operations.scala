package Models

import services.UserService

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 5/3/17.
  */
object Operations {
  def addUser(user:User) = UserService.listOfUser += user

  def getUsers:ListBuffer[User]= UserService.listOfUser

}

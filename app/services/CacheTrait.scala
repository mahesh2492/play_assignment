package services

import Models.User



trait CacheTrait {
//adding boolean
  def addToCache(value:String,userObject:User):Boolean
  def getFromCache(value:String):Option[User]


}
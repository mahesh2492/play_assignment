package services

import javax.inject.Inject

import Models.User
import play.api.cache.CacheApi

class CacheImplementation @Inject() (cache: CacheApi) extends CacheTrait {



  def addToCache(value:String,newObject:User)={

    cache.set(value, newObject)
    true
  }
  def getFromCache(value:String)={

    cache.get[User](value)
  }
}
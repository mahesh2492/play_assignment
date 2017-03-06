package Models

import play.api.libs.json.{Json, OFormat}

import scala.collection.mutable.ListBuffer
case class User (
                  fname:String,
                  mname:String,
                  lname:String,
                  uname:String,
                  password:String,
                  repassword:String,
                  mobile:String,
                  gender:String,
                  age:Int,
                  hobbies:String
                )

object User {
  implicit val userJson: OFormat[User] = Json.format[User]

}






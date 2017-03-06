package Models

import play.api.libs.json.{Json, OFormat}

case class UserSignIn (username: String,password:String)

object UserSignIn {
  implicit val userJson: OFormat[UserSignIn] = Json.format[UserSignIn]

}
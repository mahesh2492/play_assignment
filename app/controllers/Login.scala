package controllers

import javax.inject._
import Models.{UserSignIn, Operations}
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

@Singleton
class Login @Inject() extends Controller{

  val userForm: Form[UserSignIn] = Form {
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText


    )(UserSignIn.apply)(UserSignIn.unapply)
  }
  val users = Operations.getUsers

  def showProfile(name:String) = Action {implicit request =>
   val usr = users.filter(_.uname == name).head
    Ok(views.html.userinfo(usr))
  }



  def showForm() = Action {
    Ok(views.html.login())
  }


  def processForm= Action{ implicit request =>
    userForm.bindFromRequest.fold (
      formWithErrors => {
        Logger.info("error occurred")
        Redirect(routes.Login.showForm()).flashing("Error Message"->"Incorrect username or password")
      },
      userData => {
        Logger.info(userData.toString)
        val flag = users.map(x => if (x.uname == userData.username && x.password == userData.password) true else false)
        if (flag.contains(true)) {

          Redirect(routes.Login.showProfile(userData.username)).withSession("currentUser" -> userData.username).flashing("msg" -> "Login Successful")
        }
        else {
          Logger.info("error ")
        Redirect(routes.Login.showForm()).flashing("msg" -> "Incorrect username or password")
      }

      }
    )
  }


}

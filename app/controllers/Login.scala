package controllers

import javax.inject._
import Models.{User, UserSignIn, Operations}
import play.api._
import play.api.cache.CacheApi
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import services.CacheTrait

@Singleton
class Login @Inject() (cacheService: CacheTrait) extends Controller{

  val userForm: Form[UserSignIn] = Form {
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText


    )(UserSignIn.apply)(UserSignIn.unapply)
  }
//  val users = Operations.getUsers

  def showProfile(name:String) = Action { implicit request =>
    val data: Option[User] =cacheService.getFromCache(name)

    data match {
      case Some(x) if(x.isadmin == true) => Ok(views.html.admininfo(x))
      case Some(x) if(x.isadmin == false) => Ok(views.html.userinfo(x))

    }
  }



  def showForm() = Action { implicit request =>
    Ok(views.html.login()).flashing("a" -> "a")
  }


  def processForm= Action{ implicit request =>
    userForm.bindFromRequest.fold (
      formWithErrors => {
        Logger.info("error occurred")
        Redirect(routes.Login.showForm()).flashing("Error Message"->"Something went wrong. Please Try again later")
      },
      userData => {
        val data = cacheService.getFromCache(userData.username)

        val encrypt = Operations.hash(userData.password)
        println(encrypt)

        val flag = data.map(x => if (x.uname == userData.username && x.password == encrypt) true else false)
        if (flag.contains(true)) {
          data match{
            case Some(x) if(x.status)  => Redirect(routes.Login.showProfile(userData.username))
            case _ =>  Redirect(routes.Login.showForm()).flashing("Status"->"Permission Denied")
          }
        }
        else {
          Logger.info("error ")
        Redirect(routes.Login.showForm()).flashing("msg" -> "Incorrect username or password")
      }

      }
    )
  }


}

package controllers
import play.api.Logger
import javax.inject._
import Models.{Operations, User}
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

@Singleton
class Signup @Inject() extends Controller{

  val userForm: Form[User] = Form {
    mapping(
      "fname" -> nonEmptyText,
      "mname" -> text,
      "lname" -> nonEmptyText,
      "uname" -> nonEmptyText,
      "password" -> nonEmptyText,
      "repassword" -> nonEmptyText,
      "mobile" -> nonEmptyText,
      "gender" ->nonEmptyText,
      "age" -> number(min = 18, max = 75),
      "hobbies" -> nonEmptyText

    )(User.apply)(User.unapply)
  }



  def showForm() = Action {
    Ok(views.html.signup())
  }

  val users = Operations.getUsers
  def addPerson= Action{ implicit request =>
    userForm.bindFromRequest.fold (
      formWithErrors => {
        Logger.info("error occurred")

        Redirect(routes.HomeController.index()).flashing("BadRequest"->"something Went Wrong,Try Later")
      },
      userData => {
        Logger.info(userData.toString)
        val currentUser = userForm.bindFromRequest.get
        val flag = users.map( x=> if(x.uname == currentUser.uname) true else false)
       if(!flag.contains(false))
         {
           if(userData.password != userData.repassword)
             Redirect(routes.HomeController.index()).flashing("PasswordMismatch"->"Pasword dosent match")
           else {
             Operations.addUser(userData)
             Redirect(routes.Login.showProfile(userData.uname))
           }
         }
        else
         Redirect(routes.HomeController.index()).flashing("Already_Exist"->"Username is already taken")



      }
  )
  }


}
package controllers
import play.api.Logger
import javax.inject._
import Models.{Operations, User}
import play.api._
import play.api.cache.CacheApi
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.CacheTrait

@Singleton
class Signup @Inject()(cache: CacheApi,cacheService: CacheTrait) extends Controller{

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
      "hobbies" -> nonEmptyText,
      "isadmin"  -> boolean,
      "status" -> boolean

    )(User.apply)(User.unapply)
  }



  def showForm() = Action { implicit request =>
    Ok(views.html.signup()).flashing("a" -> "a")
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
        //val flag = users.map( x=> if(x.uname == userData.uname) true else false)
        val flag=cacheService.getFromCache(userData.uname)
       if(flag == None)
         {
           if(userData.password != userData.repassword)
             Redirect(routes.Login.showForm()).flashing("PasswordMismatch"->"Pasword dosent match")
           else {

             val encrypt = Operations.hash(userData.password)
             println(encrypt)
             val encryptedUserdata = userData.copy(password = encrypt)
             cacheService.addToCache(userData.uname,encryptedUserdata)

             Operations.listOfUsersNames += userData.uname
             Redirect(routes.Login.showProfile(userData.uname))
           }
         }
       else {
         Logger.info("i am already taken")
         Redirect(routes.Login.showForm()).flashing("Already_Exist" -> "Username is already taken")
       }


      }
  )
  }


}










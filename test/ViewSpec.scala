import Models.User
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc.Flash
import play.api.test.Helpers.{contentAsString, _}
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class ViewsSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "Rending index page" in new App {
    val a = mock[Flash]
    val html = views.html.index()(a)
    contentAsString(html) must include("Welcome!")
  }

  "Rending signup page" in new App {
    val a = mock[Flash]
    val html = views.html.signup()(a)
    contentAsString(html) must include("Create a new account")
  }


  "Rending sigin page" in new App {
    val a = mock[Flash]
    val html = views.html.login()(a)
    contentAsString(html) must include("Sign in to continue")
  }

  "Rending profile page" in new App {

    val html = views.html.admininfo(

      User("mahesh", "chand", "kandpal", "mahesh24", "male", "aa", "", "", 11, "", true, true))
    contentAsString(html) must include("mahesh24")
  }

  "Rending admin privilege page" in new App {

    val html = views.html.adminprivileges(List("aa"))

    contentAsString(html) must include("aa")
  }

}
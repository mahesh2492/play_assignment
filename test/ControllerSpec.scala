import controllers.{AdminController, HomeController, Login}
import Models.User
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, contentType, status, _}
import services.CacheImplementation

class ControllerSpec extends PlaySpec with OneAppPerTest with MockitoSugar {


  "HomeController" should {

    "render the index page" in {

      val homeObj = new HomeController
      val home = homeObj.index().apply(FakeRequest())
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome")
    }

  }

  "Login" should {

    "showProfile" in {
      val customCache = mock[CacheImplementation]
      val showObj = new Login(customCache)
      val user = User("mahesh", "chand", "kandpal", "mahesh24", "male", "aa", "", "", 11, "", true, true)
      customCache.addToCache("aa", user)
      val userDetails: Option[User] = customCache.getFromCache("aa")
      when(userDetails) thenReturn Some(user)
      val home = showObj.showProfile("aa").apply(FakeRequest())
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Admin Profile")
    }

  }



  "AdminController" should {

    "suspend" in {
      val customCache = mock[CacheImplementation]
      val showObj = new AdminController(customCache)
      val user = User("mahesh", "chand", "kandpal", "mahesh24", "male", "aa", "", "", 11, "", true, true)
      customCache.addToCache("aa", user)
      val userDetails: Option[User] = customCache.getFromCache("aa")
      when(userDetails) thenReturn Some(user)
      val home = showObj.suspend("aa").apply(FakeRequest())
      status(home) equals 303
    }


    "resume" in {
      val customCache = mock[CacheImplementation]
      val showObj = new AdminController(customCache)
      val user = User("mahesh", "chand", "kandpal", "mahesh24", "male", "aa", "", "", 11, "", true, true)
      customCache.addToCache("aa", user)
      val userDetails: Option[User] = customCache.getFromCache("aa")
      when(userDetails) thenReturn Some(user)
      val home = showObj.resume("aa").apply(FakeRequest())
      status(home) equals 303
    }


  }

}

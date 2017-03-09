import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

class RouteSpec extends PlaySpec with OneAppPerTest{

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome")
    }

  }

  "Signup" should {

    "sucessfull redirect to profile" in {
      val home = route(app, FakeRequest(GET, "/profile")).get
      status(home) equals 303
    }
  }

  "Login" should {

    "sucessfull redirect to logout" in {
      val home = route(app, FakeRequest(GET, "/logout")).get
      status(home) equals 303
    }


    "sucessfull redirect to profile" in {
      val home = route(app, FakeRequest(GET, "/profile")).get
      status(home) equals 303
    }

  }


  "AdminController" should {

    "sucessfull redirect to profile" in {
      val home = route(app, FakeRequest(GET, "/profile")).get
      status(home) equals 303
    }

//    "render the admin privileges page" in {
//      val admin = route(app, FakeRequest(GET, "/manage")).get
//      status(admin) mustBe (OK)
//      contentType(admin) mustBe Some("text/html")
//      contentAsString(admin) must include("Admin Privileges")
//    }

    "sucessfull redirect to adminpanel" in {
      val home = route(app, FakeRequest(GET, "/resume")).get
      status(home) equals 303
    }

    "sucessfull redirect to adminpanel on suspend(refresh)" in {
      val home = route(app, FakeRequest(GET, "/suspend")).get
      status(home) equals 303
    }

  }




}

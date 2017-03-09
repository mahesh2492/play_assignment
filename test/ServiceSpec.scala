import Models.User
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.cache.CacheApi
import services.{CacheTrait, CacheImplementation}

class ServiceSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "Tesing set method of cache trait" should {
    "get the person with the specified username" in {
      val cache = mock[CacheApi]
      val mockObj = new CacheImplementation(cache)
      when(mockObj.getFromCache(" ")) thenReturn Option(User.apply("mahesh", "chand", "kandpal", "mahesh24", "male", "aa", "", "", 11, "", true, true))

    }
  }

  "Tesing set method of cache trait" should {
    "add to cache" in {
      val cache = mock[CacheApi]
      val customCache = mock[CacheTrait]
      val user = User("mahesh", "chand", "kandpal", "mahesh24", "male", "aa", "", "", 11, "", true, true)
      val service = new CacheImplementation(cache)
      customCache.addToCache("aa", user)
      when(cache.get[User]("aa")) thenReturn Some(user)

    }


  }
}
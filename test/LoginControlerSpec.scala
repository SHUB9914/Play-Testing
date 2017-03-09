import controllers.LoginController
import models.UserData
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.cache.CacheApi
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentType, status, _}
import services.ImpConfService

class LoginControlerSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  val mockImpConfService = mock[ImpConfService]
  val cacheConfService = mock[CacheApi]
  val obj = new LoginController(cacheConfService,mockImpConfService)




  "LoginController should be " should {

    "render to the login page" in {
      val result = obj.index.apply(FakeRequest(GET,"/login"))
      status(result) mustEqual(OK)
    }


    "render the login  page" in {
      when(cacheConfService.get[UserData]("")) thenReturn None
      val result = obj.login.apply(FakeRequest(GET, "/userlogin").withFormUrlEncodedBody(("name","s"),("pass","svgsdvg")))
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")

    }
  }

}

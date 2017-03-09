import controllers.HomeController
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, OK, status}

class HomeControllerSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "HomeController" should {

    "render to the view page" in {
      val obj = new HomeController
      val result = obj.index.apply(FakeRequest(GET,"/"))
      status(result) mustEqual(OK)

    }

  }
}
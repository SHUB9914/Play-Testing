
import controllers.SignupController
import models.UserData
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.cache.CacheApi
import play.api.test.FakeRequest
import play.api.test.Helpers.{POST, contentType, status, _}
import services.ImpConfService
class SignUpControlerSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  val mockImpConfService = mock[ImpConfService]
  val cacheConfService = mock[CacheApi]
  val obj = new SignupController(cacheConfService,mockImpConfService)

  "SignupController should be " should {


    "render the signup page" in {
      val result = obj.index.apply(FakeRequest(GET, "/signup"))
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")
      contentAsString(result) must include("Gender:")
    }


    "render the profile  page" in {
      val result = obj.createUser.apply(FakeRequest(POST, "/create").withFormUrlEncodedBody(("username","s"),("firstname","a"),("middlename","b"),("lastname","a"),("age","25"),("pass","12345"),("mobile","55"),("gender","a"),("hobbies","j"),("status","admin"),("isSuspended","true")))
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")

    }

    "block the user and render the admin page" in {
      val id = "SHUB9914"
      when(mockImpConfService.getType) thenReturn "admin"
      when(cacheConfService.get[UserData](id)) thenReturn Some(UserData(" "," "," "," ", 24, " ","9999999999", " ",List("shubham"), " " ,"false"))
      val result = obj.delete(id).apply(FakeRequest(POST,"/delete1/:id"))
      status(result) mustEqual(OK)

    }


    "resume block user and render the admin page" in {
      val id = "SHUB9914"
      when(mockImpConfService.getType) thenReturn "admin"
      when(cacheConfService.get[UserData](id)) thenReturn Some(UserData(" "," "," "," ", 24, " ","9999999999", " ",List("shubham"), " " ,"true"))
      val result = obj.delete(id).apply(FakeRequest(POST,"/view/:id"))
      status(result) mustEqual(OK)

    }


    "render the admin page" in {
      val result = obj.index.apply(FakeRequest(GET, "/show"))
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")

    }



  }

}

import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

class ApplicationSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(OK)
    }

  }

  "routes should be " should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Our mission")
    }

  }

  "SignupController" should {

    "render the index page" in {
      val login = route(app, FakeRequest(GET, "/login")).get

      status(login) mustBe OK
      contentType(login) mustBe Some("text/html")

    }

    "render the signup page" in {
      val signup = route(app, FakeRequest(GET, "/signup")).get

      status(signup) mustBe OK
      contentType(signup) mustBe Some("text/html")
      contentAsString(signup) must include("Gender:")

    }

    "hendle profile  page with error" in {
      val profile = route(app, FakeRequest(GET, "/profile")).get
      println(">>>>>>>>>>>>>>>>>>>>>>>>" + contentAsString(profile))
      status(profile) mustBe SEE_OTHER



    }


    "render the profile  page" in {
      val create = route(app, FakeRequest(POST, "/create").withFormUrlEncodedBody(("username","s"),("firstname","a"),("middlename","b"),("lastname","a"),("age","25"),("pass","12345"),("mobile","55"),("gender","a"),("hobbies","j"),("status","admin"),("isSuspended","true"))).get

      status(create) mustBe OK
      contentType(create) mustBe Some("text/html")

    }

    "render the login  page" in {
      val login = route(app, FakeRequest(GET, "/userlogin").withFormUrlEncodedBody(("name","s"),("pass","svgsdvg"))).get
      status(login) mustBe OK
      contentType(login) mustBe Some("text/html")

    }

    " view route render to the error  page" in {
      val view = route(app, FakeRequest(GET, "/view/:id")).get
      status(view) mustBe OK
      contentType(view) mustBe Some("text/html")

    }
    " delete route render to the error  page" in {
      val delete = route(app, FakeRequest(GET, "delete1/:id")).get
      status(delete) mustBe OK
      contentType(delete) mustBe Some("text/html")

    }

    "  render to the show  page" in {
      val show = route(app, FakeRequest(GET, "delete1/:id")).get
      status(show) mustBe OK
      contentType(show) mustBe Some("text/html")

    }



  }
//
//  "SignupController should be " should {
//
//    "render the index page" in {
//      val id ="s01"
//      val mockDataService = mock[ImpConfService]
//      when(mockDataService.getType) thenReturn "admin"
//      val mockcacheService =  mock[CacheApi]
//      when(mockcacheService.get[UserData](id).get) thenReturn UserData("shubham","s","","", 25, "","","",List(""),"","")
//
//      val obj = new SignupController(mockcacheService,mockDataService)
//
//    }
//
//  }

//  "MyService#ConfService" should {
//    "render the admin  page " in {
//      val mockDataService = mock[ConfService]
//      when(mockDataService.getType) thenReturn "admin"
//    }
//  }



}

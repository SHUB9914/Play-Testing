package controllers
import javax.inject._
import models.UserData
import play.api.cache._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, _}
import services.{ImpConfService, MD5}

@Singleton
class SignupController @Inject()(cache: CacheApi,statusService: ImpConfService)(implicit val messagesApi: MessagesApi) extends Controller with I18nSupport {
  val status = statusService.getType
  val loginForm: Form[UserData] = Form(
    mapping(
      "username" -> nonEmptyText,
      "firstname" -> nonEmptyText,
      "middlename" -> text,
      "lastname" -> nonEmptyText,
      "age" -> number(min = 18, max = 75),
      "pass"-> nonEmptyText,
      "mobile" -> nonEmptyText,
      "gender" -> nonEmptyText,
      "hobbies"-> list(text),
      "status"->text,
      "isSuspended"->text
    )(UserData.apply)(UserData.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.singup("",loginForm,status))
  }

  def createUser = Action{ implicit request=>
    loginForm.bindFromRequest.fold(
      errorForm => {
        BadRequest(views.html.singup("", errorForm,status))
      }, validForm => {
        val user = cache.get[models.UserData](validForm.name)
        val status = statusService.getType
        if (status =="admin") {
          statusService.userdata+=validForm.name
          user match {
            case Some(admindata)=>  Redirect(routes.LoginController.index()).flashing("success" -> "User already exist please Login")
            case None => val encrypt = validForm.copy(pass = MD5.hash(validForm.pass))
              cache.set(validForm.name,encrypt)
              Ok(views.html.admin( statusService.userdata)) withSession (request.session + ("mySession" -> s"${validForm.name}"))
          }

        } else {
          user match {
            case Some(userdata) => Redirect(routes.LoginController.index()).flashing("success" -> "User already exist please Login")
            case None =>  val encrypt = validForm.copy(pass = MD5.hash(validForm.pass))
              cache.set(validForm.name, encrypt)
              Redirect(routes.ProfileController.index()) withSession (request.session + ("mySession" -> s"${validForm.name}"))
          }
        }

      }
    )
  }



  def delete(id:String) = Action { implicit  request =>

    val user =  cache.get[UserData](id).get
    val update =  user.copy(isSuspended = "true")
    cache.remove(id)
    cache.set(id, update)
    Ok(views.html.admin(statusService.userdata))
  }

  def view(id:String) = Action{ implicit request =>
    val userdata =  cache.get[UserData](id).get
    val update =  userdata.copy(isSuspended = "false")
    cache.remove(id)
    cache.set(id, update)
    Ok(views.html.admin(statusService.userdata))
  }

  def show = Action { implicit request =>
    Ok(views.html.admin(statusService.userdata))
  }

}

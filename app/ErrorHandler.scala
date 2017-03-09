import controllers.routes
import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import javax.inject.Singleton;

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    statusCode match{

      case 400 =>   Future.successful(Redirect(routes.HomeController.index()))
      case 404 =>   Future.successful(Redirect(routes.HomeController.index())); Future.successful(NotFound)
      case _   =>     Future.successful(BadRequest)

    }

  }


  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(Redirect(routes.HomeController.index()))
  }
}
package controllers


import play.api.mvc.{ Controller, Action}


class AccountInfo extends Controller {

  def showForm = Action { implicit request =>
    if (request.session.get("name").isEmpty)
      Redirect(routes.Login.showForm())
    else
      Ok(views.html.home())
  }

}
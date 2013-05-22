package controllers.backend;

import play.*;
import play.mvc.*;

import controllers.CompressController;
import views.html.*;
import views.html.frontendView.*;
import views.html.backendView.*;

public class Backend extends CompressController {

	public static Result index(){

		return ok(dashboard_index.render("Ini parameter"));
	}

}
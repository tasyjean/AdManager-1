package controllers;


import play.*;
import play.mvc.*;
import controllers.frontend.*;
import views.html.*;
import views.html.frontendView.*;
import controllers.frontend.FrontEndController;

public class Application extends CompressController {
  
    public static Result index() {

    	//SOLUTIOOOON
    	return redirect(controllers.frontend.routes.FrontEndController.home());

    	// return redirect(controllers.frontend.routes.Frontend.home());
    }
    
}

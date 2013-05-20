package controllers;


import play.*;
import play.mvc.*;
import controllers.frontend.*;
import views.html.*;
import views.html.frontend.*;
import controllers.frontend.StaticPages;

public class Application extends CompressController {
  
    public static Result index() {

    	//SOLUTIOOOON
    	return redirect(controllers.frontend.routes.StaticPages.home());

    	// return redirect(controllers.frontend.routes.Frontend.home());
    }
    
}

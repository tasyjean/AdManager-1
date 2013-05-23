package controllers.backend;

import java.util.HashMap;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.data.User;
import models.service.Authentificator;
import play.*;
import play.mvc.*;

import controllers.CompressController;
import views.html.*;
import views.html.frontendView.*;
import views.html.backendView.*;

public class Backend extends CompressController {

	private static Authentificator auth=new Authentificator();
	@SubjectPresent
	public static Result index(){
		User user=auth.getUserLogin(session());
		if(user!=null){
			return ok(dashboard_index.render("Ini parameter" + user.getEmail() + " "+ user.getFront_name() ));
		}else{
			return forbidden(home.render(new HashMap<String, String>()));
		}
	}

}
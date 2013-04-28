package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
    	//Just want to test
    	//test kedua kali
        return ok(index.render("Your new application is ready."));
    }
  
}

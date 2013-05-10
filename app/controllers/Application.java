package controllers;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

import play.*;
import play.mvc.*;
import controllers.frontend.*;
import views.html.*;
import views.html.frontend.*;

public class Application extends CompressController {
  
    public static Result index() {

    	return ok(home.render("Home"));
    }
    
}

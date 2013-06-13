package controllers.security;

import models.service.Authenticator;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import views.html.*;


public class SecurityHandler extends AbstractDeadboltHandler {

	Authenticator auth = new Authenticator();
	@Override
	public Result beforeAuthCheck(Context context) {
	
		return null;
	}
	public Subject getSubject(Context context){
		
		return auth.getUserLogin(context.session());
	}
	public DynamicResourceHandler getDynamicResourceHandler(Http.Context context){
		
		return new DynamicHandler();
	}
	
	public Result onAuthFailure(Context context, String content){
		String source=context.request().getHeader(Http.HeaderNames.REFERER);
		return forbidden(error404.render(source));
	}

}

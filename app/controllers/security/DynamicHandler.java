package controllers.security;

import play.mvc.Http.Context;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;

public class DynamicHandler implements DynamicResourceHandler {

	@Override
	public boolean checkPermission(String arg0, DeadboltHandler arg1,
			Context arg2) {

		return false;
	}
	@Override
	public boolean isAllowed(String arg0, String arg1, DeadboltHandler arg2,
			Context arg3) {
		// TODO Auto-generated method stub
		return false;
	}


}

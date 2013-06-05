package controllers;


import java.util.ArrayList;

import models.data.ZoneChannel;

import play.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import play.mvc.Http.RequestHeader;
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
    
    public static Result testRequest(){
    	
    	String host=request().getHeader(HOST);
    	String method=request().method();
    	String host2=request().host();
    	String req=request().path();
    	String remoteAdress=request().remoteAddress();
    	String uri=request().uri();
    	String source=request().getHeader(REFERER);
    	String agent=request().getHeader(USER_AGENT);
    	
    	
    	ArrayList<String> data=new ArrayList<String>();
    	data.add(host);
    	data.add(method);
    	data.add(host2);
    	data.add(req);
    	data.add(remoteAdress);
    	data.add(uri);
    	data.add(source);
    	data.add(agent);
    	RequestBody body = request().body();
//    	return ok("Got json: " + body.asText());
    	
    	
    	return ok(testView.render(data));
    	
    }
    
    public static Result testWrite(){
    	
    	ZoneChannel channel=new ZoneChannel();
    	channel.setChannel_name("Channel 1");
    	channel.setChannel_description("Channel Name");
    	
    	channel.save();
    	
    	return ok();
    	
    }
    
}

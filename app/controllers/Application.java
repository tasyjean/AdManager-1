package controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import models.custom_helper.file_manager.FileManager;
import models.data.ZoneChannel;

import play.*;
import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import play.mvc.Http.RequestHeader;
import controllers.frontend.*;
import views.html.*;
import views.html.test.*;
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
    	String play_path=Play.application().path().getAbsolutePath();
    	String play_path2 = "";
    	FileManager manager=new FileManager();
    	String pathfile=manager.getFilePath(11);
    	try {
			play_path2=Play.application().path().getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String play_path3=Play.application().path().getName();
    	String play_path4=Play.application().path().getPath();
    	String play_path5=Play.application().path().toString();

    	
    	ArrayList<String> data=new ArrayList<String>();
    	data.add(host);
    	data.add(method);
    	data.add(host2);
    	data.add(req);
    	data.add(remoteAdress);
    	data.add(uri);
    	data.add(source);
    	data.add(agent);
    	data.add(play_path);
    	data.add(play_path2);
    	data.add(play_path3);
    	data.add(play_path4);
    	data.add(play_path5);
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
    
    public static Result testSession(){
    	
    	String date=new Date().toString().replace(" ", "");
    	flash("date",date);
    	session("session_date"+date,"132");
    	return ok(test1.render());
    }
    public static Result testSession2(){
    	DynamicForm form=Form.form().bindFromRequest();
    	form.get("id");
    	return ok();
    }
}

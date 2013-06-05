package controllers.backend;

/*
 * @Author Xenovon
 * Kelas UserController digunakan untuk menangani request berkaitan dengan 
 * manajemen pengguna, khususnya untuk administrator 
 */
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.dataWrapper.TemplateData;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.user_view.*;

public class UserController extends CompressController {

	@Restrict({@Group("administrator"), @Group("manager")})
	@With(DataFiller.class)
	public static Result showUser(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(user_index.render(data));
	}
	
	public static Result showSingleUser(int user_id){
		return ok();
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result createUser(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		return ok(create_user.render(data));
	}
	
	public static Result saveUser(){
		return ok();
	}
	
	public static Result editUser(int user_id){
		return ok();
	}
	
	public static Result saveEditUser(){
		return ok();
	}



}

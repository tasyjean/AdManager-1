package controllers.backend;

/*
 * @Author Xenovon
 * Kelas UserController digunakan untuk menangani request berkaitan dengan 
 * manajemen pengguna, khususnya untuk administrator 
 */
import java.io.File;


import com.amazonaws.services.s3.transfer.Upload;
import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.SaveToEnum;
import models.data.User;
import models.dataWrapper.TemplateData;
import models.service.user.UserCreator;
import models.service.user.UserDelete;
import models.service.user.UserFetch;
import play.Play;
import play.mvc.Http.*;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.user_view.*;

public class UserController extends CompressController {

	public static FileManager manager=new FileManager();
	public static UserFetch fetch=new UserFetch();
	public static UserCreator creator=new UserCreator(manager);
	public static UserDelete  delete =new UserDelete();
	
	
	@Restrict({@Group("administrator"), @Group("manager")})
	@With(DataFiller.class)
	public static Result showUser(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<User> page=fetch.getUser(0,40, "", "");
		return ok(user_index.render(data, page));
	}
	
	@Restrict({@Group("administrator"), @Group("manager")})
	@With(DataFiller.class)
	public static Result showUserPage(int page_number){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<User> page=fetch.getUser(page_number,40, "", "");
		return ok(user_index.render(data, page));
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
		
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			int id=manager.saveNew(picture, SaveToEnum.PROFILE_PICTURE);
			if(id!=0){
				manager.saveThumbnail(id);
				return ok(test_upload.render(manager.getThumbnailURL(id),manager.getFilePath(id)));			
			}else{
				
				return badRequest("upload gagal");
			}
		} else {
		  flash("error", "Missing file");
		  return badRequest("Salah");    
		}		
	}
	
	public static Result editUser(int user_id){
		return ok();
	}
	
	public static Result saveEditUser(){
		return ok();
	}
	

/*
 * 
 * 		  String fileName = picture.getFilename();
		  fileName=fileName.replace(" ", "_");
		  
		  String contentType = picture.getContentType(); 
		  File file = picture.getFile();
		  
		  String path=Play.application().path()+"/public/images/upload/"+fileName;
		  file.renameTo(new File(path));

		  String x="File uploaded dengan nama " + file.getName()+" <b>ukuran<b> "+file.length()/1000+" KB <img src='"+file.toURI()+"'>";
 */

}

package controllers.backend;

/*
 * @Author Xenovon
 * Kelas UserController digunakan untuk menangani request berkaitan dengan 
 * manajemen pengguna, khususnya untuk administrator 
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;


import com.amazonaws.services.s3.transfer.Upload;
import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.custom_helper.SendMail;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.SaveToEnum;
import models.data.User;
import models.data.UserContact;
import models.data.Zone;
import models.dataWrapper.TemplateData;
import models.dataWrapper.user.UserContactData;
import models.form.backendForm.userForm.ContactForm;
import models.form.backendForm.userForm.UserForm;
import models.form.backendForm.zoneForm.ZoneForm;
import models.service.user.UserCreator;
import models.service.user.UserDelete;
import models.service.user.UserFetch;
import play.Play;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.mvc.Http.*;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.user_view.*;
import views.html.backendView.zone_view.create_zone;
import views.html.backendView.zone_view.create_zone_success;

public class UserController extends CompressController {

	public static FileManager manager=new FileManager();
	public static UserFetch fetch=new UserFetch();
	public static SendMail mailer=new SendMail();
	public static UserCreator creator=new UserCreator(manager, mailer);
	public static UserDelete  delete =new UserDelete();
	
	final static Form<UserForm> userForm = Form.form(UserForm.class);
	final static Form<ContactForm> contactForm = Form.form(ContactForm.class);
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
	
	@With(DataFiller.class)
	@Restrict({@Group("administrator"), @Group("manager")})
	public static Result showSingleUser(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		User user=fetch.getSingleUser(id_user);
		return ok(show_single_user.render(data, user));
	}
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result createUser(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		
		return ok(create_user.render(data,userForm));
	}	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result createUserStep(int step, int id){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		if(step==2){
			List<UserContact> user_contact=null;
			try {
				user_contact = User.find.byId(1).getUserContact();
			} catch (Exception e) {
				e.printStackTrace();
			}
			UserContactData contact_data=new UserContactData();
			return ok(create_user2.render(data,id, contactForm, contact_data, user_contact));
		}else {
			return ok(create_user3.render(data, id));
		}
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result saveUserPicture(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart part = body.getFile("picture");
		if (part!= null) {
			int result=creator.saveProfilePicture(part, id_user);
			if(result==0){
				flash("success","Data Pengguna ditambahkan");
				return ok(show_single_user.render(data, User.find.byId(id_user)));	 //redirect					
			}else if(result==2){
				flash("error","Hanya mendukung file jpg, png dan gif");
			}else{
				flash("error","Kesalahan saat upload");
			}
		} else {
			flash("error", "File Kosong");
		}
		return badRequest(create_user3.render(data, id_user));				

	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))		
	public static Result saveUser(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Form<UserForm> filledForm = Form.form(UserForm.class).bindFromRequest();
		if(filledForm.hasErrors()){
			return ok(create_user.render(data,filledForm));
		}else{
			try {
				User user=creator.saveUser(filledForm);
				flash("success","true");
				UserContactData contact_data=new UserContactData();
				List<UserContact> user_contact=null;
				try {
					user_contact = User.find.byId(user.getId_user()).getUserContact();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return ok(create_user2.render(data,user.getId_user(),contactForm, contact_data, user_contact));
			} catch (NullPointerException e) {
				e.printStackTrace();
				filledForm.globalErrors().add(new ValidationError("error_update", 
																   Messages.get("validation.error_update")));
				return ok(create_user.render(data,filledForm));
			}
			
		}		
	}
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result saveContact(int id){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Form<ContactForm> filledForm=Form.form(ContactForm.class).bindFromRequest();
		List<UserContact> user_contact=null;
		UserContactData contact_data=new UserContactData();
		
		try {
			user_contact = User.find.byId(id).getUserContact();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(filledForm.hasErrors()){
		
			return ok(create_user2.render(data, id, filledForm, contact_data, user_contact));			
		}else{
			boolean sukses=creator.saveContact(filledForm, id);
			if(sukses){
				flash("success","Data telah disimpan, lanjutkan pengisian data");
				return ok(create_user2.render(data, id, contactForm, contact_data,  user_contact));			
			}else{
				flash("error","Kesalahan dalam menyimpan data");
				return ok(create_user2.render(data, id, filledForm, contact_data,  user_contact));							
			}
		}


	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result editUserPicture(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		User user=User.find.byId(id_user);
		return ok(edit_user3.render(data, user));
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))		
	public static Result updateUserPicture(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart part = body.getFile("picture");
		if (part!= null) {
			
			int result=creator.updateProfilePicture(part, id_user);
			if(result==0){
				flash("edit","sukses");
				return ok(show_single_user.render(data, User.find.byId(id_user)));	 				
			}else if(result==2){
				flash("error","Hanya mendukung file jpg, png dan gif");
			}else{
				flash("error","Kesalahan saat upload");
			}
		} else {
			flash("error", "File Kosong");
		}
		return badRequest(edit_user3.render(data, id_user));		
	}
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result editUserBasic(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		return TODO;	
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result updateUserBasic(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		return TODO;
	}
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result editUserContact(int id_contact){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		return TODO;
	}	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result updateUserContact(int id_contact){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		return TODO;
	}
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result addUserContact(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		return TODO;
	}	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result newUserContact(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		return TODO;
	}		
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result deleteUser(int id_user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
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

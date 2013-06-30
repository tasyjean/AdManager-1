package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ProfileController digunakan untuk menangani request berkaitan dengan 
 * manajemen profile masing-masing user
 */
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.custom_helper.RoleFactory;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.FileManagerFactory;
import models.custom_helper.file_manager.FileManagerInterface;
import models.data.User;
import models.data.UserContact;
import models.dataWrapper.TemplateData;
import models.dataWrapper.user.UserContactData;
import models.form.backendForm.userForm.ContactForm;
import models.form.backendForm.userForm.EditUserForm;
import models.form.backendForm.userForm.UserForm;
import models.service.Authenticator;
import models.service.profile.ProfileModificator;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.profile_view.*;
import views.html.helper.form;

public class ProfileController extends CompressController {

	public static RoleFactory factory=new RoleFactory();
	public static FileManagerInterface manager=new FileManagerFactory().getManager();
	
	public static Authenticator auth=new Authenticator();
	public static ProfileModificator prm=new ProfileModificator(factory, manager);
	
	public static UserContactData contact_data=new UserContactData();
	
	final static Form<EditUserForm> editUserForm = Form.form(EditUserForm.class);
	final static Form<UserForm> userForm = Form.form(UserForm.class);
	final static Form<ContactForm> contactForm = Form.form(ContactForm.class);

	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showProfile(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		User user=auth.getUserLogin(session());
		return ok(profile_index.render(data, user));
	}
	
	
	@With(DataFiller.class)
	@SubjectPresent	
	public static Result editProfilePicture(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		User user=auth.getUserLogin(session());
		return ok(edit_profile3.render(data));
	}
	
	@With(DataFiller.class)
	@SubjectPresent		
	public static Result updateProfilePicture(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart part = body.getFile("picture");
		User user=auth.getUserLogin(session());
		if (part!= null) {
			int result=prm.saveProfilePicture(part, user);
			if(result==0){
				flash("edit","sukses");
				flash("success","Foto profile diubah");
				return redirect(routes.ProfileController.showProfile());		 				
			}else if(result==2){
				flash("error","Hanya mendukung file jpg, png dan gif");
			}else{
				flash("error","Kesalahan saat upload");
			}
		} else {
			flash("error", "File Kosong");
		}
		return badRequest(edit_profile3.render(data));	
	}
	@With(DataFiller.class)
	@SubjectPresent	
	public static Result editProfileBasic(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		User user=auth.getUserLogin(session());
		return ok(edit_profile.render(data,editUserForm,user));	
	}
	
	@With(DataFiller.class)
	@SubjectPresent
	public static Result updateProfileBasic(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Form<EditUserForm> filledForm=Form.form(EditUserForm.class).bindFromRequest();
		User user=auth.getUserLogin(session());
		if(filledForm.hasErrors()){
			return ok(edit_profile.render(data, filledForm, user));
		}else{
			try {
				user=prm.updateProfile(filledForm, user);
				flash("edit","Sukses Update Informasi profil dasar");
				flash("success","Sukses mengubah informasi");
				return redirect(routes.ProfileController.showProfile());	
			} catch (Exception e) {
				flash("error","Gagal memperbaharui data profil");
				return ok(edit_profile.render(data, filledForm, user));
			}
		}
	}
	@With(DataFiller.class)
	@SubjectPresent	
	public static Result editProfileContact(int id_contact){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		UserContact contact=UserContact.find.byId(id_contact);
		return ok(edit_profile2.render(data, contactForm, contact_data, contact));
	}	
	@With(DataFiller.class)
	@SubjectPresent	
	public static Result updateProfileContact(int id_contact){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Form<ContactForm> filledForm=Form.form(ContactForm.class).bindFromRequest();
		UserContact contact=UserContact.find.byId(id_contact);
		if(filledForm.hasErrors()){
			return ok(edit_profile2.render(data, filledForm, contact_data, contact));
		}else{
			try {
				UserContact sukses=prm.editContact(filledForm, id_contact);
				flash("edit","Sukses memperbaharui data profil");
				flash("success","Sukses memperbaharui data profil");
				
				return redirect(routes.ProfileController.showProfile());	
			} catch (Exception e) {
				flash("error","Gagal memperbaharui data profil");
				e.printStackTrace();
				return ok(edit_profile2.render(data, filledForm, contact_data, contact));
			}				
		}
	}
	@With(DataFiller.class)
	@SubjectPresent	
	public static Result addUserContact(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		User user=auth.getUserLogin(session());
		return ok(new_contact.render(data, contactForm, contact_data, user));
	}	
	@With(DataFiller.class)
	@SubjectPresent	
	public static Result newUserContact(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Form<ContactForm> filledForm=Form.form(ContactForm.class).bindFromRequest();
		User user=auth.getUserLogin(session());

		if(filledForm.hasErrors()){
			return ok(new_contact.render(data, filledForm, contact_data, user ));
		}else{
			boolean sukses=prm.saveContact(filledForm, user);
			if(sukses){
				flash("edit","Sukses menambah kontak");
				flash("success","Sukses menambah kontak");
				return redirect(routes.ProfileController.showProfile());	
			}else{
				flash("error","Gagal memperbaharui data profile");
				return ok(new_contact.render(data, filledForm, contact_data, user));				
			}
		}	
	}		
	@With(DataFiller.class)
	@SubjectPresent
	public static Result deleteContact(int id_contact){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		try{
			int id_user=prm.deleteContact(id_contact);
			flash("success", "Data berhasil dihapus");
		}catch(Exception e){
			flash("error", "Data gagal dihapus");
			e.printStackTrace();
		}finally{
			flash("edit","Awawaw");
		}
		return redirect(routes.ProfileController.showProfile());

	}

	@With(DataFiller.class)
	@SubjectPresent
	public static Result changePassword(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		return ok(new_password.render(data));
	}
	
	@With(DataFiller.class)
	@SubjectPresent
	public static Result savePassword(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DynamicForm form=Form.form().bindFromRequest();
		User user=auth.getUserLogin(session());
		String old_password=form.get("old_password");
		String new_password=form.get("new_password");
		String new_password_repeat=form.get("new_password_repeat");
		
			int result=prm.changePassword(old_password, new_password, new_password_repeat, user);			
			if(result==0){
				flash("success", "password berhasil dirubah");
				return redirect(routes.ProfileController.showProfile());
			}else if(result==1){
				flash("error", "Input password  tidak benar");
				return redirect(routes.ProfileController.changePassword());
			}else if(result==2){
				flash("error", "Dua password baru tidak sama");
				return redirect(routes.ProfileController.changePassword());								
			}else{
				flash("error", "Gagal melakukan perubahan password");
				return redirect(routes.ProfileController.changePassword());				
			}

	}
}

package controllers.backend;

import models.custom_helper.setting.SettingDefault;
import models.custom_helper.setting.SettingManager;
import models.data.User;
import models.dataWrapper.TemplateData;
import models.dataWrapper.setting.SettingData;
import models.service.setting.SettingEditor;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import views.html.error404;
import views.html.backendView.user_view.show_single_user;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.setting_view.*;


public class SettingController extends CompressController {
	
	/*
	 * Daftar Pengaturan
	 * -> Harga iklan dasar
	 * 	  -> Per klik
	 * 	  -> Per 1000 impressi
	 * 	  -> Per Hari
	 * 	  -> tingkat faktor diskon
	 * -> Default iklan untuk masing-masing ukuran zona
	 * -> 
	 */
	public static SettingManager settingManager=new SettingManager();
	public static SettingDefault settingDefault=new SettingDefault();
	public static SettingEditor settingEditor=new SettingEditor(settingManager,settingDefault);
	
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result showSetting(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		
		SettingData settingData= new SettingData(page);
		switch(page){
			case 1:return ok(showSetting1.render(data, settingData));
			case 2:return ok(showSetting2.render(data, settingData));
			default:redirect(controllers.backend.routes.SettingController.showSetting(1));
		}
		
		return ok(showSetting1.render(data, settingData));
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result saveSetting(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		DynamicForm dataForm=Form.form().bindFromRequest();
		
		if(page==1){
			String message=settingEditor.savePrice(dataForm);
			if(message.equals("sukses")){
				flash("success", Messages.get("success.preferences"));
				return redirect(controllers.backend.routes.SettingController.showSetting(1));
			}else{
				flash("error", message);
				return redirect(controllers.backend.routes.SettingController.showSetting(1));		
			}
		}else if(page==2){
			String message=settingEditor.saveBanner(dataForm);
			if(message.equals("sukses")){
				flash("success", Messages.get("success.preferences"));
				return redirect(controllers.backend.routes.SettingController.showSetting(2));				
			}else{
				if(message.equals("sukses")){
					flash("success", Messages.get(message));
					return redirect(controllers.backend.routes.SettingController.showSetting(2));								
				}
			}
		}
		return redirect(controllers.backend.routes.SettingController.showSetting(1));
	}
	@Restrict(@Group("administrator"))
	public static Result saveDefault(){
		settingEditor.setDefault();
		flash("success","Berhasil Mengembalikan Ke Settingan Awal");
		return redirect(controllers.backend.routes.SettingController.showSetting(1));

	}

}

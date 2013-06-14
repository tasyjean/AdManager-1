package controllers.backend;

import models.data.User;
import models.dataWrapper.TemplateData;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import views.html.backendView.user_view.show_single_user;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import controllers.CompressController;
import controllers.action.DataFiller;

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
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result showSetting(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		flash("edit","editing");
		return ok();
	}


}

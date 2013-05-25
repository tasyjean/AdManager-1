package controllers.backend;

import java.util.HashMap;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.data.User;
import models.dataWrapper.TemplateData;
import models.service.Authentificator;
import play.*;
import play.mvc.*;

import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.*;
import views.html.frontendView.*;
import views.html.backendView.*;

/*
 * 
 * Struktur utama dan menu utama halaman dahsboard, tapi non fungsionalitas
 */
public class DashboardController extends CompressController {

	private static Authentificator auth=new Authentificator();
	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		return ok(dashboard_index.render(data));
	}

}
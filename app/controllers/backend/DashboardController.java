package controllers.backend;

import java.util.HashMap;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.custom_helper.TemplateData;
import models.data.User;
import models.service.Authentificator;
import play.*;
import play.mvc.*;

import controllers.CompressController;
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
	public static Result index(){

		return ok(dashboard_index.render(""));
	}

}
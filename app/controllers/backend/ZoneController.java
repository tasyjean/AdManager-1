package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ZoneController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.dataWrapper.TemplateData;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.zone_view.*;

public class ZoneController extends CompressController {

	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(zone_index.render(data));
	}


}

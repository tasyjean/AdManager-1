package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ProfileController digunakan untuk menangani request berkaitan dengan 
 * manajemen profile masing-masing user
 */
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.dataWrapper.TemplateData;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.profile_view.*;

public class ProfileController extends CompressController {

	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(profile_index.render(data));
	}


}

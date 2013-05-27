package controllers.backend;

/*
 * @Author Xenovon
 * Kelas UserController digunakan untuk menangani request berkaitan dengan 
 * manajemen pengguna, khususnya untuk administrator 
 */
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.dataWrapper.TemplateData;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.user_view.*;

public class UserController extends CompressController {

	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(user_index.render(data));
	}

}

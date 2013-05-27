package controllers.backend;
/*
 * @Author Xenovon
 * Kelas FinanceController digunakan untuk menangani request berkaitan dengan 
 * manajemen keuangan
 */
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.dataWrapper.TemplateData;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.finance_view.*;

public class FinanceController extends CompressController {

	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(finance_index.render(data));
	}

}


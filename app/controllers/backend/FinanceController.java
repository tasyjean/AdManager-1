package controllers.backend;
/*
 * @Author Xenovon
 * Kelas FinanceController digunakan untuk menangani request berkaitan dengan 
 * manajemen keuangan
 */
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
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
	@Restrict({@Group("advertiser")})
	@With(DataFiller.class)
	public static Result newDeposito(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	

		return ok();
	}
	@Restrict({@Group("advertiser")})
	@With(DataFiller.class)
	public static Result saveDeposito(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	

		return ok();
	}
	@Restrict({@Group("advertiser")})
	@With(DataFiller.class)	
	public static Result deleteDeposito(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	

		return ok();
	}
	@Restrict({@Group("advertiser"), @Group("manager")})
	@With(DataFiller.class)	
	public static Result showSingleDeposito(int idDeposito){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	

		return ok();
	}
	@Restrict({@Group("advertiser"), @Group("manager")})
	@With(DataFiller.class)	
	public static Result listDeposito(int idDeposito){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	

		return ok();
	}

	@Restrict({@Group("advertiser"), @Group("manager")})
	@With(DataFiller.class)	
	public static Result validate(int idDeposito){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	

		return ok();
	}
	
//	@Restrict({@Group("administrator"), @Group("advertiser")})

	

}


package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ReportController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.custom_helper.DateBinder;
import models.data.Campaign;
import models.data.User;
import models.data.UserRole;
import models.data.enumeration.RoleEnum;
import models.dataWrapper.TemplateData;
import models.dataWrapper.report.ReportData;
import models.dataWrapper.report.UserListPage;
import models.service.Authenticator;
import models.service.report.ReportGenerator;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.report_view.*;
import views.html.helper.form;

public class ReportController extends CompressController {

	public static SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
	public static DateBinder binder=new DateBinder();
	public static ReportGenerator report=new ReportGenerator(binder);
	public static Authenticator auth=new Authenticator();
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DynamicForm form=Form.form().bindFromRequest();
		ReportData reportData=null;
		User selectedUser=null;
		try {
			if(auth.getUserRole(session()).getName().equals("advertiser")){
				selectedUser=auth.getUserLogin(session());
			}else{
				UserRole advertiser_role=UserRole.find.where().eq("name", RoleEnum.ADVERTISER).findUnique();
				List<User> userList=User.find.where().eq("role", advertiser_role).findList();
				if(userList.size()!=0){
					selectedUser=userList.get(0);				
				}else{
					return ok();
				}
			}
			Campaign campaign=null;
			Calendar calendar=Calendar.getInstance();
			Date to=calendar.getTime();
			calendar.set(Calendar.DATE, -90);
			Date from=calendar.getTime();
			
			reportData=report.getReport(selectedUser, campaign, from, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok(report_index.render(data, selectedUser, reportData));
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result indexWithForm(int user){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DynamicForm form=Form.form().bindFromRequest();
		ReportData reportData=null;
		User selectedUser;
		try {
			int idUser=user;
			int idCampaign;
			try {
				idCampaign = Integer.parseInt(form.get("campaign"));
			} catch (Exception e1) {
				idCampaign=0;
				e1.printStackTrace();
			}
			
			Campaign campaign=null;
			
			if(auth.getUserRole(session()).getName().equals("advertiser")){
				selectedUser=auth.getUserLogin(session());
			}			
			if(idCampaign!=0){
				campaign=Campaign.find.byId(idCampaign);				
			}			
			if(idUser!=0){
				selectedUser=User.find.byId(idUser);				
			}else{
				UserRole advertiser_role=UserRole.find.where().eq("name", RoleEnum.ADVERTISER).findUnique();
				List<User> userList=User.find.where().eq("role", advertiser_role).findList();
				if(userList.size()!=0){
					selectedUser=userList.get(0);				
				}else{
					return ok();
				}
			}
			Date from;
			Date to;
			try {
				from = format.parse(form.get("from"));
				to = format.parse(form.get("to"));
			} catch (Exception e) {
				Calendar calendar=Calendar.getInstance();
				to=calendar.getTime();
				calendar.set(Calendar.DATE, -90);
				from=calendar.getTime();
				e.printStackTrace();
				Logger.debug("Calendar error");
			}
			
			reportData=report.getReport(selectedUser, campaign, from, to);
		} catch (Exception e) {
			e.printStackTrace();
			return redirect(controllers.backend.routes.ReportController.index());
		}
		return ok(report_index.render(data, selectedUser, reportData));
	}
	
	@Restrict({@Group("administrator"),@Group("manager")})
	@With(DataFiller.class)
	public static Result userPerformanceReport(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		UserListPage userPage=report.getReportCMO(page-1, 30);
		
		return ok(management_report.render(data,userPage));
	}

}


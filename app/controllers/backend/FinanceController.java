package controllers.backend;
/*
 * @Author Xenovon
 * Kelas FinanceController digunakan untuk menangani request berkaitan dengan 
 * manajemen keuangan
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.AdsTransaction;
import models.data.Campaign;
import models.data.Deposito;
import models.data.TransferConfirmation;
import models.data.User;
import models.data.UserContact;
import models.dataWrapper.TemplateData;
import models.dataWrapper.campaign.CampaignList;
import models.dataWrapper.finance.DepositoData;
import models.dataWrapper.finance.UserFinancialData;
import models.form.backendForm.financeForm.DepositoForm;
import models.form.backendForm.financeForm.TransferForm;
import models.service.Authenticator;
import models.service.finance.DepositoOperator;
import models.service.finance.TransactionFetcher;
import models.service.notification.NotificationCenter;
import play.Logger;
import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.finance_view.*;

public class FinanceController extends CompressController {

	public static Form<TransferForm> transferForm=Form.form(TransferForm.class);
	public static Form<DepositoForm> depositoForm=Form.form(DepositoForm.class);
	public static Authenticator auth=new Authenticator();
	public static SettingManager setting=new SettingManager();
	public static NotificationCenter notif=new NotificationCenter();
	public static DepositoOperator deposit=new DepositoOperator(notif);
	public static TransactionFetcher fetcher=new TransactionFetcher();
	public static SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");

	@SubjectPresent
	@With(DataFiller.class)
	public static Result index_default(){
		List<User> user_list=new ArrayList<User>();	
		DepositoData depositoData=new DepositoData();
		user_list=depositoData.getUser();
		
		int user_id;
		if(user_list.size()!=0){
			user_id=user_list.get(0).getId_user();
		}else{
			user_id=5;
		}
		return redirect(controllers.backend.routes.FinanceController.index(user_id));
	}
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(int idUser){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DepositoData depositoData=null;
		List<User> user_list=new ArrayList<User>();		
		UserFinancialData financeData=null;
		User user=null;
		try {
			depositoData=new DepositoData();
			if(auth.getUserRole(session()).getName().equals("advertiser")){
				user=auth.getUserLogin(session());
			}else{
				user=User.find.byId(idUser);
				user_list=depositoData.getUser();
				if(user==null){
					user=user_list.get(0);
				}
			}
			financeData=new UserFinancialData(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok(finance_index.render(data, user, user_list, financeData));
		
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showAdsTransactionList(int idUser, String option){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		/*
		 * Urutan request [page].[user].[validated].[filter] (0 semua, 1 klik, 2 impresi 3 harian)
		 * @(data: TemplateData, ads_transaction_list: Page[AdsTransaction], show:Integer, page:Integer, option=Integer, user:User, campaignList:List[Campaign])
		 */
		
		String[] options=option.split("-");
		int page;
		int show;
		int showOption;
		int idCampaign;
		Date from;
		Date to;
		//Date default value
		Calendar calendar=Calendar.getInstance();
		to=calendar.getTime();
		calendar.set(Calendar.DATE, -9000);
		from=calendar.getTime();
		
		Campaign campaign=null;
		try {
			DynamicForm form=Form.form().bindFromRequest();
			try {
				show=Integer.parseInt(form.get("view"));
			} catch (Exception e) {
				e.printStackTrace();
				show = Integer.parseInt(options[1]);
			}
			try {
				idCampaign = Integer.parseInt(form.get("campaign"));
			} catch (Exception e1) {
				idCampaign=0;
				e1.printStackTrace();
			}
			if(idCampaign!=0){
				campaign=Campaign.find.byId(idCampaign);				
			}			
			
			try {
				from = format.parse(form.get("from"));
				to = format.parse(form.get("to"));
			} catch (Exception e) {
				Logger.debug("Calendar error");
			}
			if(options.length!=3){
				Logger.debug("Ukuran Option "+options.length+" "+option);
				throw  new Exception();
			}
			page = Integer.parseInt(options[0]);
			showOption= Integer.parseInt(options[2]);
		} catch (Exception e) {
			page=0;
			show=50;
			showOption=0;
			e.getMessage();
		}
		User user=null;
		Page<AdsTransaction> result=null;
		Logger.debug("Parsing Page "+page+" "+show+" "+idUser+" ");
		//@(data: TemplateData, ads_transaction_list: Page[AdsTransaction], show:Integer, page:Integer, option=Integer, user:User, campaignList:List[Campaign])
		CampaignList campaignList;
		if(auth.getUserRole(session()).getName().equals("advertiser")){
			User userLogin=auth.getUserLogin(session());
			campaignList=new CampaignList(userLogin);
			result=fetcher.getAdsTransaction(userLogin, show, page, showOption, campaign, from, to);
			return ok(ads_transaction.render(data,result,show,page,showOption,userLogin, campaignList));	    			
		}else{
			user=User.find.byId(idUser);
			campaignList=new CampaignList(user);
			
			result=fetcher.getAdsTransaction(user, show, page, showOption, campaign, from, to);
		}
		return ok(ads_transaction.render(data,result,show, page, showOption, user,campaignList));	    
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showDepositoList(int idUser, String option){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		/*
		 * Urutan request [page].[user].[validated].
		 */
		String[] options=option.split("-");
		int page;
		int show;
		try {
			DynamicForm form=Form.form().bindFromRequest();
			try {
				show=Integer.parseInt(form.get("view"));
			} catch (Exception e) {
				e.printStackTrace();
				show = Integer.parseInt(options[1]);
			}
			if(options.length!=2){
				Logger.debug("Ukuran Option "+options.length+" "+option);
				throw  new Exception();
			}
			page = Integer.parseInt(options[0]);
		} catch (Exception e) {
			page=0;
			show=50;
			e.getMessage();
		}
		User user=null;
		Page<Deposito> result=null;
		Logger.debug("Parsing Page "+page+" "+show+" "+idUser+" ");
		//@(data: TemplateData, ads_transaction_list: Page[AdsTransaction], show:Integer, page:Integer,user:User)

		if(auth.getUserRole(session()).getName().equals("advertiser")){
			User userLogin=auth.getUserLogin(session());
			result=fetcher.getDeposito(userLogin, show, page);
			return ok(deposito_list.render(data,result,show,page,userLogin));	    			
		}else{
			user=User.find.byId(idUser);
			result=fetcher.getDeposito(user, show, page);
		}
		return ok(deposito_list.render(data,result,show,page,user));	    
		
	}	
	@Restrict({@Group("advertiser")})
	@With(DataFiller.class)
	public static Result newConfirmation(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		User user=auth.getUserLogin(session()); 
		String content=setting.getString(KeyEnum.PAYMENT_INSTRUCTION);
		List<UserContact> contact=deposit.getUserBankAccount(user);
		
		return ok(confirm_payment.render(data, user, transferForm, content, contact));
	}
	@Restrict({@Group("advertiser")})
	@With(DataFiller.class)
	public static Result saveConfirmation(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<TransferForm> filledForm=Form.form(TransferForm.class).bindFromRequest();
		User user=auth.getUserLogin(session()); 
		String content=setting.getString(KeyEnum.PAYMENT_INSTRUCTION);
		List<UserContact> contact=deposit.getUserBankAccount(user);

		if(filledForm.hasErrors()){
			return ok(confirm_payment.render(data, user, filledForm, content, contact));
		}else{
			TransferConfirmation confirm=deposit.saveConfirmation(filledForm, user);
			if(confirm!=null){
				flash("success", "Konfirmasi telah diterima, silahkan menunggu untuk divalidasi");
				return ok(confirm_payment.render(data, user, transferForm, content, contact));				
			}else{
				flash("error", "Konfirmasi pembayaran gagal disimpan");
				return ok(confirm_payment.render(data, user, filledForm, content, contact));							
			}
		}
	}
	@Restrict({@Group("advertiser"),@Group("manager")})
	@With(DataFiller.class)	
	public static Result deleteConfirmation(int idConfirmation){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		try {
			boolean sukses=deposit.deleteTransfer(TransferConfirmation.find.byId(idConfirmation));
			if(!sukses){
				flash("error","Data gagal dihapus");
			}else{
				flash("success","Data berhasil dihapus");
				return redirect(controllers.backend.routes.FinanceController.listConfirmation("all"));
			}
		} catch (Exception e) {
			flash("error","Terjadi kesalahan pada proses penghapusan");
			e.printStackTrace();
		}
		return redirect(controllers.backend.routes.FinanceController.showSingleConfirmation(idConfirmation));		
	}
	@Restrict({@Group("manager")})
	@With(DataFiller.class)	
	public static Result newDeposito(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DepositoData depositoData=new DepositoData();
		return ok(new_deposito.render(data,depositoForm,depositoData));
	}
	@Restrict({@Group("manager")})
	@With(DataFiller.class)	
	public static Result saveDeposito(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<DepositoForm> filledForm =Form.form(DepositoForm.class).bindFromRequest();
		DepositoData depositoData=new DepositoData();
		if(filledForm.hasErrors()){
			return ok(new_deposito.render(data,filledForm, depositoData));
		}else{
			Deposito deposito=deposit.newDeposito(filledForm);
			if(deposito!=null){
				flash("success", "Penyimpanan deposito untuk "+deposito.getUser().getFront_name()+" berhasil dilakukan");
				return redirect(controllers.backend.routes.FinanceController.newDeposito());
			}else{
				flash("error","Penyimpanan deposito gagal dilakukan");
				return ok(new_deposito.render(data,filledForm, depositoData));
			}
		}
	}
		
	@SubjectPresent
	@With(DataFiller.class)	
	public static Result showSingleConfirmation(int idConfirmation){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		TransferConfirmation confirm=TransferConfirmation.find.byId(idConfirmation);
		return ok(show_single_confirmation.render(data, confirm));
	}
	@SubjectPresent
	@With(DataFiller.class)	
	public static Result listConfirmation(String option){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		/*
		 * Urutan request [page].[user].[validated].
		 */
		String[] options=option.split("-");
		int page;
		int validated;
		int user_id;
		try {
			if(options.length!=3){
				Logger.debug("Ukuran Option "+options.length+" "+option);
				throw  new Exception();
			}
			page = Integer.parseInt(options[0]);
			validated = Integer.parseInt(options[2]);
			user_id = Integer.parseInt(options[1]);
		} catch (Exception e) {
			page=0;
			validated=2;
			user_id=0;
			e.getMessage();
		}
		Page<TransferConfirmation> result=null;
		Logger.debug("Parsing Page "+page+" "+validated+" "+user_id+" ");

		if(auth.getUserRole(session()).getName().equals("advertiser")){
			User userLogin=auth.getUserLogin(session());
			result=deposit.getTransferConfirmationByUser(page, 30, userLogin, validated);
		}else{
			User user=User.find.byId(user_id);
			if(user==null){
				result=deposit.getTransferConfirmation(page, 30, validated);
			}else{
				result=deposit.getTransferConfirmationByUser(page, 30, user, validated);
			}
		}
		DepositoData depositoData=new DepositoData();
		return ok(confirmation_list.render(data,result,depositoData.getUser(),validated,page,user_id));
	}

	@Restrict({@Group("manager")})
	@With(DataFiller.class)	
	public static Result validateConfirmation(int idConfirmation){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		try {
			DynamicForm filledForm=Form.form().bindFromRequest();
			String message=filledForm.get("message");
			boolean valid=filledForm.get("valid") != null;
			TransferConfirmation confirm=TransferConfirmation.find.byId(idConfirmation);
			Logger.debug(message+" "+valid);
			boolean success=deposit.validate(confirm, auth.getUserLogin(session()), message, valid);{
				if(success){
					flash("success","Validasi disimpan");
				}else{
					flash("error","Kesalahan dalam validasi konfirmasi");
				}
			}
		} catch (Exception e) {
			flash("error","Kesalahan dalam validasi konfirmasi");			
			e.printStackTrace();
		}
		return redirect(controllers.backend.routes.FinanceController.showSingleConfirmation(idConfirmation));			

	}
	

	

}


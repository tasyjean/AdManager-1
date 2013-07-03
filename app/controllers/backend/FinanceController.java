package controllers.backend;
/*
 * @Author Xenovon
 * Kelas FinanceController digunakan untuk menangani request berkaitan dengan 
 * manajemen keuangan
 */
import java.util.List;

import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.Deposito;
import models.data.TransferConfirmation;
import models.data.User;
import models.data.UserContact;
import models.dataWrapper.TemplateData;
import models.dataWrapper.finance.DepositoData;
import models.form.backendForm.financeForm.DepositoForm;
import models.form.backendForm.financeForm.TransferForm;
import models.service.Authenticator;
import models.service.finance.DepositoOperator;
import models.service.notification.NotificationCenter;
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
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(finance_index.render(data));
	}
	@Restrict({@Group("advertiser")})
	@With(DataFiller.class)
	public static Result newConfirmation(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
//		@(data: TemplateData, user:User,transfer_form:Form[TransferForm], content:String, rekening:List[UserContact]) 
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
	@Restrict({@Group("advertiser")})
	@With(DataFiller.class)	
	public static Result deleteConfirmation(int idConfirmation){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok();
	}
	@Restrict({@Group("manager")})
	@With(DataFiller.class)	
	public static Result deleteDeposito(int idConfirmation){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok();
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
		
	@Restrict({@Group("advertiser"), @Group("manager")})
	@With(DataFiller.class)	
	public static Result showSingleConfirmation(int idConfirmation){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok();
	}
	@SubjectPresent
	@With(DataFiller.class)	
	public static Result listConfirmation(String option){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		/*
		 * Urutan request [page].[user].[validated].
		 */
		String[] options=option.split(".");
		int page;
		int validated;
		int user_id;
		try {
			if(options.length!=3){
				throw  new Exception();
			}
			page = Integer.parseInt(options[0]);
			validated = Integer.parseInt(options[2]);
			user_id = Integer.parseInt(options[1]);
		} catch (Exception e) {
			page=1;
			validated=2;
			user_id=0;
			e.printStackTrace();
		}
		Page<TransferConfirmation> result=null;
		
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
		
		return ok();
	}
	

	

}


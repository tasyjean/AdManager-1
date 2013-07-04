package controllers.backend;

import models.data.Notification;
import models.data.User;
import models.dataWrapper.TemplateData;
import models.service.Authenticator;
import models.service.notification.NotificationCenter;

import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.profile_view.notification;

public class NotificationController extends CompressController {

/*
 * 	 *	notif.active_ads=Campaign |nama_campaign|  diaktifkan   (nama campaign, id campaign) untuk pemilik campaign
		notif.non_active_ads=Campaign |nama_campaign|  di non aktifkan (nama campaign, id campaign) untuk pemilik campaign
		notif.empty_deposito=Saldo tidak mencukupi, silahkan isi kembali ("", "") atau kosong, untuk pemilik saldo
		notif.please_validate=Ada pembayaran baru dari |nama_user| yang perlu divalidasi (nama_user, id validasi) untuk management
		notif.validated=Pembayaran senilai |jumlah| telah divalidasi (jumlah, id pembayaran) untuk pemilik pembayaran
		notif.see_report= Silahkan lihat laporan iklan  (jumlah, id pembayaran) untuk advertiser
		notif.new_campaign=|nama_user| telah membuat campaign baru  (nama user, id user) untuk administrator dan manager
		notif.new_user=Pengguna baru bernama |nama_user| telah terdaftar  (nama user, id user) untuk administrator
		notif.should_active=Campaign |nama_campaign| sudah memasuki masa mulai, namun belum diaktifkan, silahkan aktifkan atau mundurkan waktu mulai  (nama_campaign, id_campaign)

 */
	static NotificationCenter notif=new NotificationCenter();
	static Authenticator auth=new Authenticator();
	
	public static Result getLink(int idNotif){
		Call call;
		try {
			Notification notification=Notification.find.byId(idNotif);
			int parameter=Integer.parseInt(notification.getParam().split(":")[1]);
			
			call = null;
			switch (notification.getNotification_type()) {
				case ACTIVE_ADS:call=controllers.backend.routes.CampaignController.showSingleCampaign(parameter);break;
				case EMPTY_DEPOSITO:call=controllers.backend.routes.ProfileController.showProfile();break;
				case NEW_CAMPAIGN:call=controllers.backend.routes.CampaignController.showSingleCampaign(parameter);break;
				case NEW_USER:call=controllers.backend.routes.UserController.showSingleUser(parameter);break;
				case NON_ACTIVE_ADS:call=controllers.backend.routes.CampaignController.showSingleCampaign(parameter);break;
				case PLEASE_VALIDATE:call=controllers.backend.routes.FinanceController.showSingleConfirmation(parameter);break;
				case VALIDATED :call=controllers.backend.routes.FinanceController.showDepositoList(parameter, "all");break;
				case SEE_REPORT:call=controllers.backend.routes.ReportController.index();break;
				case SHOULD_ACTIVE:call=controllers.backend.routes.CampaignController.showSingleCampaign(parameter);break;
				case NEW_DEPOSITO:call=controllers.backend.routes.FinanceController.showDepositoList(parameter, "all");break;
				default:
					break;
				}
			notif.setRead(idNotif);
		} catch (Exception e) {
			call=controllers.backend.routes.ProfileController.showProfile();
			e.printStackTrace();
		}
		return redirect(call);
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result notifPage(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		User user=auth.getUserLogin(session());
		page=page-1;
		Page<Notification>  notifications=notif.getListNotif(50, page, user);
		notifications.getPageIndex();
		notifications.getTotalRowCount();
		notifications.getTotalPageCount();
		return ok(notification.render(data, notifications));
	}
}

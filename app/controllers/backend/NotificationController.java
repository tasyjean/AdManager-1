package controllers.backend;

import models.data.Notification;
import models.data.User;
import models.dataWrapper.TemplateData;
import models.service.Authenticator;
import models.service.notification.NotificationCenter;

import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;

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
		Notification notification=Notification.find.byId(idNotif);
		switch (notification.getNotification_type()) {
		case ACTIVE_ADS:
			break;

		default:
			break;
		}
		return ok();
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result notifPage(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		User user=auth.getUserLogin(session());
		Page<Notification>  notifications=notif.getListNotif(50, page, user);
		
		return ok();
	}
}

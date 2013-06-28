package models.service.ads_delivery;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import play.mvc.Content;
import models.custom_helper.DateBinder;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Zone;
import models.data.enumeration.CampaignTypeEnum;
/*
 * Untuk menyeleksi iklan
 */
import models.service.campaign.BannerProcessor;
import models.service.campaign.CampaignProcessor;
import models.service.notification.NotifItem;
import models.service.notification.NotificationCenter;
import models.service.notification.NotificationType;
public class AdSelector {

	/*
	 * May be this is most fragile, long and difficult algoritm
	 * 
	 * Step
	 * -> Tentukan tipe zona
	 * -> Cari banner yang terhubung dengan zona, dan kondisi aktif
	 *    Jika ngga ada, tampilkan default
	 * -> JIka ada   	
	 * 	  -> cek apakah ada banner yang campaignnya eklusif dimana
	 * 		 -> Aktif
	 * 		 -> Masih berlaku  -> jika ngga, nonaktifkan, dan kirim notifikasi
	 * 		 -> Pemiliknya masih punya cukup dana, kalo ga cukup, nonaktifkan iklan, dan kirim notifikasi
	 * 			-> transaksi dihitung tiap tampilan pertama dalam 1 hari
	 * 			-> Artinya jika dalam sehari tidak ada impresi, maka tidak ada transaksi
	 * 		 -> jika memenuhi syarat, iklan tampil
	 * 		 -> jika ngga, populasikan banner non eklusif
 	 * 	  -> populasikan banner non eklusif
 	 * 	     -> pastikan semua memenuhi syarat
 	 * 			-> aktif
 	 * 			-> slot impressi/click masih ada (jika ngga, nonaktifkan, kirim notif)
 	 * 			-> pemiliknya masih punya cukup dana  (jika ngga, nonaktifkan, kirim notif)
 	 * 				-> untuk klik, transaksi dihitung tiap klik
 	 * 				-> untuk impressi, transaksi dihitung tiap kelipatan 1000
 	 * 			-> jika memenuhi syarat, maka tampilkan
 	 * 		-> jika yang memenuhi syarat lebih dari 1
 	 * 		   -> gunakan probabilitas campaign mana yang akan dipilih, melalui harga
 	 * 		   -> Jika yang terpilih memiliki 2 kandidat banner, maka banner yang ditampilkan yang bobotnya lebih tinggi
 	 * 		   -> jika bobotnya sama, pake random
 	 * 
	 */
	/*
	 * return value : idBanner
	 */
	BannerProcessor    bannerProc;
	CampaignProcessor  campaignProc;
	DateBinder 		   binder;
	NotificationCenter notif;
	
	public AdSelector(BannerProcessor bannerProc,
					  CampaignProcessor campaignProc,
					  DateBinder binder,
					  NotificationCenter notif){
		this.bannerProc=bannerProc;
		this.campaignProc=campaignProc;
		this.binder=binder;
		this.notif=notif;
	}
	public int get(int zone_id){

		//langkah 1, populasikan placement aktif
		List<BannerPlacement> banners=BannerPlacement.find.where().
													 eq("zone", Zone.find.byId(zone_id)).
													 eq("isActive", true).findList();
		//jika ngga ada, return 0 
		if(banners.size()==0){
			return 0;
		}		
		//langkah 2 populasikan banner yang campaignnya ekslusif
		int selected=isContainExclusive(banners);

		if(selected==0){
			
		}else{
			return selected;
		}
		
		
		final int zone=zone_id;
		Content content=new Content() {
			@Override
			public String contentType() {
				return "html/text";
			}
			@Override
			public String body() {
				String text="<h1>Test Konten</h1>+" +
						"<p>Ini adalah tes dari zona "+zone+" <p>";
				return text;
			}
		};
		return 0;
	}
	//mengembalikan id banner, dari campaign eklusif yang valid
	private int isContainExclusive(List<BannerPlacement> inputs){
		for(BannerPlacement banner:inputs){
			
		}
		return 0;
	}
	/*
	 * 	 * 	-> Aktif
	 * 		 -> Masih berlaku  -> jika ngga, nonaktifkan, dan kirim notifikasi
	 * 		 -> Pemiliknya masih punya cukup dana, kalo ga cukup, nonaktifkan iklan, dan kirim notifikasi
	 * 			-> transaksi dihitung tiap tampilan pertama dalam 1 hari
	 * 			-> Artinya jika dalam sehari tidak ada impresi, maka tidak ada transaksi

	 */
	//cek apakah ada yang ekslusig atau
	private boolean checkValidExclusive(BannerPlacement inputs){
		Campaign campaign=inputs.getBanner().getCampaign();
		
		if(campaign.getCampaign_type()==CampaignTypeEnum.EXCLUSIVE){
			if(!campaign.isDeleted()){
				if(campaign.isActivated()){
					
				}
			}
			
		return false;
		}
	}
	//cek kevalidan campaign ekslusif (siap tampil nggak)
	//ntar proses ini mesti dipisah
	private boolean checkExclusiveCampaign(Campaign campaign){
		Date from=campaign.getStart_date();
		Date to=campaign.getEnd_date();
		
		if(binder.todayIsBetween(from, to)){
			if(!campaign.isActivated()){  //jika udah memasuki masa tampil, tapi belum tampil, kirim notif
				sendActiveNotification(campaign); // kirim notifikasi kalo campaign mesti diaktifin
				return false;
			}else{ //sudah memasukin masa aktif dan telah diaktifkan
				if(campaign.getId_user().getCurrent_balance()<campaign.getBid_price()){ //ga cukup duit
					campaign.setActivated(false);
					campaign.update();
					sendNonActiveNotification(campaign);
					return false;
				}
				return true; //memenuhi syarat
			}
		}else{ //jika diluar rentang campaign 
			if(binder.isBeforeToday(to)){ //campaign diluar end date
				if(campaign.isActivated()){ //kalo masih aktif, nonaktifin
					campaign.setActivated(false);
					campaign.update();
					sendNonActiveNotification(campaign);
				}
			}
			return false; //campaign diluar rentang, artinya ga valid
		}
	}
	private void sendActiveNotification(Campaign campaign){
		NotifItem item=new NotifItem();
		item.setParam(new String[]{campaign.getCampaignName(),
						  Integer.toString(campaign.getId_campaign())});
		item.setType(NotificationType.SHOULD_ACTIVE);
		item.setUser(campaign.getId_user());
		if(!notif.isDuplicate(item)){
			notif.pushNew(item);
			notif.pushNew("ADMINISTRATOR",item);			
		}
	}
	private void sendNonActiveNotification(Campaign campaign){
		NotifItem item=new NotifItem();
		item.setParam(new String[]{campaign.getCampaignName(),
						  Integer.toString(campaign.getId_campaign())});
		item.setType(NotificationType.NON_ACTIVE_ADS);
		item.setUser(campaign.getId_user());
		notif.pushNew(item);
		notif.pushNew("ADMINISTRATOR",item);			
	}
	private void sendEmptyDeposito(Campaign campaign){
		NotifItem item=new NotifItem();
		item.setParam(new String[]{campaign.getCampaignName(),
						  Integer.toString(campaign.getId_campaign())});
		item.setType(NotificationType.EMPTY_DEPOSITO);
		item.setUser(campaign.getId_user());
		notif.pushNew(item);
		notif.pushNew("ADMINISTRATOR",item);			
	}	
	private int selectNonExclusive(List<BannerPlacement> inputs){
		return 0;
	}
}

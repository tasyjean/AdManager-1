package models.service.ads_delivery;

import java.util.List;

import play.mvc.Content;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Zone;
/*
 * Untuk menyeleksi iklan
 */
import models.service.campaign.BannerProcessor;
import models.service.campaign.CampaignProcessor;
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
	BannerProcessor bannerProc;
	CampaignProcessor campaignProc;

	public AdSelector(BannerProcessor bannerProc,CampaignProcessor campaignProc){
		this.bannerProc=bannerProc;
		this.campaignProc=campaignProc;
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
	
	private int isContainExclusive(List<BannerPlacement> inputs){
		return 0;
	}
	private int selectNonExclusive(List<BannerPlacement> inputs){
		return 0;
	}
}

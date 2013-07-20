package models.service.ads_delivery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;


import play.Logger;
import play.mvc.Content;
import models.custom_helper.DateBinder;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;
import models.data.User;
import models.data.Zone;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;
import models.data.enumeration.ZoneTypeEnum;
import models.service.ads_delivery.tf_idf.BannerRelevancy;
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
	Random random;
	BannerRelevancy relevancy;
	
	public AdSelector(BannerProcessor bannerProc,
					  CampaignProcessor campaignProc,
					  DateBinder binder,
					  NotificationCenter notif, BannerRelevancy relevancy){
		this.bannerProc=bannerProc;
		this.campaignProc=campaignProc;
		this.binder=binder;
		this.notif=notif;
		this.relevancy=relevancy;
		this.random=new Random();
	}
	//return value[]= id banner, [0]=0 jika zona kosong
	public List<BannerPlacement> get(Zone zone, String url){

		//langkah 1, populasikan placement aktif
		List<BannerPlacement> banners=BannerPlacement.find.where().
													 eq("zone", zone).
													 eq("isActive", true).findList();
//		Logger.debug("banner placement  size "+banners.size());
		List<BannerPlacement> result=null;
		//jika ngga ada, return 0 
		if(banners.size()==0){
			return result;
		}		
		//langkah 2 populasikan banner yang campaignnya ekslusif
		int selected=isContainExclusive(banners);
//		Logger.debug("cek ekslusif "+selected);			

		if(selected>0){
			return populateFromInt(banners, new int[]{selected});
		}
		//langkah 3 populasikan banner yang campaign non eklusif
		int[] textBanner ={0};
		if(zone.getZone_type()==ZoneTypeEnum.TEXT){
			if(zone.getBanner_size().getName().equals("LeaderBoard")){
				textBanner=selectBannerNonExclusive(banners, 2, url);
			}
			if(zone.getBanner_size().getName().equals("Medium Rectangle")){
				textBanner=selectBannerNonExclusive(banners, 3, url);
			}
			return populateFromInt(banners, textBanner);
		}
		
		int[] selectedContract=selectBannerNonExclusive(banners,1, url);
//		Logger.debug("cek kontrak "+selectedContract);			
		
		return populateFromInt(banners, selectedContract);
	}
	private List<BannerPlacement> populateFromInt(List<BannerPlacement>  banners, int[] input){
		List<BannerPlacement> result = new ArrayList<BannerPlacement>();
		int x=0;
		for(int i:input){
			for(BannerPlacement place:banners){
				if(input[x]==place.getBanner().getId_banner()){
					result.add(place);
				}
			}
			x++;
		}
//		Logger.debug("Result size : "+result.size());
		return result;
	}
	//mempopulasikan banner non ekslusif
	private int[] selectBannerNonExclusive(List<BannerPlacement> inputs, int count, String url){
		List<Banner> result=new ArrayList<Banner>();
		for(BannerPlacement bannerPlace:inputs){
//			Logger.debug("selectBannerNonExclusive  "+bannerPlace.getId_banner_placement());			
			if(checkValidNonExclusive(bannerPlace)){
				result.add(bannerPlace.getBanner());
			}
		}
		if(result.size()==0){ //kosong, ngga ada yang valid
			return new int[]{0};
		}else if(result.size()==1){ //cuman ada 1
			return new int[]{result.get(0).getId_banner()};
		}else{
			//Revisi TA
			//Jika banner placement yang ada lebih dari 5, maka dipilih yang isinya relevan doang
			int selectBanner=5;
			if(url!=null){
				if(result.size()>selectBanner){
					relevancy.filterRelevantBanner_banner(result, url, selectBanner);
				}
			}
			if(count>1){
				return selectMultipleBanner(result, count);
			}else{
				return new int[]{selectBannerBasedBidPrice(result)};
			}
		}
	}
	//untuk yang teks, mendukung multiple banner
	private int[] selectMultipleBanner(List<Banner> banners,int count){
		int iterate=count*4;
		int[] selected=new int[iterate];
		for(int i=0;i<iterate;i++){
			selected[i]=selectBannerBasedBidPrice(banners);
		}
		return getHighestFrequency(selected, count);
	}
	private int[] getHighestFrequency(int[] input, int count){
		Arrays.sort(input);
		ArrayList<int[]> list=new ArrayList<int[]>();
		ArrayList<Integer>  list2=new ArrayList<Integer>();
		int i=0;
		for(int ax:input){
			if(!list2.contains(ax)){
				list2.add(ax);
				list.add(new int[]{ax,1});
				i++;
			}else{
				int[] x=list.get(i-1);
				x[1]=x[1]+1;
				list.set(i-1, x);
			}
		}
		Collections.sort(list, new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				return o2[1]-o1[1];
			}
			
		});
		int[] returnValue=new int[count];
		int is=0;
		for(int[] result:list){
			if(is>=count) break;
			returnValue[is]=result[0];
			System.out.println(result[0]+" "+result[1]);
			is++;
		}
		return returnValue;
	}
	//memilih banner berdasarkan harga campaign......
	private int selectBannerBasedBidPrice(List<Banner> banners){
		//pupulasikan campaign
		List<Campaign> campaigns=new ArrayList<Campaign>();
		for(Banner banner:banners){
			if(!campaigns.contains(banner.getCampaign()))
				campaigns.add(banner.getCampaign());
		}
		//pilih campaign secara random dengan bobot
		int selectedCampaign=selectCampaignBasedBidPrice(campaigns);
		
		//populasikan  banner milik campaign
		List<Banner> selectedBanner=new ArrayList<Banner>();
		
		for(Banner banner:banners){
			if(banner.getCampaign().getId_campaign()==selectedCampaign)
				selectedBanner.add(banner);
		}
		//pilhan return value
		if(selectedBanner.size()==0){
//			Logger.debug("Ada bug dalam pemilihan banner");
			return 0;
		}else if(selectedBanner.size()==1){
			return selectedBanner.get(0).getId_banner();
		}else{
			return selectBannerBasedWeight(selectedBanner);
		}
	}
	private int selectCampaignBasedBidPrice(List<Campaign> campaigns){
		Campaign result = null;
		double bestValue = Double.MAX_VALUE;
		for (Campaign element : campaigns) {
		    double  value =  -Math.log(random.nextDouble()) / element.getBid_price();
		    if (value < bestValue) {
		        bestValue = value;
		        result = element;
		    }
		}
		return result.getId_campaign(); //kembalikan id campaign yang kepilih		
	}
/*	 * 	  -> populasikan banner non eklusif
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
	private boolean checkValidNonExclusive(BannerPlacement bannerPlace){
		Campaign campaign=bannerPlace.getBanner().getCampaign();
		Banner banner = bannerPlace.getBanner();
		//validasi banner
		if(banner.isDeleted()){
			return false;
		}
		if(!banner.isActive()){
			return false;
		}
		
		//validasi campaign
		if(campaign.getCampaign_type()==CampaignTypeEnum.CONTRACT){
			if(!campaign.isDeleted()){
				if(campaign.isActivated()){
					return checkContractCampaign(campaign);					
				}
			}
		}
		return false;
	}
	
	private boolean checkContractCampaign(Campaign campaign){
		User user=campaign.getId_user();
//		Logger.debug("Check contract campaign "+user.getCurrent_balance());			
		
		if(user.getCurrent_balance()<campaign.getBid_price()){
			//nonaktifkan
//			Logger.debug("non ekslusif dana ngga cukup "+user.getCurrent_balance());			

			nonActivateCampaign(campaign);
			sendEmptyDeposito(campaign);
			return false;
		}
		if(campaign.getPricing_model()==PricingModelEnum.CPA){
			if(campaign.getClickLeft()<1){
				nonActivateCampaign(campaign);
				return false;
			}
		}if(campaign.getPricing_model()==PricingModelEnum.CPM){
			if(campaign.getImpressionLeft()<1){
				nonActivateCampaign(campaign);
				return false;
			}
		}
		return true;
	}
	
	//mengembalikan id banner, dari campaign eklusif yang valid
	//aturan validitas, lihat diatas
	private int isContainExclusive(List<BannerPlacement> inputs){
		List<Banner> result=new ArrayList<Banner>();
		for(BannerPlacement bannerPlace:inputs){
			if(checkValidExclusive(bannerPlace)){
				result.add(bannerPlace.getBanner());
			}
		}
		if(result.size()==0){ //kosong, ngga ada yang valid
			return 0;
		}else if(result.size()==1){ //cuman ada 1
			return result.get(0).getId_banner();
		}else{
			return selectBannerBasedWeight(result);
		}
	}
	//menangani >1 banner dalam iklan eklusif dalam campaign yang sama
	private int selectBannerBasedWeight(List<Banner> banner){
		int sum=0;
		for(Banner item:banner){
			sum=sum+item.getWeight();
		}
		if(sum==0){
			sum=banner.size()+1;
			for(Banner item:banner){
				item.setWeight(1);
			}
		}
		Banner[] bannerArray=new Banner[sum];
		int i=0;
		for(Banner item:banner){
			for(int x=0;x<item.getWeight();x++){
				bannerArray[i]=item;
				Logger.debug(item.getName()+" "+i);
				i++;
			}
		}
		return bannerArray[new Random().nextInt(sum-1)].getId_banner(); //kembalikan id banner yang kepilih
	}
	/*
	 * 	 * 	-> Aktif
	 * 		-> Banner aktif
	 * 		 -> Masih berlaku  -> jika ngga, nonaktifkan, dan kirim notifikasi
	 * 		 -> Pemiliknya masih punya cukup dana, kalo ga cukup, nonaktifkan iklan, dan kirim notifikasi
	 * 			-> transaksi dihitung tiap tampilan pertama dalam 1 hari
	 * 			-> Artinya jika dalam sehari tidak ada impresi, maka tidak ada transaksi

	 */
	
	//cek apakah ada yang ekslusif atau ngga
 	private boolean checkValidExclusive(BannerPlacement inputs){
		Campaign campaign=inputs.getBanner().getCampaign();
		Banner banner=inputs.getBanner();
		//validasi banner
		if(banner.isDeleted()){
			return false;
		}
		if(!banner.isActive()){
			return false;
		}

		//validasi campaign			
		if(campaign.getCampaign_type()==CampaignTypeEnum.EXCLUSIVE){
			if(!campaign.isDeleted()){
				return checkExclusiveCampaign(campaign);
			}
		}
		return false;
	}
	//cek kevalidan campaign ekslusif (siap tampil nggak)
	//ntar proses ini mesti dipisah
	private boolean checkExclusiveCampaign(Campaign campaign){
		Date from=campaign.getStart_date();
		Date to=campaign.getEnd_date();
		
		if(binder.todayIsBetween(from, to)){
			if(!campaign.isActivated()){  //jika udah memasuki masa tampil, tapi belum tampil, dan uang cukup, kirim notif
				if(isShouldActive(campaign)){  
					sendActiveNotification(campaign);// kirim notifikasi kalo campaign mesti diaktifin
				}
					return false;
			}else{ //sudah memasukin masa aktif dan telah diaktifkan
				return (isEnoughMoney(campaign))? true:false;  //jika uang cukup, maka oke
			}
		}else{ //jika diluar rentang campaign 
			if(binder.isBeforeToday(to)){ //campaign diluar end date
				if(campaign.isActivated()){ //kalo masih aktif, nonaktifin
					nonActivateCampaign(campaign);
				}
			}
			return false; //campaign diluar rentang, artinya ga valid
		}
	}
	private void  nonActivateCampaign(Campaign campaign){
		campaign.setActivated(false);
		campaign.update();
		sendNonActiveNotification(campaign);
	}
	
	//duitnya cukup ngga, kalo ngga, kirim notifikasi dan dinonaktifin
	private boolean isEnoughMoney(Campaign campaign){
		if(campaign.getId_user().getCurrent_balance()<campaign.getBid_price()){ //ga cukup duit
			campaign.setActivated(false);
			campaign.update();
			sendEmptyDeposito(campaign);
			sendNonActiveNotification(campaign);			
			return false;
		}
		return true; //memenuhi syarat
		
	}
	//apakah perlu aktif atau ngga dengan membandingkan anu
	private boolean isShouldActive(Campaign campaign){
		if(campaign.getId_user().getCurrent_balance()<campaign.getBid_price()){ //ga cukup duit			
			return false;
		}
		return true; //memenuhi syarat
		
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
//		notif.pushNew("ADMINISTRATOR",item);			
	}	

}

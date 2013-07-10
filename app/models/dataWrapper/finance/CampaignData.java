package models.dataWrapper.finance;

import java.util.List;

import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.custom_helper.*;

public class CampaignData {

	private Campaign campaign;
	private int totalPrice;
	private int totalTransaction;
	
	public CampaignData(Campaign campaign) {
		this.campaign=campaign;
		List<Banner> banners=Banner.find.where().eq("campaign", campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banners).findList();
		List<AdsTransaction> transactions=AdsTransaction.find.where().in("bannerPlacement", placement).findList();
		
		totalTransaction= transactions.size();
		totalPrice=0;
		for(AdsTransaction transaction:transactions){
			totalPrice=totalPrice+transaction.getAmount();
		}
	}
	public Campaign getCampaign(){
		return campaign;
	}
	public String getTotalTransaction(){
		return Angka.toAngka(totalTransaction)+" Kali Transaksi";
	}
	public String getTotalPrice(){
		return Angka.toRupiah(totalPrice);
	}
}

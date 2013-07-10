package models.dataWrapper.report;

import java.util.List;

import models.custom_helper.Angka;
import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerAction;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;
import models.data.User;
import models.data.enumeration.ActionTypeEnum;

public class UserList {

	private User user;
	private int totalAdsTransaction;
	private int totalAdsTransactionFund;
	private int totalCampaign;
	private int totalBanner;
	private int totalImpression;
	private int totalClick;
	
	public UserList(User user) {
		this.user=user;
		this.setTotalAdsTransaction();
		this.setTotalAdsTransactionFund();
		this.setTotalBanner();
		this.setTotalCampaingn();
		this.setTotalClick();
		this.setTotalImpression();
	}
	public int getTotalAdsTransaction() {
		return totalAdsTransaction;
	}	
	public String getTotalAdsTransaction_angka() {
		return Angka.toAngka(getTotalAdsTransaction());
	}

	public void setTotalAdsTransaction() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banner=Banner.find.where().in("campaign", campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banner).findList();
		this.totalAdsTransaction =AdsTransaction.find.where().in("bannerPlacement", placement).findList().size();
		
	}
	public User getUser(){
		return user;
	}
	public int getTotalCampaign() {
		return totalCampaign;
	}
	public String getTotalCampaign_angka() {
		return Angka.toAngka(getTotalCampaign());
	}

	public void setTotalCampaingn() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		this.totalCampaign=campaign.size();
	}

	public int getTotalBanner() {
		return totalBanner;
	}

	public void setTotalBanner() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banners=Banner.find.where().in("campaign",campaign).findList();
		this.totalBanner = banners.size();
 	}

	public int getTotalImpression() {
		return totalImpression;
	}
	public String getTotalImpression_angka() {
		return Angka.toAngka(getTotalImpression());
	}
	public void setTotalImpression() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banners=Banner.find.where().in("campaign",campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banners).findList();
		List<Impression> impression=Impression.find.where().in("bannerPlacement", placement).findList();
		
		this.totalImpression=impression.size();
	}

	public int getTotalClick() {
		return totalClick;
	}	
	public String getTotalClick_angka() {
		return Angka.toAngka(getTotalClick());
	}

	public void setTotalClick() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banners=Banner.find.where().in("campaign",campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banners).findList();
		List<Impression> impression=Impression.find.where().in("bannerPlacement", placement).findList();
		List<BannerAction> actions=BannerAction.find.where().eq("action_type", ActionTypeEnum.CLICK.name()).in("impression", impression).findList();
		this.totalClick=actions.size();
	}
	public int getTotalAdsTransactionFund() {
		return totalAdsTransactionFund;
	}
	public String getTotalAdsTransactionFund_rupiah() {
		return Angka.toRupiah(totalAdsTransactionFund);
	}
	public void setTotalAdsTransactionFund() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banner=Banner.find.where().in("campaign", campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banner).findList();
		List<AdsTransaction> transaction=AdsTransaction.find.where().in("bannerPlacement", placement).findList();
		
		int total=0;
		for(AdsTransaction trans:transaction){
			total=total+trans.getAmount();
		}
		this.totalAdsTransactionFund =total; 
	}
}

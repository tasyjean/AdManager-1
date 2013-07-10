package models.dataWrapper.report;

import java.util.List;

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
	private int totalCampaingn;
	private int totalBanner;
	private int totalImpression;
	private int totalClick;
	
	public UserList(User user) {
		this.user=user;
	}
	public int getTotalAdsTransaction() {
		return totalAdsTransaction;
	}

	public void setTotalAdsTransaction() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banner=Banner.find.where().in("campaign", campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banner).findList();
		this.totalAdsTransaction =AdsTransaction.find.where().in("bannerPlacement", placement).findList().size();
		
	}

	public int getTotalCampaingn() {
		return totalCampaingn;
	}

	public void setTotalCampaingn() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		this.totalCampaingn=campaign.size();
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

	public void setTotalImpression(int totalImpression) {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banners=Banner.find.where().in("campaign",campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banners).findList();
		List<Impression> impression=Impression.find.where().in("bannerPlacement", placement).findList();
		
		this.totalImpression=impression.size();
	}

	public int getTotalClick() {
		return totalClick;
	}

	public void setTotalClick(int totalClick) {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banners=Banner.find.where().in("campaign",campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banners).findList();
		List<Impression> impression=Impression.find.where().in("bannerPlacement", placement).findList();
		List<BannerAction> actions=BannerAction.find.where().eq("action_type", ActionTypeEnum.CLICK.name()).in("impression", impression).findList();
		this.totalClick=actions.size();
	}
}

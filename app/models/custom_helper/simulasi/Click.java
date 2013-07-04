package models.custom_helper.simulasi;

import java.util.Date;

import play.Logger;

import com.avaje.ebean.Ebean;

import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerAction;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;
import models.data.User;
import models.data.enumeration.ActionTypeEnum;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;

public class Click {


	public BannerAction click(Impression impression, Date timestamp){
		Ebean.beginTransaction();
		try {
			BannerAction action=BannerAction.find.where().
											eq("impression", impression).
											eq("action_type", ActionTypeEnum.CLICK.name()).
											findUnique();
			if(action==null){
				action=newClick(impression, timestamp);
				Ebean.commitTransaction();
			}
			return action;
			
		} catch (Exception e) {
			Ebean.rollbackTransaction();
			e.printStackTrace();
			return null;
		}finally{
			Ebean.endTransaction();
		}
	}
	private BannerAction newClick(Impression impression, Date timestamp) throws Exception{
		Banner banner=impression.getAdsPlacement().getBanner();
		Campaign campaign=banner.getCampaign();
		
		BannerAction action=new BannerAction();
		action.setImpression(impression);
		action.setTimestamp(timestamp);
		action.setAction_type(ActionTypeEnum.CLICK);
		action.save();
		
		int currentBannerClickCount=banner.getClick_count();
		banner.setClick_count(currentBannerClickCount+1);
		banner.update();
		
		int currentCampaignClick=campaign.getCurrent_click();
		campaign.setCurrent_click(currentCampaignClick+1);
		campaign.update();
		//jika CPA, maka klik dihitung sebagai transaksi
		if(campaign.getPricing_model()==PricingModelEnum.CPA){
			newClickTransaction(campaign, banner, impression.getAdsPlacement(), timestamp);
		}
		return action;
	}
	private void newClickTransaction(Campaign campaign, Banner banner, BannerPlacement placement, Date timestamp) throws Exception{
		User user=campaign.getId_user();
		int current_balance=user.getCurrent_balance();
		int amount = campaign.getBid_price();
	
		AdsTransaction transaction=new AdsTransaction();
		transaction.setAmount(campaign.getBid_price());
		transaction.setCurrent_balance(current_balance-amount);
		transaction.setBannerPlacement(placement);
		transaction.setTimestamp(timestamp);
		transaction.setTransaction_type(PricingModelEnum.CPA);
		transaction.save();
		
		user.setCurrent_balance(current_balance-amount);
		user.update();
	}
}



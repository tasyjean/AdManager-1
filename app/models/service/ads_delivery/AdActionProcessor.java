package models.service.ads_delivery;

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

public class AdActionProcessor {


	public BannerAction click(Impression impression){
		Ebean.beginTransaction();
		try {
			BannerAction action=BannerAction.find.where().
											eq("impression", impression).
											eq("action_type", ActionTypeEnum.CLICK.name()).
											findUnique();
			if(action==null){
				action=newClick(impression);
				Logger.debug("New Click + "+action.getTimestamp());
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
	private BannerAction newClick(Impression impression) throws Exception{
		Banner banner=impression.getAdsPlacement().getBanner();
		Campaign campaign=banner.getCampaign();
		
		BannerAction action=new BannerAction();
		action.setImpression(impression);
		action.setTimestamp(new Date());
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
			newClickTransaction(campaign, banner, impression.getAdsPlacement());
		}
		return action;
	}
	private void newClickTransaction(Campaign campaign, Banner banner, BannerPlacement placement) throws Exception{
		User user=campaign.getId_user();
		int current_balance=user.getCurrent_balance();
		int amount = campaign.getBid_price();
	
		AdsTransaction transaction=new AdsTransaction();
		transaction.setAmount(campaign.getBid_price()+(int)(campaign.getBid_price()*placement.getZone().getPriceFactor()));
		transaction.setCurrent_balance(current_balance-amount);
		transaction.setBannerPlacement(placement);
		transaction.setTimestamp(new Date());
		transaction.setTransaction_type(PricingModelEnum.CPA);
		transaction.save();
		
		user.setCurrent_balance(current_balance-amount);
		user.update();
	}
}

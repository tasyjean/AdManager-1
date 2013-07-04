package models.custom_helper.simulasi;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.User;
import models.data.Zone;
import models.data.enumeration.PricingModelEnum;
import play.Logger;

public class FlatProcessor {

	public void process(BannerPlacement place, Date timestamp) throws Exception{
		Banner banner=place.getBanner();
		Campaign campaign=banner.getCampaign();
		Zone zone=place.getZone();
		List<Banner> banners=Banner.find.where().eq("campaign", campaign).findList();
		List<BannerPlacement> bannerPlace=BannerPlacement.find.where().in("banner", banners).findList();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timestamp);
		calendar.set(Calendar.HOUR, -12);//siklus dari jam 12:01 pagi sampe jam 12:01 pagi besoknya
		calendar.set(Calendar.MINUTE, +1);
		calendar.set(Calendar.SECOND, 0);				
		Date startOfToday=calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		Date endOfToday=calendar.getTime();
		//dihitung secara harian
		List<AdsTransaction> transaction=AdsTransaction.find.where().between("timestamp", startOfToday, endOfToday).in("bannerPlacement", bannerPlace)
															  .eq("transaction_type", PricingModelEnum.FLAT.name().toLowerCase()).findList();
		if(transaction.size()==0){
			startTransaction(place, timestamp);
		}
	}
	
	private void startTransaction(BannerPlacement placement, Date timestamp) throws Exception{
		Banner banner=placement.getBanner();
		Campaign campaign=banner.getCampaign();
		User user=campaign.getId_user();
		
		int current_balance=user.getCurrent_balance();
		int amount = campaign.getBid_price();
		int dailyPrice=campaign.countPrice()/campaign.campaignDuration();

		AdsTransaction transaction=new AdsTransaction();
		transaction.setAmount(dailyPrice);
		transaction.setCurrent_balance(current_balance-dailyPrice);
		transaction.setBannerPlacement(placement);
		transaction.setTimestamp(timestamp);
		transaction.setTransaction_type(PricingModelEnum.FLAT);
		transaction.save();
		
		user.setCurrent_balance(current_balance-dailyPrice);
		user.update();

	}

}

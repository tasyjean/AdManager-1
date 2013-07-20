package models.service.ads_delivery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import play.Logger;

import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.User;
import models.data.Zone;
import models.data.enumeration.PricingModelEnum;

//memutuskan apakah mesti transaksi
//dihitung secara harian

public class FlatProcessor {

	public void process(BannerPlacement place) throws Exception{
		Banner banner=place.getBanner();
		Zone zone=place.getZone();
		Campaign campaign=banner.getCampaign();
		List<Banner> banners=Banner.find.where().eq("campaign", campaign).findList();
		List<BannerPlacement> bannerPlace=BannerPlacement.find.where().in("banner", banners).findList();
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR, -12);//siklus dari jam 12:01 pagi sampe jam 12:01 pagi besoknya
		calendar.set(Calendar.MINUTE, +1);
		calendar.set(Calendar.SECOND, 0);				
		Date startOfToday=calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		Date endOfToday=calendar.getTime();
		//dihitung secara harian
		List<AdsTransaction> transaction=AdsTransaction.find.where().between("timestamp", startOfToday, endOfToday).in("bannerPlacement", bannerPlace)
															  .eq("transaction_type", PricingModelEnum.FLAT.name().toLowerCase()).findList();
		Logger.trace("Size Transaction "+transaction );
		if(transaction.size()==0){
			startTransaction(place);
		}
	}
	
	private void startTransaction(BannerPlacement placement) throws Exception{
		Banner banner=placement.getBanner();
		Campaign campaign=banner.getCampaign();
		User user=campaign.getId_user();
		
		int current_balance=user.getCurrent_balance();
		int amount = campaign.getBid_price();
		int dailyPrice=campaign.countPrice()/campaign.campaignDuration();

		AdsTransaction transaction=new AdsTransaction();
		transaction.setAmount(dailyPrice+(int)(dailyPrice*placement.getZone().getPriceFactor()));
		transaction.setCurrent_balance(current_balance-dailyPrice);
		transaction.setBannerPlacement(placement);
		transaction.setTimestamp(new Date());
		transaction.setTransaction_type(PricingModelEnum.FLAT);
		transaction.save();
		
		user.setCurrent_balance(current_balance-dailyPrice);
		user.update();

	}

}

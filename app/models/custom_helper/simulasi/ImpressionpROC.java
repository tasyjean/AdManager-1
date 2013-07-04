package models.custom_helper.simulasi;

import java.util.Date;

import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;
import models.data.User;
import models.data.enumeration.PricingModelEnum;
import play.mvc.Http.Context;

import com.avaje.ebean.Ebean;

public class ImpressionpROC {

	FlatProcessor flatProcessor;
	public ImpressionpROC(FlatProcessor flatProcessor){
		this.flatProcessor=flatProcessor;
	}
	public Impression newImpression(BannerPlacement placement, String source,  Date timestamp){
		Ebean.beginTransaction();
		try {
			Impression impresi=new Impression();
			impresi.setAdsPlacement(placement);
			impresi.setTimestamp(timestamp);
			impresi.setViewer_ip("simulated");
			impresi.setViewer_source(source);
			impresi.save();
			
			//impressioon count untuk banner dan campaign
			Banner banner=placement.getBanner();
			Campaign campaign=banner.getCampaign();
			
			int bannerImpressionCount=banner.getImpression_count();
			banner.setImpression_count(bannerImpressionCount+1);
			banner.update();
			
			int campaignImpressionCount=campaign.getCurrent_impression();
			campaign.setCurrent_impression(campaignImpressionCount+1);
			campaign.update();
			if(campaign.getPricing_model()==PricingModelEnum.CPM){
				if(isTimeToTransaction(campaign)){
					addTransaction(banner,placement, timestamp);
				}
				
			}else if(campaign.getPricing_model()==PricingModelEnum.FLAT){
				
				flatProcessor.process(placement,timestamp);
			}
			Ebean.commitTransaction();
			return impresi;
		} catch (Exception e) {
			e.printStackTrace();
			Ebean.rollbackTransaction();
			return null;
		}finally{
			Ebean.endTransaction();
		}
	}
	
	private void addTransaction(Banner banner, BannerPlacement placement, Date timestamp) throws Exception {
		Campaign campaign=banner.getCampaign();
		User user=campaign.getId_user();
		int dailyPrice=campaign.getBid_price();
		int currentBalance=user.getCurrent_balance()-dailyPrice;
		
		AdsTransaction transaction = new AdsTransaction();
		transaction.setAmount(dailyPrice);
		transaction.setBannerPlacement(placement);
		transaction.setCurrent_balance(currentBalance);
		transaction.setTransaction_type(PricingModelEnum.CPM);
		transaction.setTimestamp(timestamp);
		transaction.save();		
		user.setCurrent_balance(currentBalance);
		user.update();
	}
	//mengetahui apakah sudah 1000 transaksi
	//alternatif nama : isTimeToBlowUserMoney
	private boolean isTimeToTransaction(Campaign campaign){
		if(campaign.getCurrent_impression()%1000==0){
			return true;
		}
		return false;
	}

}

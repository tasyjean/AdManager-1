package models.service.ads_delivery;

import java.util.Date;

import com.avaje.ebean.Ebean;

import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;
import models.data.User;
import models.data.enumeration.PricingModelEnum;
import play.mvc.Http.Context;

public class ImpressionProcessor {

	FlatProcessor flatProcessor;
	public ImpressionProcessor(FlatProcessor flatProcessor){
		this.flatProcessor=flatProcessor;
	}
	public Impression newImpression(BannerPlacement placement, String source,  Context context){
		Ebean.beginTransaction();
		try {
			Impression impresi=new Impression();
			impresi.setAdsPlacement(placement);
			impresi.setTimestamp(new Date());
			impresi.setViewer_ip(context.request().remoteAddress());
			impresi.setViewer_source(source);
			impresi.save();
			context.response().setCookie(placement.getBanner().getId_banner()+"",
										placement.getBanner().getName(), 3*60*60); //satu impresi user dibatesi selama 3 jam
			
			//impressioon count untuk banner dan campaign
			Banner banner=placement.getBanner();
			Campaign campaign=banner.getCampaign();
			
			int bannerImpressionCount=banner.getImpression_count();
			banner.setImpression_count(bannerImpressionCount+1);
			banner.update();
			
			int campaignImpressionCount=campaign.getCurrent_impression();
			campaign.setCurrent_impression(campaignImpressionCount);
			campaign.update();
			if(campaign.getPricing_model()==PricingModelEnum.CPM){
				if(isTimeToTransaction(campaign)){
					addTransaction(banner,placement);
				}				
			}else if(campaign.getPricing_model()==PricingModelEnum.FLAT){
				
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
	
	private void addTransaction(Banner banner, BannerPlacement placement) throws Exception {
		Campaign campaign=banner.getCampaign();
		User user=campaign.getId_user();
		
		int currentBalance=user.getCurrent_balance()-campaign.getBid_price();
		
		AdsTransaction transaction = new AdsTransaction();
		transaction.setAmount(campaign.getBid_price());
		transaction.setBannerPlacement(placement);
		transaction.setCurrent_balance(currentBalance);
		transaction.setTransaction_type(PricingModelEnum.CPM);
		transaction.setTimestamp(new Date());
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

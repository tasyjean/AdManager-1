package models.service.ads_delivery;

import java.util.Date;

import play.mvc.Http.Context;
import play.mvc.Http.Session;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;

public class AdsDeliverer {

	ImpressionProcessor impression;
	public AdsDeliverer(ImpressionProcessor impression){
		this.impression=impression;
	}
	public Impression countImpression(BannerPlacement placement, String source, Context context){
		Banner banner=placement.getBanner();
		Impression impresi;
		if(context.request().cookies().get(banner.getId_banner()+"")!=null){
			impresi=impression.newImpression(placement, source, context);
			return impresi;
		}else{
			impresi=Impression.find.where().eq("bannerPlacement", placement).
											 order().asc("timestamp").findList().get(0);
			
			return impresi;
		}
	}
	

	
}

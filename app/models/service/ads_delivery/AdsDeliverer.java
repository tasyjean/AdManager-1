package models.service.ads_delivery;

import java.util.Date;
import java.util.List;

import play.Logger;
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
		if(context.request().cookies().get(banner.getId_banner()+"")==null){
			impresi=impression.newImpression(placement, source, context);
			return impresi;
		}else{
			Logger.debug("Anu Debug placement"+placement.getId_banner_placement());
			//error karena tampilan banner sudah kecatat di cookie, tapi ngga kecatat di placement
			//solusi pilih placement dengan banner yang cookie itu
			
			try {
				//Oke, gunakan solusi lain, jadikan id placement sebagai value cookie, ntar dipake buat ngeluarin impresi
				int idPlacement=Integer.parseInt(context.request().cookies().get(banner.getId_banner()+"").value());  
				BannerPlacement placemen=BannerPlacement.find.byId(idPlacement);
				impresi=Impression.find.where().eq("bannerPlacement", placemen).
												 order().asc("timestamp").findList().get(0);
				
				return impresi;
			} catch (Exception e) {
				e.printStackTrace();
				List<BannerPlacement> placements=BannerPlacement.find.where().eq("banner", banner).findList();
				impresi=Impression.find.where().in("bannerPlacement", placements).
												 order().asc("timestamp").findList().get(0);
				
				return impresi;
			}
		}
	}
	

	
}

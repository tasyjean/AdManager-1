package models.custom_helper.simulasi;

import java.util.Date;
import java.util.List;

import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;

public class AdDeliver {

	ImpressionpROC impressionpROC;
	public AdDeliver(ImpressionpROC impressionpROC){
		this.impressionpROC=impressionpROC;
	}
	public Impression countImpression(BannerPlacement placement, String source, Date timestamp){
				
			Banner banner=placement.getBanner();
			Impression impresion;
			impresion=impressionpROC.newImpression(placement, source, timestamp);
			return impresion;
	}

}

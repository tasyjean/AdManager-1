package controllers.backend;

/*
 * @Author Xenovon
 * Kelas AdsDeliveryController digunakan untuk menangani request berkaitan dengan 
 * delivery iklan
 */
import java.util.ArrayList;
import java.util.List;

import models.data.Banner;
import models.data.BannerSize;
import models.service.ads_delivery.AdSelector;
import play.Logger;
import play.mvc.Content;
import play.mvc.Result;
import controllers.CompressController;
import views.html.ui_component.ads.*;

public class AdsDeliveryController extends CompressController {

	static AdSelector ad_selector= new AdSelector();

	public static Result banner(int zone){
		
		final int zon=zone;

		return ok(ad_selector.get(100));
	}
	public static Result click(int impression){
		
		return ok();
	}
	
	public static Result getBanner(int idBanner){
		Banner banner=Banner.find.byId(idBanner);
		if(banner!=null){
			if(banner.getBannerType().name().equals("BANNER")){
				String fileName=banner.getContent_link().getName();
				String content;
				String target=banner.getTarget();
				if(fileName.endsWith("swf")){
					content="SWF";
				}else{
					content="IMAGE";
				}
				Logger.debug(content);
				return ok(banner_ads.render(banner, target, content));
			}else{
				return ok("Banner invalid");				
			}
		}else{
			return ok("Banner invalid");
		}
	}
	public static Result getSingleTextBanner(int idBanner){
		Banner banner=Banner.find.byId(idBanner);
		BannerSize size=BannerSize.find.byId(1);
		if(banner!=null){
			if(banner.getBannerType().name().equals("TEXT")){
				List<Banner> banners=new ArrayList<Banner>();
				banners.add(banner);
				String target=banner.getTarget();
				return ok(text_ads.render(banners, target, size));
			}else{
				return ok("Banner invalid");				
			}
		}else{
			return ok("Banner invalid");
		}		
	}
}

package controllers.backend;

/*
 * @Author Xenovon
 * Kelas AdsDeliveryController digunakan untuk menangani request berkaitan dengan 
 * delivery iklan
 */
import java.util.ArrayList;
import java.util.List;

import models.custom_helper.DateBinder;
import models.custom_helper.file_manager.FileManagerFactory;
import models.custom_helper.file_manager.FileManagerInterface;
import models.data.Banner;
import models.data.BannerSize;
import models.service.ads_delivery.AdSelector;
import models.service.campaign.BannerProcessor;
import models.service.campaign.CampaignProcessor;
import models.service.notification.NotificationCenter;
import play.Logger;
import play.mvc.Content;
import play.mvc.Result;
import controllers.CompressController;
import views.html.ui_component.ads.*;

public class AdsDeliveryController extends CompressController {

	static FileManagerInterface manager=new FileManagerFactory().getManager();
	static BannerProcessor banner=new BannerProcessor(manager);
	static DateBinder binder=new DateBinder();
	static CampaignProcessor campaign=new CampaignProcessor(binder,banner);
	static NotificationCenter notif=new NotificationCenter();
	static AdSelector ad_selector=new AdSelector(banner,campaign,binder,notif);

	public static Result banner(int zone){
		
		final int zon=zone;

		return ok("Banner terpilih "+ad_selector.get(zone));
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

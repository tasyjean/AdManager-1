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
import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.Banner;
import models.data.BannerAction;
import models.data.BannerPlacement;
import models.data.BannerSize;
import models.data.Impression;
import models.data.Zone;
import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.ZoneTypeEnum;
import models.service.ads_delivery.AdActionProcessor;
import models.service.ads_delivery.AdSelector;
import models.service.ads_delivery.AdsDeliverer;
import models.service.ads_delivery.FlatProcessor;
import models.service.ads_delivery.ImpressionProcessor;
import models.service.campaign.BannerProcessor;
import models.service.campaign.CampaignProcessor;
import models.service.notification.NotificationCenter;
import play.Logger;
import play.mvc.Call;
import play.mvc.Content;
import play.mvc.Http.Context;
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
	static FlatProcessor flatProcessor=new FlatProcessor();
	static ImpressionProcessor impression=new ImpressionProcessor(flatProcessor);
	static AdActionProcessor adAction=new AdActionProcessor();
	static AdsDeliverer adsDeliverer=new AdsDeliverer(impression);
	static SettingManager setting=new SettingManager();
	
	
	public static Result banner(int zone, String source){
		Zone zone_object=Zone.find.byId(zone);
		
		List<BannerPlacement> result=ad_selector.get(zone_object);
		if(result==null){
			String body="";		
			try {
				if(zone_object.getZone_default_view()==DefaultViewEnum.DEFAULT_ADS){
					body=zone_object.getBanner_size().getDefault_code();
				}else if(zone_object.getZone_default_view()==DefaultViewEnum.DEFAULT_CODE){
					body=zone_object.getZone_default_code();				
				}else{
					body="";
				}
			} catch (Exception e) {
				e.printStackTrace();
				body="";
			}			
			return ok(empty_ads.render(body));
		}else{
			Logger.debug("Daftar Banner= "+result.toString());
			Logger.debug("Ukuran Banner= "+result.size());

			List<Impression> impression=new ArrayList<Impression>();
			List<Banner> banner=new ArrayList<Banner>();
			int i=0;
			for(BannerPlacement bannerPlacement:result){
				impression.add(adsDeliverer.countImpression(bannerPlacement, source, Context.current()));
				banner.add(bannerPlacement.getBanner());
			}
			if(zone_object.getZone_type()==ZoneTypeEnum.BANNER){
				//pastikan ukurannya 1
				Banner banner_send=banner.get(0);
				Impression impression_send=impression.get(0);
				String fileName=banner_send.getContent_link().getName();
				String tipe="";
				if(fileName.endsWith("swf")){
					tipe="SWF";
				}else{
					tipe="IMAGE";
				}
					return ok(banner_ads_production.render(banner_send,impression_send,tipe));
				
			}else{
				return ok(text_ads_production.render(banner,impression,zone_object.getBanner_size()));
			}
		}

	}
	public static Result showZoneDefaultView(int zone){
		return TODO;
		
	}
	public static Result clickHandler(long impression){
		Impression impresi=Impression.find.byId(impression);
		String route=impresi.getAdsPlacement().getBanner().getTarget();
		Logger.debug("Route "+route);
		BannerAction action=adAction.click(impresi);
		if(action==null){
			Logger.error("Terjadi exception pada pencatatan click");
		}
		return redirect(route);
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

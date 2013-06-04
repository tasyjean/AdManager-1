package controllers.backend;

/*
 * @Author Xenovon
 * Kelas AdsDeliveryController digunakan untuk menangani request berkaitan dengan 
 * delivery iklan
 */
import models.service.ads_delivery.AdSelector;
import play.mvc.Content;
import play.mvc.Result;
import controllers.CompressController;

public class AdsDeliveryController extends CompressController {

	static AdSelector ad_selector= new AdSelector();

	public static Result banner(int zone){
		
		final int zon=zone;

		return ok(ad_selector.get(100));
	}
	public static Result click(int impression){
		
		return ok();
	}
}

package controllers.backend;

/*
 * @Author Xenovon
 * Kelas AdsDeliveryController digunakan untuk menangani request berkaitan dengan 
 * delivery iklan
 */
import play.mvc.Result;
import controllers.CompressController;

public class AdsDeliveryController extends CompressController {

	public static Result index(){
		
		return ok();
	}

}

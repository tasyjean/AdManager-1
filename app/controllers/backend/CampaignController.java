package controllers.backend;

/*
 * @Author Xenovon
 * Kelas CampaignController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import play.mvc.Result;
import controllers.CompressController;

public class CampaignController extends CompressController {

	public static Result index(){
		
		return ok();
	}

}

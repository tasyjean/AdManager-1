package controllers.backend;

/*
 * @Author Xenovon
 * Kelas CampaignController digunakan untuk menangani request berkaitan dengan 
 * manajemen pengguna, khususnya untuk administrator 
 */
import play.mvc.Result;
import controllers.CompressController;

public class UserController extends CompressController {

	public static Result index(){
		
		return ok();
	}

}

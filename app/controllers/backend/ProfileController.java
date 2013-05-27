package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ProfileController digunakan untuk menangani request berkaitan dengan 
 * manajemen profile masing-masing user
 */
import play.mvc.Result;
import controllers.CompressController;

public class ProfileController extends CompressController {

	public static Result index(){
		
		return ok();
	}

}

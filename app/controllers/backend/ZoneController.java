package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ZoneController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import java.util.HashMap;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.dataWrapper.TemplateData;
import models.form.backendForm.zoneForm.ChannelForm;
import models.form.backendForm.zoneForm.ZoneForm;
import models.form.frontendForm.LoginForm;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.zone_view.*;

public class ZoneController extends CompressController {

	final static Form<ZoneForm> zoneForm = Form.form(ZoneForm.class);
    final static Form<ChannelForm> loginForm = Form.form(ChannelForm.class);
	
    final static String create_zone="create_zone";
    final static String create_channel="create_channel";
    final static String view_single_channel="view_single_channel";
    final static String view_single_zone="view_single_zone";
    final static String view_list="view_list";
    final static String view_banner_size_list="view_banner_size_list";
    
    
    /*
     * View Choice data
     * create_zone
     * create_channel
     * view_single_channel
     * view_single_zone
     * view_list
     * view_banner_size
     * 
     */
    
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result index(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		HashMap<String, Object> map_data= new HashMap<String, Object>();
		map_data.put("data", "Data Anu");
		return ok(zone_index.render(data));
	}
	
	public static Result showZone(){
		return ok();
	}
	
	public static Result showChannel(){
		
		return ok();
	}
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result createZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(create_zone.render(data,loginForm));
		
	}
	public static Result saveZone(){
		return ok();
	}
	public static Result saveChannel(){
		return ok();
	}
	
	@With(DataFiller.class)	
	public static Result createChannel(){
		return ok();
	}

	public static Result editZone(){
		return ok();
	}
	
	public static Result editChannel(){
		return ok();
	}
	
	public static Result deleteChannel(){
		return ok();
	}
	
	public static Result deleteZone(){
		return ok();
	}
}

package controllers.backend;

/*
 * @Author Xenovon
 * Kelas ZoneController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import java.util.HashMap;

import com.amazonaws.services.simpleemail.model.Message;
import com.avaje.ebean.Page;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.data.Zone;
import models.data.ZoneChannel;
import models.dataWrapper.TemplateData;
import models.form.backendForm.zoneForm.ChannelForm;
import models.form.backendForm.zoneForm.ZoneForm;
import models.form.frontendForm.LoginForm;
import models.service.Authentificator;
import models.service.zone.ChannelProcessor;
import models.service.zone.ErrorEnum;
import models.service.zone.ZoneProcessor;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.zone_view.*;
import views.html.frontendView.login;

public class ZoneController extends CompressController {

	final static Form<ZoneForm> zoneForm = Form.form(ZoneForm.class);
    final static Form<ChannelForm> channelForm = Form.form(ChannelForm.class);
	
    static ChannelProcessor cp = new ChannelProcessor();
    static ZoneProcessor zp = new ZoneProcessor();


	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showZonePage(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<Zone> zone_data=zp.getZone(page, 10, "", "", "");
		return ok(zone_index.render(data, zone_data));
	}
	@With(DataFiller.class)	
	@SubjectPresent	
	public static Result showZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<Zone> zone_data=zp.getZone(0, 10, "", "", "");
		return ok(zone_index.render(data, zone_data));
	}
	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showChannel(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<ZoneChannel> channel_data=cp.getChannel(0, 10, "", "", "");
		return ok(channel_index.render(data, channel_data));
	}
	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showChannelPage(int page){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Page<ZoneChannel> channel_data=cp.getChannel(page, 10, "", "", "");		
		return ok(channel_index.render(data, channel_data));
	}	
	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showSingleZone(int id_zone){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(show_single_zone.render(data,zp.getSingleZone(id_zone)));
	}

	@With(DataFiller.class)	
	@SubjectPresent
	public static Result showSingleChannel(int id_channel){	
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(show_single_channel.render(data, 
											 cp.getSingleChannel(id_channel), 
											 cp.getChannelZone(id_channel)));
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))
	public static Result createZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(create_zone.render(data,zoneForm,zp.getZoneFormData()));
		
	}
	
	@With(DataFiller.class)
	@Restrict(@Group("administrator"))	
	public static Result createChannel(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(create_channel.render(data, channelForm));
	}
	
	@Restrict(@Group("administrator"))
	@With(DataFiller.class)
	public static Result saveZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<ZoneForm> filledForm=Form.form(ZoneForm.class).bindFromRequest();
		if(filledForm.hasErrors()){
			
			return ok(create_zone.render(data,filledForm, zp.getZoneFormData()));
		}else{
			try {
				Zone zona=zp.saveForm(filledForm);
				return ok(create_zone_success.render(data,zona));
			} catch (NullPointerException e) {
				e.printStackTrace();
				filledForm.globalErrors().add(new ValidationError("error_update", Messages.get("validation.error_update")));
				return ok(create_zone.render(data,filledForm, zp.getZoneFormData()));
			}
			
		}
	}
	
	@Restrict(@Group("administrator"))
	@With(DataFiller.class)
	public static Result saveChannel(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		Form<ChannelForm>  channelForm = Form.form(ChannelForm.class).bindFromRequest();
		if(channelForm.hasErrors()) {
			return ok(create_channel.render(data, channelForm));
        } else {
        	ZoneChannel zoneChannel = cp.saveForm(channelForm);
    		return ok(create_channel_success.render(data, zoneChannel));
        }		
	}

	@With(DataFiller.class)		
	public static Result editZone(int id_zone){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		session("id_edit_zone", Integer.toString(id_zone)); //untuk menyimpan zona mana yang diedit
		return ok(edit_zone.render(data, zoneForm, zp.getZoneFormData(),zp.getSingleZone(id_zone)));
	}
	
	@With(DataFiller.class)		
	public static Result saveEditZone(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<ZoneForm> filledForm=Form.form(ZoneForm.class).bindFromRequest();
		int id_zone = Integer.parseInt(session("id_edit_zone"));
		
		if(filledForm.hasErrors()){
			return badRequest(edit_zone.render(data, filledForm, zp.getZoneFormData(), zp.getSingleZone(id_zone)));
		}else{
			boolean sukses=zp.saveFormEdit(filledForm, id_zone);
			if(sukses){
				flash("success","Zona Berhasil di ubah");
				return redirect(controllers.backend.routes.ZoneController.showSingleZone(id_zone));
			}else{
				filledForm.globalErrors().add(new ValidationError("error_update", Messages.get("validation.error_update")));
				flash("success",Messages.get("validation.error_update"));
				return badRequest(edit_zone.render(data, filledForm, zp.getZoneFormData(), zp.getSingleZone(id_zone)));
			}
		}
	}
	@With(DataFiller.class)		
	public static Result editChannel(int id_channel){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		session("id_edit_channel", Integer.toString(id_channel));
		return ok(edit_channel.render(data,channelForm, cp.getSingleChannel(id_channel)));
	}
	@With(DataFiller.class)		
	public static Result saveEditChannel(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<ChannelForm> filledForm=Form.form(ChannelForm.class).bindFromRequest();
		
		int id_channel = Integer.parseInt(session("id_edit_channel"));
		if(filledForm.hasErrors()){
			return badRequest(edit_channel.render(data, filledForm,  cp.getSingleChannel(id_channel)));
		}else{
			boolean sukses=cp.updateChannel(filledForm, id_channel);
			if(sukses){
				flash("success","Channel Berhasil di ubah");
				return redirect(controllers.backend.routes.ZoneController.showSingleChannel(id_channel));
			}else{
				filledForm.globalErrors().add(new ValidationError("error_update", Messages.get("validation.error_update")));
				flash("success",Messages.get("validation.error_update"));
				return badRequest(edit_channel.render(data, filledForm, cp.getSingleChannel(id_channel)));
			}	
		}
		
	}
	

	@With(DataFiller.class)		
	public static Result deleteChannel(int id_channel){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		ErrorEnum errorEnum=cp.softDelete(id_channel);
		if(errorEnum.equals(ErrorEnum.INTEGRITY_PROBLEM)){
			flash("error",Messages.get("error.delete.integrity"));
		}else if(errorEnum.equals(ErrorEnum.JUST_PLAIN_FAILED)){
			flash("error",Messages.get("error.delete.plain"));
		}else{
			flash("success",Messages.get("error.delete.success"));			
		}
		
		return ok(channel_delete_status.render(data,request().getHeader(REFERER)));
	}
	
	@With(DataFiller.class)		
	public static Result deleteZone(int id_zone){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		ErrorEnum errorEnum=zp.softDelete(id_zone);
		if(errorEnum.equals(ErrorEnum.INTEGRITY_PROBLEM)){
			flash("error",Messages.get("error.delete.integrity"));
		}else if(errorEnum.equals(ErrorEnum.JUST_PLAIN_FAILED)){
			flash("error",Messages.get("error.delete.plain"));
		}else{
			flash("success",Messages.get("error.delete.success"));			
		}
		
		return ok(zone_delete_status.render(data, request().getHeader(REFERER)));
	}
	

}

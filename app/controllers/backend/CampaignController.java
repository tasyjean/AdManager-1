package controllers.backend;

/*
 * @Author Xenovon
 * Kelas CampaignController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import java.util.Date;

import models.custom_helper.setting.SettingManager;
import models.dataWrapper.TemplateData;
import models.dataWrapper.campaign.CampaignFormData;
import models.form.backendForm.campaignForm.CampaignForm;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.campaign_view.*;

public class CampaignController extends CompressController {

	public static SettingManager manager=new SettingManager();
	public static CampaignFormData campaignData;
	public static Form<CampaignForm> campaignForm=Form.form(CampaignForm.class);
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showCampaign(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(campaign_index.render(data));
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result newCampaign(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		campaignData=new CampaignFormData(manager);
		
		return ok(create_campaign.render(data, campaignForm, campaignData));
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result saveNewCampaign(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<CampaignForm> filledForm=Form.form(CampaignForm.class).bindFromRequest();
		campaignData=new CampaignFormData(manager);
		
		return ok(create_campaign.render(data, filledForm, campaignData));
	}
		
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result newBanner(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok();
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result newBannerPlacement(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok();
	}
	
}

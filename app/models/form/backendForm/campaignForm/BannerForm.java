package models.form.backendForm.campaignForm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.custom_helper.DateBinder;
import models.custom_helper.setting.SettingManager;
import models.data.Campaign;
import play.data.validation.ValidationError;
import play.i18n.Messages;

public class BannerForm {

	public String id_banner="";
	public String campaign="";
	public String bannerSize="";
	public String bannerType="";
	public String name="";
	public String description="";
	public String title="";
	public String content_text="";
	public String target="";
	public String alt_text="";
	public String weight="";
	/*
	 * 
	 * Panjang tittle maksimal 27
	 * panjang content text max 73
	 */
	public List<ValidationError> validate(){
		List<ValidationError> error= new ArrayList<ValidationError>();
		if(description.length()>250){
			error.add(new ValidationError("description", Messages.get("error.description")));			
		}
		//validasi kosong apa ngga
		if(title.length()==0){
			error.add(new ValidationError("title", Messages.get("error.title_required")));
		}
		if(content_text.length()==0){
			error.add(new ValidationError("content_text", Messages.get("error.contentText_required")));			
		}
		if(bannerType.length()==0){
			error.add(new ValidationError("bannerType", Messages.get("error.bannerType_required")));			
		}
		if(name.length()==0){
			error.add(new ValidationError("name", Messages.get("error.name_required")));			
		}
		if(bannerType.equals("TEXT")){
			if(bannerSize.length()==0){
				error.add(new ValidationError("bannerSize", Messages.get("error.banneSize_required")));			
			}	
			//validasi panjang maksimal
			if(title.length()>27){
				error.add(new ValidationError("title", Messages.get("error.title_toolong")));
			}
			if(content_text.length()>73){
				error.add(new ValidationError("content_text", Messages.get("error.contentText_toolong")));			
			}			
		}
		if(target.length()!=0){
			try {
				URL url=new URL(target);
			} catch (MalformedURLException e) {
				error.add(new ValidationError("target", Messages.get("error.url_salah")));			
				e.printStackTrace();
			}	
		}
		try{
			int weight_int=Integer.parseInt(weight);
			if(weight_int>10 || weight_int<0 ){
				error.add(new ValidationError("weight", Messages.get("error.weight_range")));										
			}
		}catch(NumberFormatException e){
			error.add(new ValidationError("weight", Messages.get("error.weight_required")));						
		}
		try{
			int idCampaign=Integer.parseInt(campaign);
			Campaign campaign=Campaign.find.byId(idCampaign);
			campaign.getId_campaign();
		}catch(NumberFormatException e){
			System.out.println("CAMPAIGN "+campaign);
			error.add(new ValidationError("campaign", Messages.get("error.campaign_salah")));
			e.printStackTrace();
		}catch(NullPointerException e){
			System.out.println("CAMPAIGN "+campaign);
			error.add(new ValidationError("campaign", Messages.get("error.campaign_salah")));	
			e.printStackTrace();
		}
		return (error.size()==0)? null:error;
		


	}
	
	
}

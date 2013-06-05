package models.form.backendForm.zoneForm;

import java.util.List;

import javax.persistence.Column;

import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import models.data.BannerSize;
import models.data.ZoneChannel;
import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.ZoneTypeEnum;

public class ChannelForm {

	public String channel_name="";
	
	public String channel_description="";

	public String validate(){
		if(channel_name.equals("")){
			return Messages.get("constraint.required");
		}
		
		return null;
	}
	
	private boolean isEmpty(String input){
		try{
			if(input.equals("")){
				return true;
			}			
		}catch(NullPointerException e)
		{
			return true;
		}
		return false;
	}
}

package models.form.backendForm.zoneForm;

import java.util.List;

import javax.persistence.Column;

import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import models.data.BannerSize;
import models.data.ZoneChannel;
import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.ZoneTypeEnum;

public class ChannelForm {

	@Required
	private String channel_name;
	
	private String channel_description;

	public String validate(){
				
		return null;
	}
}

package models.form.backendForm.zoneForm;

import java.util.List;

import play.data.validation.ValidationError;

public class ZoneForm {

	private int zone_channel;

	private int ads_size;
	
	private String zone_type;
	
	private String zone_default_view;
	
	private String zone_default_code;
		
	public List<ValidationError> validate(){
		
		return null;
	}

}

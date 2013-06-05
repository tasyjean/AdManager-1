package models.form.backendForm.zoneForm;

import java.util.List;

import play.data.validation.ValidationError;

public class ZoneForm {

	public int zone_channel;
	
	public String name;
	
	public String description;

	public String ads_size;
	
	public String zone_type;
	
	public String zone_default_view;
	
	public String zone_default_code;
		
	public List<ValidationError> validate(){
		
		return null;
	}
	

}

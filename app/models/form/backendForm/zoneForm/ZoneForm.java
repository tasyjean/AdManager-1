package models.form.backendForm.zoneForm;

import java.util.List;

import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

public class ZoneForm {

	@Required
	public String zone_channel="";
	@Required
	public String name="";
	public String description="";
	@Required
	public String banner_size="";
	@Required
	public String zone_type="";
	@Required
	public String zone_default_view="";
	public String zone_default_code="";
	public String session_key="";
	
	public List<ValidationError> validate(){
		
		return null;
	}
	

}

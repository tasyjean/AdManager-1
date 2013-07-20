package models.form.backendForm.zoneForm;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.i18n.Messages;

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
	public String priceFactor;
	
	public List<ValidationError> validate(){
		List<ValidationError> error = new ArrayList<ValidationError>();
		
		try {
			float price = (float) Double.parseDouble(priceFactor);
		} catch (NumberFormatException e) {
			error.add(new ValidationError("priceFactor","Angka faktor harga tidak valid"));
			return error;
		}catch (Exception e){
			error.add(new ValidationError("error","Kesalahan validasi"));	
			return error;
		}
		return null;
	}
	

}

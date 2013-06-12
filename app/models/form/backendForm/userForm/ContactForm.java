package models.form.backendForm.userForm;

import java.util.List;

import play.data.validation.ValidationError;
import play.i18n.Messages;

public class ContactForm {

	public int id;
	public int data_count;
	public String value="";
	public String description="";
	public String contact_type="";
	
	public String validate(){
		if(value.equals("")){
			return Messages.get("validation.required");
		}
		return null;
	}

	
}

package models.form.backendForm.campaignForm;

import java.util.ArrayList;
import java.util.List;

import models.custom_helper.DateBinder;
import models.custom_helper.setting.SettingManager;
import play.data.validation.ValidationError;

public class BannerForm {

	public String id_banner;
	public String campaign;
	public String bannerSize;
	public String bannerType;
	public String name;
	public String description;
	public String tittle;
	public String content_text;
	public String content;  //calon hapus
	public String target;
	public String alt_text;
	public int weight;
	
	public List<ValidationError> validate(){
		List<ValidationError> error= new ArrayList<ValidationError>();
		return error;
	}
	
	
}

package models.service.zone;

import models.data.Zone;
import play.data.Form;

public class ZoneProcessor {

	
	public boolean saveForm(Form<Zone> data){
		return true;
	}
	public Zone getSingleZone(int id){
		return null;
	}
	
	public boolean delete(int id){
		return true;
	}

}

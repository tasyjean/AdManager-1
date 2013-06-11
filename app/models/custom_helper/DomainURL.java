package models.custom_helper;

import play.Play;

public class DomainURL {

	public static String get(){
		return Play.application().configuration().getString("domain.url");
	}

}

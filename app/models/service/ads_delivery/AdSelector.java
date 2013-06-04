package models.service.ads_delivery;

import play.mvc.Content;
import models.data.Banner;
import models.data.Zone;

public class AdSelector {

	//ganti jadi banner nanti
	public Content get(int zone_id){
		
		final int zone=zone_id;
		
		//simulasi
		Content content=new Content() {
			
			@Override
			public String contentType() {
				return "html/text";
			}
			
			@Override
			public String body() {
				String text="<h1>Test Konten</h1>+" +
						"<p>Ini adalah tes dari zona "+zone+" <p>";
				return text;
			}
		};
		return content;
	}
}

package models.service.zone;

import java.util.ArrayList;
import java.util.List;

import org.h2.store.PageOutputStream;

import com.avaje.ebean.Page;
import com.avaje.ebean.annotation.EnumValue;

import models.data.BannerSize;
import models.data.Zone;
import models.data.ZoneChannel;
import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.ZoneTypeEnum;
import models.dataWrapper.zone.ZoneFormData;
import models.form.backendForm.zoneForm.ZoneForm;
import play.data.Form;
import play.i18n.Messages;

public class ZoneProcessor {

	
	public Zone saveForm(Form<ZoneForm> data){
		Zone zone=new Zone();
		ZoneChannel channel=ZoneChannel.find.byId(Integer.parseInt(data.get().zone_channel));
		BannerSize banner_size=BannerSize.find.byId(Integer.parseInt(data.get().banner_size));
		zone.setZone_name(data.get().name);
		zone.setBanner_size(banner_size);
		zone.setDescription(data.get().description);
		zone.setZone_channel(channel);
		zone.setZone_default_code(data.get().zone_default_code);
		zone.setZone_default_view(DefaultViewEnum.valueOf(data.get().zone_default_view));
		zone.setZone_type(ZoneTypeEnum.valueOf(data.get().zone_type));
		
		zone.save();
		return zone;
	}
	public Zone getSingleZone(int id){
		return null;
	}
	
	public boolean delete(int id){
		return true;
	}
	//Mendapatkan data untuk form zona
	public ZoneFormData getZoneFormData(){
		return new ZoneFormData();
	}
	public Page<Zone> getZone(int page, int pageSize, String sortBy, String order, String filter){
        return 
                Zone.find.where()
                    .ilike("zone_name", "%" + filter + "%")
                    .orderBy(sortBy + " " + order)
                    .fetch("zone_channel")
                    .findPagingList(pageSize)
                    .getPage(page);		
	}



}

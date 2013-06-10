package models.service.zone;

import java.util.ArrayList;
import java.util.List;

import org.h2.store.PageOutputStream;

import com.avaje.ebean.Page;
import com.avaje.ebean.annotation.EnumValue;

import models.data.BannerPlacement;
import models.data.BannerSize;
import models.data.Zone;
import models.data.ZoneChannel;
import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.ZoneTypeEnum;
import models.dataWrapper.zone.ZoneFormData;
import models.form.backendForm.zoneForm.ZoneForm;
import play.data.Form;
import play.i18n.Messages;
import scala.collection.parallel.ParIterableLike.Find;

public class ZoneProcessor {

	
	public Zone saveForm(Form<ZoneForm> data){
		Zone zone;
		try {
			zone = new Zone();
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
		}
		return zone;
	}
	public boolean saveFormEdit(Form<ZoneForm> data, int id_zone){
		Zone zone;
		try {
			zone = Zone.find.byId(id_zone);
			ZoneChannel channel=ZoneChannel.find.byId(Integer.parseInt(data.get().zone_channel));
			BannerSize banner_size=BannerSize.find.byId(Integer.parseInt(data.get().banner_size));
			zone.setZone_name(data.get().name);
			zone.setBanner_size(banner_size);
			zone.setDescription(data.get().description);
			zone.setZone_channel(channel);
			zone.setZone_default_code(data.get().zone_default_code);
			zone.setZone_default_view(DefaultViewEnum.valueOf(data.get().zone_default_view));
			zone.setZone_type(ZoneTypeEnum.valueOf(data.get().zone_type));
			
			zone.update();
		} catch (Exception e) {
			e.printStackTrace();			
			return false;
		}
		return true;
	
	}
	public Zone getSingleZone(int id){
		Zone zone=Zone.find.byId(id);
		return (zone.isDeleted()) ? null: zone; //pastikan tidak sedang dihapus
	}
	public ZoneChannel getSingleChannel(int id){
		try {
			ZoneChannel channel=ZoneChannel.find.byId(id);
			return (channel.isDeleted()) ? null:channel;//sama, pastikan tidak sedang dihapus
			
		} catch (NullPointerException e) {
			return null;
		}
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
                    .where()
                    .eq("t0.is_deleted", false) //tidak sedang dihapus (t0 representasi tabel zona)
                    .orderBy(sortBy + " " + order)
                    .fetch("zone_channel") 
                    .findPagingList(pageSize)
                    .getPage(page);		
	}
	
	public ErrorEnum softDelete(int id_zone){
		try {
			Zone zone= Zone.find.byId(id_zone);
			List<BannerPlacement> bannerPlacement = BannerPlacement.find.where().eq("zone", zone).findList();
			if(bannerPlacement.size()!=0){
				return ErrorEnum.INTEGRITY_PROBLEM;
			}else{
				zone.setDeleted(true);
				zone.update();
				return ErrorEnum.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorEnum.JUST_PLAIN_FAILED;
		}
	}
	
	public ErrorEnum hardDelete(int id_zone){
		try {
			Zone zone= Zone.find.byId(id_zone);
			zone.delete();
			return ErrorEnum.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorEnum.JUST_PLAIN_FAILED;
		}
	}
	//delete all
	public ErrorEnum hardDelete(){
		try {
			List<Zone> zones=Zone.find.where().eq("is_deleted", true).findList();
			for(Zone zone:zones){
				zone.delete();
			}
			return ErrorEnum.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorEnum.JUST_PLAIN_FAILED;
		}
	}
	public List<Zone> getListDeleted(){
		return Zone.find.where().eq("is_deleted", true).findList();
	}
	public boolean restore(int id_zone){
		try {
			Zone zone=Zone.find.byId(id_zone);
			zone.setDeleted(false);
			zone.update();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	//restore All
	public boolean restore(){
		try {
			List<Zone> zone=Zone.find.where().eq("is_deleted", true).findList();
			for(Zone single:zone){
				single.setDeleted(false);
				single.update();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	

}

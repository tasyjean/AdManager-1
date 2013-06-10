package models.service.zone;

import java.util.List;

import com.avaje.ebean.Page;

import models.data.BannerPlacement;
import models.data.Zone;
import models.data.ZoneChannel;
import models.form.backendForm.zoneForm.ChannelForm;
import play.data.Form;

public class ChannelProcessor {

	public ZoneChannel saveForm(Form<ChannelForm> form){
		ZoneChannel channel=new ZoneChannel();
		
		channel.setChannel_description(form.get().channel_description);
		channel.setChannel_name(form.get().channel_name);
		
		channel.save();
		return channel;
	}
	public boolean updateChannel(Form<ChannelForm> form, int id_channel){
		try {
			ZoneChannel channel=ZoneChannel.find.byId(id_channel);
			channel.setChannel_description(form.get().channel_description);
			channel.setChannel_name(form.get().channel_name);
			channel.update();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Page<ZoneChannel> getChannel(int page, int pageSize, String sortBy, String order, String filter){
        return 
                ZoneChannel.find.where()
                    .ilike("channel_name", "%" + filter + "%")
                    .where()
                    .eq("t0.is_deleted", false) //tidak sedang dihapus                  
                    .orderBy(sortBy + " " + order)
                    .findPagingList(pageSize)
                    .getPage(page);		
	}
	
	public ZoneChannel getSingleChannel(int id_channel){
		ZoneChannel channel=ZoneChannel.find.byId(id_channel);
		return (channel.isDeleted()) ? null : channel;
	}
	public List<Zone> getChannelZone(int id_channel){
		ZoneChannel channel=ZoneChannel.find.byId(id_channel);
		return Zone.find.where().eq("zone_channel", channel).eq("is_deleted", false).findList();
	}
	
	public ErrorEnum softDelete(int id_channel){
		try {
			ZoneChannel zone_channel= ZoneChannel.find.byId(id_channel);
			List<Zone> zone = Zone.find.where().eq("zone_channel", zone_channel).eq("is_deleted", false).findList();
			if(zone.size()!=0){
				return ErrorEnum.INTEGRITY_PROBLEM;
			}else{
				zone_channel.setDeleted(true);
				zone_channel.update();
				return ErrorEnum.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorEnum.JUST_PLAIN_FAILED;
		}
	}
	
	public ErrorEnum hardDelete(int id_channel){
		try {
			ZoneChannel zone_channel= ZoneChannel.find.byId(id_channel);
			zone_channel.delete();
			return ErrorEnum.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorEnum.JUST_PLAIN_FAILED;
		}
	}
	
	public List<ZoneChannel> getListDeleted(){
		return ZoneChannel.find.where().eq("is_deleted", true).findList();
	}
	public boolean restore(int id_channel){
		try {
			ZoneChannel zone_channel=ZoneChannel.find.byId(id_channel);
			zone_channel.setDeleted(false);
			zone_channel.update();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean restore(){
		try {
			List<ZoneChannel> zone_channel=ZoneChannel.find.where().eq("is_deleted", true).findList();
			for(ZoneChannel single:zone_channel){
				single.setDeleted(false);
				single.update();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


}

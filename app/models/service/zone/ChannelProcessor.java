package models.service.zone;

import java.util.List;

import com.avaje.ebean.Page;

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
	public Page<ZoneChannel> getChannel(int page, int pageSize, String sortBy, String order, String filter){
        return 
                ZoneChannel.find.where()
                    .ilike("channel_name", "%" + filter + "%")
                    .orderBy(sortBy + " " + order)
                    .findPagingList(pageSize)
                    .getPage(page);		
	}

}

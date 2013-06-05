package models.service.zone;

import java.util.List;

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
	public List<ZoneChannel> getChannel(){
	
		return ZoneChannel.find.all();
	}
}

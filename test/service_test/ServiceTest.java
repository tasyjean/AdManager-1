package service_test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;

import org.junit.Test;

import models.custom_helper.SetInitialData;
import models.data.User;
import models.data.Zone;
import models.data.ZoneChannel;
import models.service.zone.ChannelProcessor;
import models.service.zone.ErrorEnum;

public class ServiceTest {

	@Test
	public void testChannelProcessor(){
		running(fakeApplication(), new Runnable() {
				
			@Override
			public void run() {
				
				ChannelProcessor cp=new ChannelProcessor();
				List<Zone> zone=cp.getChannelZone(1);
				System.out.print("size= "+zone.size());
				for(Zone anu:zone){
					System.out.println(anu.getId_zone());
				}
				
				ZoneChannel zone_channel= ZoneChannel.find.byId(81);
				List<Zone> zones = Zone.find.where().eq("zone_channel", zone_channel).findList();
				for(Zone anu:zones){
					System.out.println(anu.getZone_name()+anu.getId_zone());
				}
				
				ChannelProcessor channel=new ChannelProcessor();
				ErrorEnum enume=channel.softDelete(61);
				System.out.println(enume.toString());
			}
		});	
	}

}

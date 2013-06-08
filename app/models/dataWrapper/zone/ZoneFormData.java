package models.dataWrapper.zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import play.data.Form;
import play.i18n.Messages;

import models.data.BannerSize;
import models.data.ZoneChannel;
import models.data.enumeration.DefaultViewEnum;
import models.form.backendForm.zoneForm.ZoneForm;

public class ZoneFormData {

	private List<BannerSize> bannerSize;
	private List<String[]> zoneType;
	private List<String[]> defaultView;
	private List<ZoneChannel> channel;
	
	public ZoneFormData() {
		setBannerSize();
		setChannel();
		setDefaultView();
		setZoneType();
	}
	public List<BannerSize> getBannerSize() {
		return bannerSize;
	}
	private void setBannerSize() {
		this.bannerSize=BannerSize.find.all();
	}
	public List<String[]> getZoneType() {
		
		return zoneType;
	}
	private void setZoneType() {
		List<String[]> zoneType=new ArrayList<String[]>();
		String[] banner={"BANNER",Messages.get("zone.zoneType.banner"),"checked"};  //Berdasarkan tipe yang ada di enumerasi
		String[] text={"TEXT",Messages.get("zone.zoneType.text"),""};
		zoneType.add(banner);
		zoneType.add(text);
		this.zoneType = zoneType;
	}
	public List<String[]> getDefaultView() {
		return defaultView;
	}
	private void setDefaultView() {
		List<String[]> default_view=new ArrayList<String[]>(); //berdasarkan tipe yang ada di enumerasi
		//3 array : value, message, status
		String[] empty={"EMPTY", Messages.get("zone.default_view.empty"),"checked"};
		String[] default_code={"DEFAULT_CODE", Messages.get("zone.default_view.default_code"), ""};
		String[] default_ads={"DEFAULT_ADS", Messages.get("zone.default_view.default_ads"), ""};
		default_view.add(empty);
		default_view.add(default_code);
		default_view.add(default_ads);	
		this.defaultView = default_view;
	}
	
	public List<ZoneChannel> getChannel() {
		return channel;
	}
	private void setChannel() {
		this.channel = ZoneChannel.find.where().eq("is_deleted", false).findList();

	}
	
	
	
}

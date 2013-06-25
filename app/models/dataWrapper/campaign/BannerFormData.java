package models.dataWrapper.campaign;

import java.util.ArrayList;
import java.util.List;

import play.i18n.Messages;

import models.data.BannerSize;
import models.data.enumeration.ZoneTypeEnum;

import scala.Array;

public class BannerFormData {

	private List<String[]> bannerType;
	private List<BannerSize> bannerSize;

	public BannerFormData() {
		setBannerSize();
		setBannerType();
	}
	private void setBannerSize() {
		this.bannerSize=BannerSize.find.all();
	}
	private void setBannerType() {
		List<String[]> bannerType=new ArrayList<String[]>();
		int x=0;
		for(ZoneTypeEnum enums:ZoneTypeEnum.values()){
			String checked="";
			if(x==0){
				checked="checked";
			}
			String[] content={enums.name(),enums.toString(),checked};
			bannerType.add(content);
			x++;
		}
		this.bannerType = bannerType;
	}
	public List<String[]> getBannerType(){
		return bannerType;
	}
	public List<BannerSize> getBannerSize(){
		return bannerSize;
	}
}
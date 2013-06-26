package models.service.campaign;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ning.http.multipart.FilePartSource;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Http.MultipartFormData.FilePart;
import models.custom_helper.file_manager.FileManagerInterface;
import models.custom_helper.file_manager.SaveToEnum;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.BannerSize;
import models.data.Campaign;
import models.data.FileUpload;
import models.data.User;
import models.data.Zone;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.ZoneTypeEnum;
import models.form.backendForm.campaignForm.BannerForm;

public class BannerProcessor {
	FileManagerInterface manager;

	public BannerProcessor(FileManagerInterface manager){
		this.manager=manager;
	}
	public Banner saveBanner(Form<BannerForm> form, FileUpload file){
		Banner banner=null;
		try {
			banner=new Banner();
			int idCampaign=Integer.parseInt(form.get().campaign);
			int idBanner_size=Integer.parseInt(form.get().bannerSize);
					
			Campaign campaign=Campaign.find.byId(idCampaign);
			BannerSize size=BannerSize.find.byId(idBanner_size);
			banner.setCampaign(campaign);
			banner.setBannerSize(size);
			banner.setBannerType(ZoneTypeEnum.valueOf(form.get().bannerType));
			banner.setName(form.get().name);
			banner.setDescription(form.get().description);
			banner.setTitle(form.get().title);
			banner.setContent_text(form.get().content_text);
			banner.setTarget(form.get().target);
			banner.setAlt_text(form.get().alt_text);
			banner.setWeight(Integer.parseInt(form.get().weight));
			banner.setContent_link(file);
			banner.setActive(true);
			banner.save();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return banner;
	}
	
	public int  saveFile(FilePart part, String type){
			/*
			 * validasi type
			 * ada 2 tipe 
			 * tipe banner teks
			 * tipe banner
			 */		
		try {
			String filetype=part.getContentType();			
			if(type.equals("TEXT")){
				if(filetype.equals("image/png") || 
				   filetype.equals("image/jpeg") || 
				   filetype.equals("image/gif"))  {					
					
					File file=part.getFile();
					String key=part.getKey();
					String fileName=part.getFilename();
					String typeContent=part.getContentType();					
					FileUpload upload=manager.saveNew(part, SaveToEnum.ADS_FILE);
					manager.resize(upload.getId(), 70,70);
					return upload.getId();	//sukses			
				
				} else return -2; //jika ngga sesuai format
			}else{
				if(filetype.equals("image/png") || 
						   filetype.equals("image/jpeg") || 
						   filetype.equals("image/gif")  ||
						   filetype.equals("application/x-shockwave-flash"))
						   {					

				FileUpload upload=manager.saveNew(part, SaveToEnum.ADS_FILE);
				return upload.getId();	//sukses			
			} else return -2;	
		}

		} catch (Exception e) {
			Logger.debug("Terjadi Error penyimpanan file :" + e.getMessage());
			e.printStackTrace();
			return -1;
		}	
	}
	public Banner updateBanner(Form<BannerForm> form, int idBanner, FileUpload file){

		Banner banner=null;
		try {
			banner=Banner.find.byId(idBanner);
//			int idCampaign=Integer.parseInt(form.get().campaign);
//			int idBanner_size=Integer.parseInt(form.get().bannerSize);
					
//			Campaign campaign=Campaign.find.byId(idCampaign);
//			BannerSize size=BannerSize.find.byId(idBanner_size);
			
//			banner.setCampaign(campaign);
//			banner.setBannerSize(size);
//			banner.setBannerType(ZoneTypeEnum.valueOf(form.get().bannerType));
			banner.setName(form.get().name);
			banner.setDescription(form.get().description);
			banner.setTitle(form.get().title);
			banner.setContent_text(form.get().content_text);
			banner.setTarget(form.get().target);
			banner.setAlt_text(form.get().alt_text);
			banner.setWeight(Integer.parseInt(form.get().weight));
			if(file!=null){
				FileUpload upload=banner.getContent_link();				
				banner.setContent_link(file);	
				banner.update();
				upload.delete();

			}
			banner.update();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return banner;	
	}
	public Banner showSingleBanner(int idBanner){
		Banner banner=Banner.find.byId(idBanner);
		if(banner.isDeleted()){
			return null;
		}
		return banner;
	}
	public Banner activate(int idBanner){
		try {
			Banner banner=Banner.find.byId(idBanner);
			if(banner.isActive()){
				banner.setActive(false);
			}else{
				banner.setActive(true);
			}
			banner.update();
			return banner;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Banner delete(int idBanner){
		try {
			Banner banner=Banner.find.byId(idBanner);
			if(banner.isActive()){
				banner.setActive(false);
			}else{
				banner.setActive(true);
			}
			banner.update();
			return banner;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	/*
	 * pertama
	 * -cari zona yang tipenya sama dengan banner, text dengan text gambar dengan gambar
	 * -pastikan zona tidak sedang dimiliki oleh banner aktif dari campaign yang aktif
	 * -untuk banner gambar, pastikan zona memiliki ukuran yang sama
	 * -untuk text, hanya pilih zona jenis text yang punya ukuran rectangle dan leader board saja
	 * lets do it
	 */
	public List<Zone> getZoneAvailable(int idBanner){
		try {
			Banner banner=Banner.find.byId(idBanner);
			ZoneTypeEnum type=banner.getBannerType();
			BannerSize bannerSize=banner.getBannerSize();
			BannerSize bannerRect=BannerSize.find.where().eq("name", "Rectangle").findUnique();
			BannerSize bannerLead=BannerSize.find.where().eq("name", "LeaderBoard").findUnique();
			Collection<BannerSize> coll=new ArrayList<BannerSize>();
			coll.add(bannerLead);
			coll.add(bannerRect);
			List<Zone> zones=null;
			//jika teks
			if(type.equals(ZoneTypeEnum.TEXT)){
				zones=Zone.find.where().in("ads_size", coll).eq("zone_type", ZoneTypeEnum.TEXT.name().toLowerCase()).
										order().asc("zone_channel").findList();
			}else{
				zones=Zone.find.where().eq("ads_size", bannerSize).eq("zone_type", type.name().toLowerCase()).
										order().asc("zone_channel").findList();
				int[] delete=new int[zones.size()];
				int deleteCount=0;
				Logger.debug("Ukuran Zones 1 " + zones.size());
				//buang zona yang udah di isi oleh campaign eklusif
				for(int x=0;x<zones.size();x++){
					if(!isZoneFree(zones.get(x))){
						zones.remove(x);				
					}			
				}
			}
			Logger.debug("Ukuran Zones " + zones.size());
			Logger.debug("Type " + type.toString() + type.name());
			return zones;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//free dari yang eklusif
	private boolean isZoneFree(Zone zone){
		List<BannerPlacement> banners=BannerPlacement.find.
													  where().
												      eq("zone",zone).
												      findList();
		Logger.debug("Ukuran zone free " + banners.size());		
		for(BannerPlacement place:banners){
			if(place.getBanner().getCampaign().
					getCampaign_type().equals(CampaignTypeEnum.EXCLUSIVE)){
				return false;
			}
		}
		return true;
		
	}
	/*
	 * Spek array 
	 * array 0 tipe data
	 * array 1 buat value
	 * array 2 buat nama yang tampil (entah channel atau zona)
	 */
	public List<String[]> getZoneAvailableGrouped(List<Zone> zones){
		
		try {
			int channel=0;
			int i=0;
			List<String[]> result=new ArrayList<String[]>();
			for(Zone zone:zones){
				if(!(zone.getZone_channel().getId_zone_channel()==channel)){
					String[] add={"CHANNEL","",zone.getZone_channel().getChannel_name()};
					String[] add2={"ZONE",Integer.toString(zone.getId_zone()),zone.getZone_name()};
					result.add(add);
					result.add(add2);
				}else{
					String[] add={"ZONE",Integer.toString(zone.getId_zone()),zone.getZone_name()};
					result.add(add);				
				}
				channel=zone.getZone_channel().getId_zone_channel();	
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * Nyimpen banner placement
	 * langsung insert aja, kalo update baru agak ribet
	 */
	public boolean saveBannerPlacement(DynamicForm form, int idBanner){
		try {
			Map<String, String> results=form.data();
			System.out.println(results.toString());
			for(Map.Entry<String, String> result:results.entrySet()){
				BannerPlacement placement=new BannerPlacement();
				placement.setBanner(Banner.find.byId(idBanner));
				placement.setZone(Zone.find.byId(Integer.parseInt(result.getValue())));
				placement.save();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

}

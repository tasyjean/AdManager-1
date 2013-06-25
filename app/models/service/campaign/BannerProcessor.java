package models.service.campaign;

import java.io.File;
import java.util.List;

import com.ning.http.multipart.FilePartSource;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import play.Logger;
import play.data.Form;
import play.mvc.Http.MultipartFormData.FilePart;
import models.custom_helper.file_manager.FileManagerInterface;
import models.custom_helper.file_manager.SaveToEnum;
import models.data.Banner;
import models.data.BannerSize;
import models.data.Campaign;
import models.data.FileUpload;
import models.data.User;
import models.data.Zone;
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
			banner.setAdsSize(size);
			banner.setAdsType(ZoneTypeEnum.valueOf(form.get().bannerType));
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
				
				} else return 2; //jika ngga sesuai format
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
			int idCampaign=Integer.parseInt(form.get().campaign);
			int idBanner_size=Integer.parseInt(form.get().bannerSize);
					
			Campaign campaign=Campaign.find.byId(idCampaign);
			BannerSize size=BannerSize.find.byId(idBanner_size);
			
			banner.setCampaign(campaign);
			banner.setAdsSize(size);
			banner.setAdsType(ZoneTypeEnum.valueOf(form.get().bannerType));
			banner.setName(form.get().name);
			banner.setDescription(form.get().description);
			banner.setTitle(form.get().title);
			banner.setContent_text(form.get().content_text);
			banner.setTarget(form.get().target);
			banner.setAlt_text(form.get().alt_text);
			banner.setWeight(Integer.parseInt(form.get().weight));
			if(file!=null){
				FileUpload upload=banner.getContent_link();
				upload.delete();
				banner.setContent_link(file);				
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
	 * 
	 */
	public List<Zone> getZoneAvailable(int idBanner){
		Banner banner=new Banner();
		ZoneTypeEnum type=banner.getAdsType();
		List<Zone> zone=Zone.find.where().eq(arg0, type)
		return null;
	}
	public void saveBannerPlacement(){
		
	}
	

}

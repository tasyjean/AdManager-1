package controllers.backend;

/*
 * @Author Xenovon
 * Kelas CampaignController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;

import com.amazonaws.services.simpleemail.model.Message;
import com.avaje.ebean.Page;

import models.custom_helper.DateBinder;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.FileManagerFactory;
import models.custom_helper.file_manager.FileManagerInterface;
import models.custom_helper.setting.SettingManager;
import models.data.Banner;
import models.data.Campaign;
import models.data.FileUpload;
import models.data.User;
import models.data.UserRole;
import models.data.Zone;
import models.data.enumeration.CampaignTypeEnum;
import models.dataWrapper.TemplateData;
import models.dataWrapper.campaign.BannerFormData;
import models.dataWrapper.campaign.CampaignFormData;
import models.form.backendForm.campaignForm.BannerForm;
import models.form.backendForm.campaignForm.CampaignForm;
import models.service.Authenticator;
import models.service.campaign.BannerProcessor;
import models.service.campaign.CampaignProcessor;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import controllers.CompressController;
import controllers.action.DataFiller;
import views.html.backendView.campaign_view.*;
import views.html.backendView.user_view.show_single_user;

public class CampaignController extends CompressController {

	public static SettingManager manager=new SettingManager();
	public static FileManagerInterface fileManager=new FileManagerFactory().getManager();
	public static BannerProcessor bannerProc=new BannerProcessor(fileManager);
	public static CampaignFormData campaignData;
	public static Form<BannerForm> bannerForm=Form.form(BannerForm.class);
	public static Form<CampaignForm> campaignForm=Form.form(CampaignForm.class);
	public static DateBinder binder=new DateBinder();
	public static CampaignProcessor campProc=new CampaignProcessor(binder, bannerProc);
	public static BannerFormData bannerFormData;
	public static Authenticator auth=new Authenticator();
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showCampaign(int selectPage){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Page<Campaign> page=null;
		if(auth.getUserRole(session()).getName().equals("advertiser")){
			page=campProc.getUserCampaign(selectPage-1,auth.getUserLogin(session()),
														 40,"createdAt","ascending","");
			Logger.debug("Merupakan advertiser");
		}else{
			page=campProc.getAllCampaign(selectPage-1, 40, "createdAt", "ascending", "");
		}
		return ok(campaign_index.render(data,page));
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showCustomCampaign(int selectPage, int idUser, int size, 
									   String filter, String sortBy,String order){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Page<Campaign> page=null;
		try {
			session("filter",filter);
			session("sortBy", sortBy);
			session("order",order);
	
			if(auth.getUserRole(session()).getName().equals("advertiser")){
				page=campProc.getUserCampaign(selectPage,auth.getUserLogin(session()),
															 size,sortBy,order,filter);
			}else{
				if(idUser!=0){
					page=campProc.getUserCampaign(selectPage,User.find.byId(idUser),
									size,sortBy,order,filter);				
				}else{
					page=campProc.getAllCampaign(selectPage,
										 size,sortBy,order,filter);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			order=session("order");
			filter=session("filter");
			sortBy=session("sortBy");
			
			redirect(controllers.backend.routes.CampaignController.showCustomCampaign(
					 selectPage,  idUser, size, filter,  sortBy,order
					));
		}
		return ok(campaign_index.render(data,page));
	}
	
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showSingleCampaign(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Campaign campaign=Campaign.find.byId(idCampaign);
		bannerProc.cleanBanner(campaign);
		User user=auth.getUserLogin(session());
		if(user.getRole().getName().equals("advertiser")){
			if(campProc.isOwnerOF(campaign, user)){
				return ok(showSingleCampaign.render(data,campaign));				
			}else{
				return redirect(controllers.backend.routes.CampaignController.showCampaign(1));
			}
		}
		return ok(showSingleCampaign.render(data,campaign));
	}
	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result newCampaign(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		campaignData=new CampaignFormData(manager);
		
		return ok(create_campaign.render(data, campaignForm, campaignData));
	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result saveNewCampaign(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<CampaignForm> filledForm=Form.form(CampaignForm.class).bindFromRequest();
		campaignData=new CampaignFormData(manager);
		if(filledForm.hasErrors()){			
			return ok(create_campaign.render(data, filledForm, campaignData));			
		}else{
			Campaign campaign=campProc.save(filledForm);
			if(campaign==null){
				flash("error","Kesalahan dalam menyimpan data");
				return ok(create_campaign.render(data, filledForm, campaignData));			
			}else{
				flash("success","Campaign Telah disimpan, Lanjutkan dengan membuat banner dan menghubungkan banner dengan zona");				
				return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(campaign.getId_campaign()));
			}
		}
		
	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result editCampaign(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		campaignData=new CampaignFormData(manager);
		Campaign campaign=Campaign.find.byId(idCampaign);
		User user=auth.getUserLogin(session());			
		if(campaign==null){
			flash("error",Messages.get("error.editCampaign"));
			
			return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(idCampaign));
		}
		if(user.getRole().getName().equals("advertiser")){
			if(!campProc.isOwnerOF(campaign, user)){
				return redirect(controllers.backend.routes.CampaignController.showCampaign(1));
			}
		}			
		return ok(edit_campaign.render(data,campaignForm,campaign,campaignData));
	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result updateCampaign(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<CampaignForm> filledForm=Form.form(CampaignForm.class).bindFromRequest();
		campaignData=new CampaignFormData(manager);
		Campaign campaign=Campaign.find.byId(idCampaign);
		if(filledForm.hasErrors()){		
			return ok(edit_campaign.render(data, filledForm,campaign, campaignData));			
		}else{
			Campaign campaign2=campProc.update(filledForm);
			if(campaign2==null){
				flash("error","Kesalahan dalam menyimpan data campaign");
				return ok(edit_campaign.render(data, filledForm, campaign, campaignData));			
			}else{
				flash("success","Perubahan Campaign telah disimpan");				
				return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(campaign2.getId_campaign()));
			}
		}		
	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result deleteCampaign(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		boolean campaign=campProc.deleteCampaign(idCampaign);
		if(campaign){
			flash("success","Campaign dihapus");
			return redirect(controllers.backend.routes.CampaignController.showCampaign(1));
		}else{
			flash("error","Campaign gagal dihapus");
			return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(idCampaign));
		}
	}	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result newBanner(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Campaign campaign=Campaign.find.byId(idCampaign);
		if(campaign.getCampaign_type().equals(CampaignTypeEnum.EXCLUSIVE)){
			bannerFormData = new BannerFormData("contract");			
		}else{
			bannerFormData = new BannerFormData();						
		}
		return ok(create_banner.render(data, idCampaign, bannerForm, bannerFormData));
	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result saveBanner(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<BannerForm> filledForm=Form.form(BannerForm.class).bindFromRequest();
		bannerFormData=new BannerFormData();
		if(filledForm.hasErrors()){			
			return ok(create_banner.render(data, idCampaign, filledForm, bannerFormData));			
		}else{
			//upload file dulu
			MultipartFormData body = request().body().asMultipartFormData();
			FilePart part = body.getFile("bannerContent");
			if (part!= null) {
				String bannerType=filledForm.get().bannerType;
				int result=bannerProc.saveFile(part, bannerType);
				if(result==-1){
					flash("error","Kesalahan saat upload file");					
				}else if(result==-2){
					flash("error","Tipe FIle tidak didukung");
				}else{
					//baru ngesave
					Banner banner=bannerProc.saveBanner(filledForm, FileUpload.find.byId(result));
					if(banner!=null){
						flash("success","Data Banner ditambahkan");
						flash("justMe","hanya aku"); //new banner placement cuma ditujukan jika banner baru dibikin
						return redirect(controllers.backend.routes.CampaignController.newBannerPlacement(banner.getId_banner()));
					}else{
						flash("error","Kesalahan saat menyimpan data");
						return ok(create_banner.render(data, idCampaign, filledForm, bannerFormData));									
					}
				}
			} else {
				
				flash("error", "File banner kosong");
				return ok(create_banner.render(data, idCampaign, filledForm, bannerFormData));			
			}
			return ok(create_banner.render(data, idCampaign, filledForm, bannerFormData));			
		}
	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result editBanner(int idbanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Banner banner=Banner.find.byId(idbanner);
		Campaign campaign=Campaign.find.byId(banner.getCampaign().getId_campaign());
		User user=auth.getUserLogin(session());
		if(user.getRole().getName().equals("advertiser")){
			if(!campProc.isOwnerOF(campaign, user)){
				return redirect(controllers.backend.routes.CampaignController.showCampaign(1));
			}
		}			
		return ok(edit_banner.render(data, banner, bannerForm));
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result updateBanner(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<BannerForm> filledForm=Form.form(BannerForm.class).bindFromRequest();
		Banner banner=Banner.find.byId(idBanner);
		
		if(filledForm.hasErrors()){			
			return ok(edit_banner.render(data, banner, filledForm));			
		}else{
			//upload file dulu
			MultipartFormData body = request().body().asMultipartFormData();
			//jika file upload ngga null, maka ngga ada yang perlu diganti
			FileUpload upload=null;
			FilePart part = body.getFile("bannerContent");
			if (part!= null) {
				String bannerType=filledForm.get().bannerType;
				int result=bannerProc.saveFile(part, bannerType);
				if(result==-1){
					flash("error","Kesalahan saat upload file");					
					return ok(edit_banner.render(data, banner, filledForm));			
				}else if(result==-2){
					flash("error","Tipe File tidak didukung");
					return ok(edit_banner.render(data, banner, filledForm));			
				}else{
					upload=FileUpload.find.byId(result);
				}
			}
			//baru ngesave
			Banner bannerSave=bannerProc.updateBanner(filledForm, idBanner , upload);
			if(bannerSave!=null){
				flash("success","Data Banner diubah ");
				return redirect(controllers.backend.routes.CampaignController.editBanner(bannerSave.getId_banner()));
			}else{
				flash("error","Kesalahan saat menyimpan data");
				return ok(edit_banner.render(data, banner, filledForm));			
			}
		}
	}	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result deleteBanner(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Banner banner=bannerProc.delete(idBanner);
		if(banner!=null){
			flash("sukses","banner telah dihapus");
			return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(
						banner.getCampaign().getId_campaign()));	
		}else{
			flash("error","Banner Gagal dihapus, data banner terhubung dengan data lainnya");
			Banner bannerReturn=Banner.find.byId(idBanner);
			return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(
						bannerReturn.getCampaign().getId_campaign()));				
		}
		
	}		
	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result newBannerPlacement(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		if(flash("justMe")!=null){
			Banner banner=Banner.find.byId(idBanner);
			List<Zone> zones=bannerProc.getZoneAvailable(idBanner);
			List<String[]> zones_group=bannerProc.getZoneAvailableGrouped(zones, banner);
			return ok(create_banner_placement.render(data, idBanner, zones_group, banner));			
		}else{
			try {
				int idCampaign=Banner.find.byId(idBanner).getCampaign().getId_campaign();
				return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(idCampaign));
			} catch (Exception e) {
				e.printStackTrace();
				return redirect(controllers.backend.routes.CampaignController.showCampaign(1));
			}
		}
		
	}
	
//BLok revisi TA=========================================================================================
	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result createBannerByPlacement(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		Campaign campaign=Campaign.find.byId(idCampaign);
		List<Zone> zones=bannerProc.getZoneAvailable();
		List<String[]> zones_group=bannerProc.getZoneAvailableGrouped(zones, campaign);
		return ok(create_banner_by_placement.render(data, zones_group, campaign));			

	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result saveBannerByPlacement(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DynamicForm filledForm=Form.form().bindFromRequest();
		Banner banner=bannerProc.saveBannerByPlacement(filledForm, idCampaign);
		if(banner!=null){
			flash("success","Silahkan Isikan data banner");
			return redirect(controllers.backend.routes.CampaignController.newBannerFromPlacement(banner.getId_banner()));
		}else{
			flash("error","Gagal Menyimpan data");
			return redirect(controllers.backend.routes.CampaignController.createBannerByPlacement(idCampaign));
		}
	}		
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result newBannerFromPlacement(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Banner banner=Banner.find.byId(idBanner);
		return ok(create_banner_from_placement.render(data, banner, bannerForm));
	}
	//Madan kaya method update banner
	@SubjectPresent
	@With(DataFiller.class)
	public static Result saveBannerFromPlacement(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Form<BannerForm> filledForm=Form.form(BannerForm.class).bindFromRequest();
		Banner banner=Banner.find.byId(idBanner);
		
		if(filledForm.hasErrors()){			
			return ok(create_banner_from_placement.render(data, banner, filledForm));
		}else{
			//upload file dulu
			MultipartFormData body = request().body().asMultipartFormData();
			//jika file upload ngga null, maka ngga ada yang perlu diganti
			FileUpload upload=null;
			FilePart part = body.getFile("bannerContent");
			if (part!= null) {
				String bannerType=filledForm.get().bannerType;
				int result=bannerProc.saveFile(part, bannerType);
				if(result==-1){
					flash("error","Kesalahan saat upload file");					
					return ok(create_banner_from_placement.render(data, banner, filledForm));
				}else if(result==-2){
					flash("error","Tipe File tidak didukung");
					return ok(create_banner_from_placement.render(data, banner, filledForm));
				}else{
					upload=FileUpload.find.byId(result);
				}
			}
			//baru ngesave
			Banner bannerSave=bannerProc.updateBanner(filledForm, idBanner , upload);
			if(bannerSave!=null){
				flash("success","Banner dibuat ");
				return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(banner.getCampaign().getId_campaign()));
			}else{
				flash("error","Kesalahan saat menyimpan data");
				return ok(create_banner_from_placement.render(data, banner, filledForm));
			}
		}
	}	
//END BLok revisi TA=====================================================================================	
	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result saveBannerPlacement(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DynamicForm filledForm=Form.form().bindFromRequest();
		boolean result=bannerProc.saveBannerPlacement(filledForm, idBanner);
		if(result){
			Banner banner=Banner.find.byId(idBanner);
			flash("success","Penempatan banner telah disimpan");
			return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(
						    banner.getCampaign().getId_campaign()));
		}else{
			flash("error","Kesalahan dalam penempatan banner");
			Banner banner=Banner.find.byId(idBanner);
			List<Zone> zones=bannerProc.getZoneAvailable(idBanner);			
			List<String[]> zones_group=bannerProc.getZoneAvailableGrouped(zones, banner);
			return ok(create_banner_placement.render(data, idBanner, zones_group, banner));
		}
	}	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result editBannerPlacement(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Banner banner=Banner.find.byId(idBanner);
		List<Zone> zones=bannerProc.getZoneAvailable(idBanner);
		List<String[]> zones_group=bannerProc.getZoneAvailableGrouped(zones, banner.getPlacement(), banner);
		return ok(edit_banner_placement.render(data,banner,zones_group));		
	}	
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result updateBannerPlacement(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		DynamicForm filledForm=Form.form().bindFromRequest();
		Banner banner=Banner.find.byId(idBanner);
		boolean result=bannerProc.updateBannerPlacement(filledForm, idBanner);
		if(result){
			flash("success","Penempatan baru disimpan");
			return redirect(controllers.backend.routes.CampaignController.editBannerPlacement(idBanner));
		}else{
			flash("error","Kesalahan dalam penempatan banner");
			List<Zone> zones=bannerProc.getZoneAvailable(idBanner);			
			List<String[]> zones_group=bannerProc.getZoneAvailableGrouped(zones, banner.getPlacement(), banner);
			return ok(edit_banner_placement.render(data, banner, zones_group));
		}		
	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)	
	public static Result activateBanner(int idBanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		Banner banner=bannerProc.activate(idBanner);
		if(banner!=null){
			if(banner.isActive()){
				flash("success","Banner diaktifkan");				
			}else{
				flash("success","Banner di non aktifkan");				
			}
		}else{
			flash("error","Aktivasi gagal");							
		}
		return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(
				banner.getCampaign().getId_campaign()));				

	}
	@Restrict({@Group("administrator"), @Group("advertiser")})
	@With(DataFiller.class)
	public static Result activate(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		int result=campProc.activate(idCampaign);
		if(result==0){
			flash("success","Campaign Telah di nonaktifkan");
		}else if(result==1){
			flash("success","Campaign Telah di aktifkan");			
		}else{
			flash("error","aktivasi campaign gagal");						
		}
		return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(idCampaign));
	}		
}

package controllers.backend;

/*
 * @Author Xenovon
 * Kelas CampaignController digunakan untuk menangani request berkaitan dengan 
 * manajemen iklan dan campaign
 */
import java.util.List;
import java.util.Date;
import java.util.Map;

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
import models.dataWrapper.TemplateData;
import models.dataWrapper.campaign.BannerFormData;
import models.dataWrapper.campaign.CampaignFormData;
import models.form.backendForm.campaignForm.BannerForm;
import models.form.backendForm.campaignForm.CampaignForm;
import models.service.Authenticator;
import models.service.campaign.BannerProcessor;
import models.service.campaign.CampaignProcessor;

import be.objectify.deadbolt.java.actions.SubjectPresent;
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
	public static CampaignProcessor campProc=new CampaignProcessor(binder);
	public static BannerFormData bannerFormData;
	public static Authenticator auth=new Authenticator();
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result showCampaign(int selectPage){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		Page<Campaign> page=null;
		if(auth.getUserRole(session()).equals("advertiser")){
			page=campProc.getUserCampaign(selectPage-1,auth.getUserLogin(session()),
														 40,"createdAt","ascending","");
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
	
			if(auth.getUserRole(session()).equals("advertiser")){
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
		
		return ok(showSingleCampaign.render(data,campaign));
	}
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result newCampaign(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		campaignData=new CampaignFormData(manager);
		
		return ok(create_campaign.render(data, campaignForm, campaignData));
	}
	@SubjectPresent
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
	@SubjectPresent
	@With(DataFiller.class)
	public static Result editCampaign(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		campaignData=new CampaignFormData(manager);
		Campaign campaign=Campaign.find.byId(idCampaign);
		if(campaign.isActivated()){
			flash("error",Messages.get("error.editCampaign"));
			
			return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(idCampaign));
		}
		return ok(edit_campaign.render(data,campaignForm,campaign,campaignData));
	}
	@SubjectPresent
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
	@SubjectPresent
	@With(DataFiller.class)
	public static Result deleteCampaign(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok("delete campaign");
	}	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result newBanner(int idCampaign){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		bannerFormData = new BannerFormData();
		return ok(create_banner.render(data, idCampaign,bannerForm, bannerFormData));
	}
	@SubjectPresent
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
					flash("error","Hanya mendukung file jpg, png dan gif");
				}else{
					//baru ngesave
					Banner banner=bannerProc.saveBanner(filledForm, FileUpload.find.byId(result));
					if(banner!=null){
						flash("success","Data Banner ditambahkan");
						return redirect(controllers.backend.routes.CampaignController.showSingleCampaign(idCampaign));
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
	@SubjectPresent
	@With(DataFiller.class)
	public static Result editBanner(int idbanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok("edit banner");
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result updateBanner(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok("update banner");
	}	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result deleteBanner(int idbanner){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok("delete banner");
		
	}		
	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result newBannerPlacement(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok("new Banner placement");
	}
	@SubjectPresent
	@With(DataFiller.class)
	public static Result editBannerPlacement(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok("edit banner placement");
	}	
	@SubjectPresent
	@With(DataFiller.class)
	public static Result updateBannerPlacement(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok("update banner");
	}
	@SubjectPresent
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

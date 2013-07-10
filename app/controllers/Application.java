package controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.custom_helper.SetInitialData;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.FileManagerFactory;
import models.custom_helper.file_manager.FileManagerInterface;
import models.data.Banner;
import models.data.User;
import models.data.UserRole;
import models.data.Zone;
import models.data.ZoneChannel;
import models.data.enumeration.RoleEnum;
import models.dataWrapper.report.BannerList;
import models.service.campaign.BannerProcessor;

import play.*;
import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import play.mvc.Http.RequestHeader;
import controllers.frontend.*;
import views.html.*;
import views.html.test.*;
import views.html.frontendView.*;
import views.html.ui_component.ads.*;
import controllers.frontend.FrontEndController;

public class Application extends CompressController {
  
    public static Result index() {

    	//SOLUTIOOOON
    	return redirect(controllers.frontend.routes.FrontEndController.home());

    	// return redirect(controllers.frontend.routes.Frontend.home());
    }
    
    public static Result testRequest(){
    	
    	String host=request().getHeader(HOST);
    	String method=request().method();
    	String host2=request().host();
    	String req=request().path();
    	String remoteAdress=request().remoteAddress();
    	String uri=request().uri();
    	String source=request().getHeader(REFERER);
    	String agent=request().getHeader(USER_AGENT);
    	String play_path=Play.application().path().getAbsolutePath();
    	String play_path2 = "";
    	FileManager manager=new FileManager();
//    	String pathfile=manager.getFilePath(1);
    	String path=System.getenv("PWD");
    	Map<String, String> string=System.getenv();
    	try {
			play_path2=Play.application().path().getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String play_path3=Play.application().path().getName();
    	String play_path4=Play.application().path().getPath();
    	String play_path5=Play.application().path().toString();

		URL url = null;
		File result=new File("/public/resulaat.jpg");
		File result2=new File("public/resulaat2.jpg");
		try {
			url=new URL("http://www.blogcdn.com/www.engadget.com/media/2013/06/air-comparison-2012-11-14-619-3.jpg");
			InputStream input=url.openStream();
			OutputStream outputStream=new FileOutputStream(result);
			OutputStream outputStream2=new FileOutputStream(result2);

			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = input.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
				outputStream2.write(bytes, 0, read);
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch(IOException io){
			
		}
		
		UserRole advertiser_role=UserRole.find.where().eq("name", RoleEnum.ADVERTISER).findUnique();
		List<User> userList=User.find.where().eq("role", advertiser_role).findList();

		
		String gambar=result.getAbsolutePath();
    	ArrayList<String> data=new ArrayList<String>();
    	data.add(host);
    	data.add(method);
    	data.add(host2);
    	data.add(req);
    	data.add(remoteAdress);
    	data.add(uri);
    	data.add(source);
    	data.add(agent);
    	data.add(play_path);
    	data.add(play_path2);
    	data.add(play_path3);
    	data.add(play_path4);
    	data.add(play_path5);
//    	data.add(pathfile);
    	data.add(path);
    	data.add(string.toString());
    	data.add(gambar);
    	data.add(Integer.toString(userList.size()));
    	RequestBody body = request().body();
//    	return ok("Got json: " + body.asText());
    	
    	List<Zone> zone=Zone.find.order().desc("zone_channel").findList();
    	FileManagerInterface managers=new FileManagerFactory().getManager();
    	BannerProcessor proc=new BannerProcessor(managers);
    	
    	List<String[]> dataks=proc.getZoneAvailableGrouped(zone);
    	return ok(testView.render(data,dataks));
    	
    }
    
    public static Result testWrite(){
    	
    	ZoneChannel channel=new ZoneChannel();
    	channel.setChannel_name("Channel 1");
    	channel.setChannel_description("Channel Name");
    	
    	channel.save();
    	
    	return ok();
    	
    }
    
    public static Result testSession(){
    	
    	String date=new Date().toString().replace(" ", "");
    	flash("date",date);
    	session("session_date"+date,"132");
    	return ok(test1.render());
    }
    public static Result testSession2(){
    	DynamicForm form=Form.form().bindFromRequest();
    	form.get("id");
    	return ok();
    }
    public static Result testDatabase(){
//    	return ok(User.find.where().eq("email", "komputok@gmail.com").findList().get(0).getEmail());
    	return ok("<h1>setangkai anggrek bulan.....</h1>");
    }
    public static Result testJS(String url){

    	Logger.debug(url);
    	return ok(empty_ads.render("<h1><a href="+url+">"+url+"</a></h1>"));
    }
    public static Result testReport(int id){
    	Banner banner=Banner.find.byId(id);
    	Calendar calendar=Calendar.getInstance();
    	calendar.set(Calendar.DATE, -90);
    	Date from=calendar.getTime();
    	calendar.set(Calendar.DATE, 20);
    	Date to=calendar.getTime();
    	BannerList banners=new BannerList(banner, from, to);
    	return ok(banners.getCTR()+" "+banners.getClick_count()+" "+banners.getImpresion_count()+" "+banners.getBanner().getName());
    }
    public static Result simulasi(){
		SetInitialData data=new SetInitialData();
		data.setDataUser();
		data.setBannerSize();
		data.setZoneChannel();
		data.setCampaignData();
		data.createBanner();
		data.simulasi();    
		return ok("sukses");
    }
}

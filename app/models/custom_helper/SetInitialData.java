package models.custom_helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.mvc.Http.Context;
import scala.util.Random;
import views.html.ui_component.ads.empty_ads;

import com.avaje.ebean.Ebean;

import models.custom_helper.file_manager.FileManager;
import models.custom_helper.simulasi.AdDeliver;
import models.custom_helper.simulasi.Click;
import models.custom_helper.simulasi.ImpressionpROC;
import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerAction;
import models.data.BannerPlacement;
import models.data.BannerSize;
import models.data.Campaign;
import models.data.Deposito;
import models.data.FileUpload;
import models.data.Impression;
import models.data.Notification;
import models.data.TransferConfirmation;
import models.data.User;
import models.data.UserContact;
import models.data.UserRole;
import models.data.Zone;
import models.data.ZoneChannel;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.PaymentMethodEnum;
import models.data.enumeration.PricingModelEnum;
import models.data.enumeration.RoleEnum;
import models.data.enumeration.ZoneTypeEnum;
import models.form.backendForm.financeForm.DepositoForm;
import models.service.ads_delivery.AdActionProcessor;
import models.service.ads_delivery.AdSelector;
import models.custom_helper.simulasi.FlatProcessor;
import models.service.ads_delivery.ImpressionProcessor;
import models.service.campaign.BannerProcessor;
import models.service.campaign.CampaignProcessor;
import models.service.finance.DepositoOperator;
import models.service.notification.NotifItem;
import models.service.notification.NotificationCenter;
import models.service.notification.NotificationType;

/*Set Initial Data, 
 * 
 * Buat ngisi data dalam rangka testing
 */
public class SetInitialData {
	FileManager manager=new FileManager();
	BannerProcessor bannerproc=new BannerProcessor(manager);
	DateBinder binder=new DateBinder();
	CampaignProcessor campaignProc=new CampaignProcessor(binder, bannerproc);
	NotificationCenter notif=new NotificationCenter();
	DepositoOperator deposito=new DepositoOperator(notif);
	AdSelector selector=new AdSelector(bannerproc, campaignProc, binder, notif);
	AdActionProcessor action=new AdActionProcessor();
	
	
	public SetInitialData(){
		//Konstruktor
	}
	
	//Data user + data kontak
	public void setDataUser(){
		
		
		//Clean Data dulu	
		deleteNotification();
		deleteTransactionData();
		deleteFinanceData();
		deleteCampaignData();
		
		
		deleteUserData();
		try {
			DB.getConnection().createStatement().execute("ALTER SEQUENCE user_data_seq RESTART WITH 1;");
			DB.getConnection().createStatement().execute("ALTER SEQUENCE zone_seq RESTART WITH 1;");
			DB.getConnection().createStatement().execute("ALTER SEQUENCE user_contact_seq RESTART WITH 1;");
			DB.getConnection().createStatement().execute("ALTER SEQUENCE user_role_seq RESTART WITH 1;");
			DB.getConnection().createStatement().execute("ALTER SEQUENCE zone_channel_seq RESTART WITH 1;");
			DB.getConnection().createStatement().execute("ALTER SEQUENCE file_upload_seq RESTART WITH 1;");
			DB.getConnection().createStatement().execute("ALTER SEQUENCE file_upload_seq RESTART WITH 1;");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert data");
		
		List<UserRole> roles=UserRole.find.all();
		for(UserRole role:roles){
			role.delete();
		}
		UserRole role1=new UserRole(RoleEnum.ADMINISTRATOR);
		UserRole role2=new UserRole(RoleEnum.MANAGER);
		UserRole role3=new UserRole(RoleEnum.ADVERTISER);
		
		role1.save();
		role2.save();
		role3.save();
		
		//Data 1
		User user1=new User("komputok@gmail.com", "password", "Adnan", "Hidayat", role1);
		user1.setCity("Bandung");
		user1.setCountry("Indonesia");
		user1.setCompany("Teknimo");
		user1.setActive(true);
		
		ArrayList<UserContact> contact1=new ArrayList<UserContact>();
		UserContact kontak_item1=new UserContact("083862263524",ContactTypeEnum.PRIVATE_PHONE,"Hubungi nomor ini jika darurat");
		UserContact kontak_item2=new UserContact("http://www.teknimo.com", ContactTypeEnum.COMPANY_WEBSITE, "Ini websitenya");
		contact1.add(kontak_item1);
		contact1.add(kontak_item2);
		user1.setUserContact(contact1);	

		user1.save();
		
		//Data 2
		User user2=new User("gunadarma@gmail.com", "password", "Sayuti", "Hidayat", role3);
		user2.setCity("Jayapandang");
		user2.setCountry("Indonesia");
		user2.setCompany("Sunaya Basah Sejahtera");
		user2.setActive(true);
		
		ArrayList<UserContact> contact2=new ArrayList<UserContact>();
		UserContact kontak2_item1=new UserContact("(021)2132999",ContactTypeEnum.HOME_PHONE,"Hubungi nomor ini jika istri saya tidak dirumah");
		UserContact kontak2_item2=new UserContact("Jalan Sudarmanto nomor 21", ContactTypeEnum.ADDRESS, "Ini websitenya");
		UserContact kontak2_item3=new UserContact("116090010010-0 \n A.N Sayuti Hidayat \n  Bank Banyumas Jabang Jawa Barat", ContactTypeEnum.BANK_ACCOUNT, "Nomor rekening saya");
		contact2.add(kontak2_item1);
		contact2.add(kontak2_item2);
		contact2.add(kontak2_item3);
		user2.setUserContact(contact2);	
		user2.save();
		
		//Data 3
		User user3=new User("sonata@gmail.com", "password", "Hutamala", "Hidayat", role2);
		user3.setCity("Jakakerto");
		user3.setCountry("Indonesia");
		user3.setCompany("Teknimo");
		user3.setActive(true);
		
		ArrayList<UserContact> contact3=new ArrayList<UserContact>();
		UserContact kontak3_item1=new UserContact("911",ContactTypeEnum.OTHER,"Hanya hubungi ketika darurat");
		UserContact kontak3_item2=new UserContact("http://www.teknimo.com", ContactTypeEnum.COMPANY_WEBSITE, "Ini websitenya");
		contact3.add(kontak3_item1);
		contact3.add(kontak3_item2);
		user3.setUserContact(contact3);	
		user3.save();
		
		DepositoForm form1=new DepositoForm();
			form1.amount="500000";
			form1.description="Pembayaran Awal untuk campaign aximtel";
			form1.idUser=(user2.getId_user()+"");
			form1.paymentMethod=PaymentMethodEnum.TRANSFER.name();
		DepositoForm form2=new DepositoForm();
			form2.amount="2000000";
			form2.description="Pembayaran untuk kekurangan di pembayaran sebelumnya";
			form2.idUser=(user2.getId_user()+"");
			form2.paymentMethod=PaymentMethodEnum.TRANSFER.name();
		DepositoForm form3=new DepositoForm();
			form3.amount="40000000";
			form3.description="Pembayaran Akhir";
			form3.idUser=(user2.getId_user()+"");
			form3.paymentMethod=PaymentMethodEnum.TRANSFER.name();
		
		newDeposito(form1);
		newDeposito(form2);
		newDeposito(form3);
	}
	public Deposito newDeposito(DepositoForm form){
		Ebean.beginTransaction();
		Deposito deposito=null;
		try {
			deposito=new Deposito();
			User user=User.find.byId(Integer.parseInt(form.idUser));
			int current_balance=user.getCurrent_balance();
			int amount=Integer.parseInt(form.amount);
			
			deposito.setAmount(amount);
			deposito.setCurrent_balance(current_balance+amount);
			deposito.setDescription(form.description);
			deposito.setTimestamp(new Date());
			deposito.setPayment_method(PaymentMethodEnum.valueOf(form.paymentMethod));
			deposito.setUser(user);
			deposito.save();
			
			user.setCurrent_balance(current_balance+amount);
			user.update();
			
			NotifItem notifItem=new NotifItem();
			notifItem.setParam(new String[]{amount+"",deposito.getId_deposito()+""});
			notifItem.setType(NotificationType.NEW_DEPOSITO);
			notifItem.setUser(user);
			notif.pushNew(notifItem);
			
			Ebean.commitTransaction();
			return deposito;
		} catch (NumberFormatException e) {
			Ebean.rollbackTransaction();
			e.printStackTrace();
		}catch(Exception e){
			Ebean.rollbackTransaction();
		}finally{
			Ebean.endTransaction();
		}
		return null;		
	}	
	public void setBannerSize(){

		//udah ada apa ngga dulu
		List<BannerSize> data=BannerSize.find.all();
		
		if(data.size()==0){
			data.add(new BannerSize("Medium Rectangle", 300, 250, "Ini ukuran Sedang"));
			data.add(new BannerSize("Rectangle", 180, 150, "Ini ukuran kecil"));
			data.add(new BannerSize("LeaderBoard", 728, 90, "Ini ukuran panjang"));
			data.add(new BannerSize("Wide Skyscrapper", 160, 600, "Ini ukuran tinggi"));
			Ebean.save(data);			
		}
		
		//Data 1

		
	}
	public void setZoneChannel(){
		List<ZoneChannel> channels = ZoneChannel.find.all();
		List<Zone> zones=Zone.find.all();
		if(channels.size()==0){
				deleteZoneChannel();
				
				ZoneChannel channel1=new ZoneChannel("General", "Diperuntukan untuk zona yang memiliki tema general");
				ZoneChannel channel2=new ZoneChannel("Gadget", "Diperuntukan untuk zona yang memiliki tema gadget");
				ZoneChannel channel3=new ZoneChannel("Software", "Diperuntukan untuk zona yang memiliki tema software");
				ZoneChannel channel4=new ZoneChannel("Internet", "Diperuntukan untuk zona yang memiliki tema internet");
				
				channel1.save();
				channel2.save();
				channel3.save();
				
				List<BannerSize> banners=BannerSize.find.all();
				Zone zone1=new Zone("Header Wide", 
									"Banner Untuk ditampilkan di area header", 
									channel1, 
									banners.get(3), 
									ZoneTypeEnum.BANNER, 
									DefaultViewEnum.EMPTY, 
									"Kosong");
				zone1.save();
				Zone zone2=new Zone("Sidebar Kecil", 
						"Banner kecil Untuk ditampilkan di sidebar kanan", 
						channel1, 
						banners.get(1), 
						ZoneTypeEnum.BANNER, 
						DefaultViewEnum.DEFAULT_CODE, 
						"Kosong");
				zone2.save();
				Zone zone3=new Zone("Sidebar Medium", 
						"Banner Medium Rectangle Untuk ditampilkan di sidebar kanan", 
						channel1, 
						banners.get(0), 
						ZoneTypeEnum.BANNER, 
						DefaultViewEnum.DEFAULT_CODE, 
						"<h1>ups tidak ada iklan </h1>");
				zone3.save();
				
				Zone zone4=new Zone("Sidebar medium gadget", 
						"Banner Untuk ditampilkan di sidebar kanan dengan channel gadget", 
						channel2, 
						banners.get(0), 
						ZoneTypeEnum.BANNER, 
						DefaultViewEnum.DEFAULT_ADS, 
						"Kosong");
				zone4.save();
				
				Zone zone5=new Zone("Banner Konten", 
						"Banner kecil Untuk ditampilkan di area konten", 
						channel2, 
						banners.get(1), 
						ZoneTypeEnum.BANNER, 
						DefaultViewEnum.DEFAULT_CODE, 
						"<h1>Kosong</h1>");
				zone5.save();
				Zone zone6=new Zone("Leader Board teks", 
						"Banner Untuk ukuran besar berisi teksr", 
						channel1, 
						banners.get(2), 
						ZoneTypeEnum.TEXT, 
						DefaultViewEnum.EMPTY, 
						"Kosong");
				zone6.save();
				Zone zone7=new Zone("Banner Medium Software", 
						"Banner untuk ukuran medium untuk tema software", 
						channel3, 
						banners.get(0), 
						ZoneTypeEnum.BANNER, 
						DefaultViewEnum.DEFAULT_ADS, 
						"Kosong");
				zone7.save();
				Zone zone8=new Zone("Banner kecil untuk tema internet", 
						"Banner kecil untuk tema internet", 
						channel4, 
						banners.get(1), 
						ZoneTypeEnum.BANNER, 
						DefaultViewEnum.EMPTY, 
						"Kosong");
				zone8.save();
				
				Zone zone9=new Zone("Banner Medium rectangle untuk teks", 
						"Banner kecil untuk tema internet", 
						channel4, 
						banners.get(0), 
						ZoneTypeEnum.TEXT, 
						DefaultViewEnum.EMPTY, 
						"Kosong");
				zone9.save();
				Zone zone10=new Zone("Banner Leader Board untuk Banner", 
						"Banner leaderboard untuk tema internet", 
						channel4, 
						banners.get(2), 
						ZoneTypeEnum.BANNER, 
						DefaultViewEnum.EMPTY, 
						"Kosong");
				zone10.save();
		}
		

	}
	public void setCampaignData(){
		
		User user=User.find.where().eq("role", 
										UserRole.find.where().eq("name", "advertiser").findUnique()).findUnique();
		//Campaign Tipe kontrak CPA
		Campaign campaign=new Campaign();
		campaign.setCampaignName("Pemasaran iPhun 8S");
		campaign.setId_user(user);
		campaign.setActivated(true);
		campaign.setBid_price(2000);
		campaign.setLimit_click(1000);
		campaign.setCampaign_type(CampaignTypeEnum.CONTRACT);
		campaign.setPricing_model(PricingModelEnum.CPA);
		campaign.setDescription("Campaign untuk memasarkan lini baru smartphone iphun yang canggih");

		campaign.setStart_date(new Date());
		
		DateTime endDate3=new DateTime();
		endDate3=endDate3.plusMonths(50);
		campaign.setEnd_date(endDate3.toDate());
		campaign.save();
		
		
		//Campaign Tipe kontrak CPM
		Campaign campaign2=new Campaign();
		campaign2.setCampaignName("Pemasaran Casing Ponsel Tangguh");
		campaign2.setId_user(user);
		campaign2.setActivated(true);
		campaign2.setBid_price(2000);
		campaign2.setStart_date(new Date());
		
		DateTime endDate2=new DateTime();
		endDate2=endDate2.plusMonths(19);
		campaign2.setEnd_date(endDate2.toDate());		
		campaign2.setLimit_impression(20000);
		campaign2.setCampaign_type(CampaignTypeEnum.CONTRACT);
		campaign2.setPricing_model(PricingModelEnum.CPM);
		campaign2.setDescription("Campaign ini ditujukan untuk memasarkan" +
				" casing baru yang bisa tahan dari serangan bom atom");
		campaign2.save();
			
		//Campaign Tipe eklusif
		Campaign campaign3=new Campaign();
		campaign3.setCampaignName("Pemasaran Layanan Baru Operator Aximtel");
		campaign3.setId_user(user);
		campaign3.setActivated(true);
		campaign3.setStart_date(new Date());
		
		DateTime endDate=new DateTime();
		endDate=endDate.plusMonths(5);
		campaign3.setEnd_date(endDate.toDate());
		
		campaign3.setBid_price(8000);
		campaign3.setCampaign_type(CampaignTypeEnum.EXCLUSIVE);
		campaign3.setPricing_model(PricingModelEnum.FLAT);
		campaign3.setDescription("Memasarkan Layanan Aximtel, jaringan paling reliable hingga diseluruh samudra hindia");
		campaign3.save();
			
	}
	public void createBanner(){
		
		Campaign campaign=Campaign.find.where().eq("campaignName", "Pemasaran Casing Ponsel Tangguh").findUnique();
		
		BannerSize size=BannerSize.find.where().eq("name","Medium Rectangle").findUnique();
		
		//bikin fileupload
		
		FileUpload upload=new FileUpload();
		upload.setId(1);
		upload.setPath("/public/upload/ads/");
		upload.setUrl_path("/public/upload/ads/");
		upload.setName("tridentCase.jpg");
		upload.save();

		Banner banner=new Banner();
		banner.setTitle("Bom Atom? Lewat Boy...");
		banner.setContent_text("Casing baru ini bisa melindungi ponsel dari bom atom, dijamin tahan");
		banner.setTarget("http://casingatom.com");
		banner.setAlt_text("Casing Model 101, Tahan luar biasa mantap");
		banner.setWeight(3);
		banner.setActive(true);
		banner.setName("Banner Casing Model 1");
		banner.setDescription("Berisi gambar untuk casing model 1");
		banner.setBannerType(ZoneTypeEnum.TEXT);
		banner.setCampaign(campaign);
		banner.setContent_link(upload);
		banner.setBannerSize(size);
		banner.save();
		
		//bikin fileupload
		
		FileUpload upload2=new FileUpload();
		upload2.setId(21);
		upload2.setPath("/public/upload/ads/");
		upload2.setUrl_path("/public/upload/ads/");
		upload2.setName("tridentCase.jpg");
		upload2.save();

		Banner banner2=new Banner();
		banner2.setTitle("Bom Atom? Sungguh terlalu..");
		banner2.setContent_text("Mau tau lebih jauh soal casing ini? Klik iklan ini doong");
		banner2.setTarget("http://casingatom.com");
		banner2.setAlt_text("Casing Ini bisa bertahan dari ancaman bom atom dan " +
				"segala macamnya...bahkan ketika tubuh pemiliknya menguap, casing ini bisa tetap bertahan...wow");
		banner2.setWeight(0);
		banner2.setActive(true);
		banner2.setName("Banner Casing Model 2");
		banner2.setDescription("Menampung gambar dengan ukuran lebih yahud, ow");
		banner2.setBannerType(ZoneTypeEnum.BANNER);
		banner2.setCampaign(campaign);
		banner2.setContent_link(upload2);
		banner2.setBannerSize(size);
		banner2.save();	
		
		//====Untuk banner ketiga
		
		Campaign campaign2=Campaign.find.where().eq("campaignName", "Pemasaran iPhun 8S").findUnique();
		BannerSize wideSkyscrapper=BannerSize.find.where().eq("name","Wide Skyscrapper").findUnique();
	
		FileUpload upload3=new FileUpload();
		upload3.setId(41);
		upload3.setPath("/public/upload/ads/");
		upload3.setUrl_path("/public/upload/ads/");
		upload3.setName("btWalking.swf");
		upload3.save();

		Banner banner3=new Banner();
		banner3.setTitle("Lihatlah kecanggihan IPhun ");
		banner3.setContent_text("Lihatlah kecanggihan iPhun 8s yang dilengkapi dengan fitur canggih");
		banner3.setTarget("http://www.iphun.com");
		banner3.setAlt_text("Lihatlah kecanggihan iPhun 8s yang " +
							"dilengkapi dengan fitur canggih, seperti kamera luar biasa bening, " +
							"dan layar luar biasa tipis..wuahaha");
		banner3.setWeight(0);
		banner3.setActive(true);
		banner3.setName("Iphun 8S Banner 1");
		banner3.setDescription("Banner ini berisi demo iphun 8s yang baru");
		banner3.setBannerType(ZoneTypeEnum.BANNER);
		banner3.setCampaign(campaign2);
		banner3.setContent_link(upload3);
		banner3.setBannerSize(wideSkyscrapper);
		banner3.save();	
		
		BannerSize leaderboard=BannerSize.find.where().eq("name","LeaderBoard").findUnique();
		
		FileUpload upload4=new FileUpload();
		upload4.setId(61);
		upload4.setPath("/public/upload/ads/");
		upload4.setUrl_path("/public/upload/ads/");
		upload4.setName("ads.jpg");
		upload4.save();

		Banner banner4=new Banner();
		banner4.setTitle("iPhun 8S Sungguh Menawan ");
		banner4.setContent_text("Lihatlah kecanggihan dan kemulusan perangkat baru ini....");
		banner4.setTarget("http://www.iphun.com");
		banner4.setAlt_text("iphun 8s");
		banner4.setWeight(0);
		banner4.setActive(true);
		banner4.setName("Iphun 8S Banner 2");
		banner4.setDescription("Banner ini untuk ukuran leaderboard, yang static, dan bukan flash");
		banner4.setBannerType(ZoneTypeEnum.BANNER);
		banner4.setCampaign(campaign2);
		banner4.setContent_link(upload4);
		banner4.setBannerSize(leaderboard);
		banner4.save();					
		
		
		//===============Untuk banner campaign ketiga ...huah akhirnya
		BannerSize rectangle=BannerSize.find.where().eq("name","Rectangle").findUnique();

		Campaign campaign3=Campaign.find.where().eq("campaignName", "Pemasaran Layanan Baru Operator Aximtel").findUnique();
	
		FileUpload upload5=new FileUpload();
		upload5.setId(81);
		upload5.setPath("/public/upload/ads/");
		upload5.setUrl_path("/public/upload/ads/");
		upload5.setName("ocean.png");
		upload5.save();

		Banner banner5=new Banner();
		banner5.setTitle("Operator Paling Oke");
		banner5.setContent_text("Nikmati kecanggihan layanan hingga ke samudera hindia paling ujung......");
		banner5.setTarget("http://www.aximtel.com");
		banner5.setAlt_text("Aximtel adalah operator seluler nomor 1 di samudra hindia");
		banner5.setWeight(0);
		banner5.setActive(true);
		banner5.setName("Operator Aximtel Layanan 1");
		banner5.setDescription("Ini adalah banner teks untuk layanan 1");
		banner5.setBannerType(ZoneTypeEnum.BANNER);
		banner5.setCampaign(campaign3);
		banner5.setContent_link(upload5);
		banner5.setBannerSize(rectangle);
		banner5.save();	
		
		BannerSize medium=BannerSize.find.where().eq("name","Medium Rectangle").findUnique();
		
		FileUpload upload6=new FileUpload();
		upload6.setId(101);
		upload6.setPath("/public/upload/ads/");
		upload6.setUrl_path("/public/upload/ads/");
		upload6.setName("antenna.jpg");
		upload6.save();

		Banner banner6=new Banner();
		banner6.setTitle("Sinyal Kuat Di penjuru Bumi");
		banner6.setContent_text("Dimanapun anda berada, termasuk dari dasar laut, sinyal tetap kuat");
		banner6.setTarget("http://www.aximtel.com");
		banner6.setAlt_text("Ini adalah gambar antenna bumi yang kami gunakan untuk menjangkau dasar laut ");
		banner6.setWeight(3);
		banner6.setActive(true);
		banner6.setName("Operator Aximtel Layanan 2");
		banner6.setDescription("Banner ini untuk ukuran leaderboard, yang static, dan bukan flash");
		banner6.setBannerType(ZoneTypeEnum.BANNER);
		banner6.setCampaign(campaign3);
		banner6.setContent_link(upload6);
		banner6.setBannerSize(medium);
		banner6.save();	
		
		//Untuk banner 2
		FileUpload upload7=new FileUpload();
		upload7.setId(121);
		upload7.setPath("/public/upload/ads/");
		upload7.setUrl_path("/public/upload/ads/");
		upload7.setName("timthumb.jpg");
		upload7.save();

		Banner banner7=new Banner();
		banner7.setTitle("Smartphone Anda Canggih?");
		banner7.setContent_text("Pasti masih kalah canggih dibanding IPhun 8s, pingin tau alasannya?");
		banner7.setTarget("http://www.iphun.com");
		banner7.setAlt_text("Iphun 8s, tercanggih");
		banner7.setWeight(3);
		banner7.setActive(true);
		banner7.setName("Banner Teks IPhun 8");
		banner7.setDescription("Ditujukan untuk mendukung kampanye banner teks iphun");
		banner7.setBannerType(ZoneTypeEnum.TEXT);
		banner7.setCampaign(campaign2);
		banner7.setContent_link(upload7);
		banner7.setBannerSize(medium);
		banner7.save();	
		
		saveBannerPlacement(banner5);
		saveBannerPlacement(banner);
		saveBannerPlacement(banner2);
		saveBannerPlacement(banner3);
		saveBannerPlacement(banner4);
		saveBannerPlacement(banner7);
	}
	public void simulasi(){
		//simulasi impresi & klik
		List<Zone> zone=Zone.find.all();
		Random random=new Random();
		int zoneCount=zone.size()-1;
		FlatProcessor processor=new FlatProcessor();
		ImpressionpROC impressionROC=new ImpressionpROC(processor);
		Click click=new Click();
		AdDeliver deliver=new AdDeliver(impressionROC);
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DATE, -90);
		for(int i=0;i<10000;i++){
			
			List<BannerPlacement> result=selector.get(zone.get(random.nextInt(zoneCount)));
			List<Impression> impression=new ArrayList<Impression>();
			List<Banner> banner=new ArrayList<Banner>();
			if(result==null){
			
			}else{
				int x=0;
				for(BannerPlacement bannerPlacement:result){
					impression.add(deliver.countImpression(bannerPlacement, "Simulated",  calendar.getTime()));
					banner.add(bannerPlacement.getBanner());
				}
			}
			
			//hitung klik secara random
			for(Impression impresi:impression){
				if(random.nextInt(30)==1){
					click.click(impresi, calendar.getTime());
				}				
			}
			if(i%500==0){
				Logger.debug("Proses pengisian data " +(((double)i/10000))*100+"%");
			}
			calendar.set(Calendar.MINUTE, new Random().nextInt(10));
			if(i%200==0){
				calendar.set(Calendar.HOUR, new Random().nextInt(54));				
			}
			//ubah variabel
		}
	}
	public boolean saveBannerPlacement(Banner banner){
		List<Zone> available=bannerproc.getZoneAvailable(banner.getId_banner());
		
		try {
			System.out.println(available.toString());
			for(Zone zone:available){
				BannerPlacement placement=new BannerPlacement();
				placement.setBanner(banner);
				placement.setZone(zone);
				placement.save();
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}	
	public void deleteCampaignData(){
		List<BannerPlacement> places=BannerPlacement.find.all();
		List<Banner>  		  banner=Banner.find.all();
		List<Campaign> 		  campaign=Campaign.find.all();
		
		if(places.size()!=0){
			Ebean.delete(places);
		}
		
		if(banner.size()!=0){
			Ebean.delete(banner);
		}
		if(campaign.size()!=0){
			Ebean.delete(campaign);
		}
	}
	public void deleteUserData(){
		
		List<User> data=User.find.all();
		List<UserRole> roles=UserRole.find.all();
		List<FileUpload> uploads=FileUpload.find.all();
		Ebean.delete(data);
		Ebean.delete(roles);
		for(FileUpload upload:uploads){
			upload.deleteDbOnly();
		}
		System.out.println("Delete data");
	}
	public void deleteNotification(){
		List<Notification> notifs=Notification.find.all();
		for(Notification notif:notifs){
			notif.delete();
		}
	}
	public void deleteTransactionData(){
		List<BannerAction> actions=BannerAction.find.all();
		List<Impression> impressions=Impression.find.all();
		for(BannerAction action:actions){
			action.delete();
		}
		for(Impression impresi:impressions){
			impresi.delete();
		}
		List<AdsTransaction> transactions = AdsTransaction.find.all();
		for(AdsTransaction transaction:transactions){
			transaction.delete();
		}
	}
	public void deleteFinanceData(){
		List<TransferConfirmation> transfers =TransferConfirmation.find.all();
		List<Deposito> depositos = Deposito.find.all();
		for(TransferConfirmation transfer:transfers){
			transfer.delete();
		}
		for(Deposito deposito:depositos){
			deposito.delete();
		}
		
	}
	public void deleteZoneChannel(){
		List<ZoneChannel> channels = ZoneChannel.find.all();
		List<Zone> zones=Zone.find.all();

		for(Zone zone:zones){
			zone.delete();
		}
		
		for(ZoneChannel channel:channels){
			channel.delete();
		}
	}
	
	
	
	
	

}

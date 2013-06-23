package models.custom_helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;

import play.db.DB;

import com.avaje.ebean.Ebean;

import models.data.Banner;
import models.data.BannerSize;
import models.data.Campaign;
import models.data.FileUpload;
import models.data.User;
import models.data.UserContact;
import models.data.UserRole;
import models.data.Zone;
import models.data.ZoneChannel;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.DefaultViewEnum;
import models.data.enumeration.PricingModelEnum;
import models.data.enumeration.RoleEnum;
import models.data.enumeration.ZoneTypeEnum;

/*Set Initial Data, 
 * 
 * Buat ngisi data dalam rangka testing
 */
public class SetInitialData {

	public SetInitialData(){
		//Konstruktor
	}
	
	//Data user + data kontak
	public void setDataUser(){
		
		
		//Clean Data dulu		
		deleteCampaignData();

		deleteUserData();
		try {
			DB.getConnection().createStatement().execute("ALTER SEQUENCE user_seq RESTART WITH 1;");
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
		contact2.add(kontak2_item1);
		contact2.add(kontak2_item2);
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
				
		}
		

	}
	public void setCampaignData(){
		
		User user=User.find.where().eq("role", 
										UserRole.find.where().eq("name", "advertiser").findUnique()).findUnique();
		//Campaign Tipe kontrak CPA
		Campaign campaign=new Campaign();
		campaign.setCampaignName("Tipe kontrak CPA");
		campaign.setId_user(user);
		campaign.setActivated(true);
		campaign.setBid_price(2000);
		campaign.setLimit_click(1000);
		campaign.setCampaign_type(CampaignTypeEnum.CONTRACT);
		campaign.setPricing_model(PricingModelEnum.CPA);
		campaign.setDescription("Campaign Dengan tipe kontrak CPA");
		campaign.save();
		
		
		//Campaign Tipe kontrak CPM
		Campaign campaign2=new Campaign();
		campaign2.setCampaignName("Tipe kontrak CPM");
		campaign2.setId_user(user);
		campaign2.setActivated(true);
		campaign2.setBid_price(2000);
		campaign2.setLimit_impression(20000);
		campaign2.setCampaign_type(CampaignTypeEnum.CONTRACT);
		campaign2.setPricing_model(PricingModelEnum.CPM);
		campaign2.setDescription("Campaign Dengan tipe kontrak CPM");
		campaign2.save();
			
		//Campaign Tipe eklusif
		Campaign campaign3=new Campaign();
		campaign3.setCampaignName("Tipe Eklusif");
		campaign3.setId_user(user);
		campaign3.setActivated(true);
		campaign3.setStart_date(new Date());
		
		DateTime endDate=new DateTime();
		endDate=endDate.plusMonths(12);
		campaign3.setEnd_date(endDate.toDate());
		
		campaign3.setBid_price(8000);
		campaign3.setCampaign_type(CampaignTypeEnum.EXCLUSIVE);
		campaign3.setPricing_model(PricingModelEnum.FLAT);
		campaign3.setDescription("Campaign Dengan tipe eklusif");
		campaign3.save();
			
	}
	public void deleteCampaignData(){
		List<Banner>   banner=Banner.find.all();
		List<Campaign> campaign=Campaign.find.all();
		
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
		
		System.out.println("Delete data");
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

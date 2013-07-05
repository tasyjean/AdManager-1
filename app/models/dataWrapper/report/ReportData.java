package models.dataWrapper.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import play.Logger;

import models.data.Campaign;
import models.data.User;
import models.data.UserRole;
import models.data.enumeration.RoleEnum;

public class ReportData {

	private List<User> userList;
	private List<BannerList> bannerList;
	private List<Campaign> campaignList;
	private User selectedUser;
	private DiagramData diagramData;
	private Date to;
	private Date from;
	SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
	public ReportData(){
		setUserList();
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList() {
		UserRole advertiser_role=UserRole.find.where().eq("name", RoleEnum.ADVERTISER).findUnique();
		userList=User.find.where().eq("role", advertiser_role).findList();
		Logger.debug("Set User Liiist");
	}
	public List<BannerList> getBannerList() {
		return bannerList;
	}
	public void setBannerList(List<BannerList> bannerList) {
		this.bannerList = bannerList;
	}
	public List<Campaign> getCampaignList() {
		return campaignList;
	}
	public void setCampaignList(List<Campaign> campaigns) {
		this.campaignList = campaigns;
	}
	public User getSelectedUser() {
		return selectedUser;
	}
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	public DiagramData getDiagramData() {
		return diagramData;
	}
	public void setDiagramData(DiagramData diagramData) {
		this.diagramData = diagramData;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public String getFrom_formatted(){
		return format.format(from);
	}	
	public String getTo_formatted(){
		return format.format(to);
	}

	
	
	
	
}

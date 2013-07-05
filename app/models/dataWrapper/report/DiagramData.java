package models.dataWrapper.report;

import java.util.List;

import models.data.Campaign;

public class DiagramData {

	
	private Campaign campaign;
	private String text;
	private String subtext;
	private List<String> categories;
	private List<Integer> impresi;
	private List<Integer> click;
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<Integer> getImpresi() {
		return impresi;
	}
	public void setImpresi(List<Integer> impresi) {
		this.impresi = impresi;
	}
	public List<Integer> getClick() {
		return click;
	}
	public void setClick(List<Integer> click) {
		this.click = click;
	}


	

}

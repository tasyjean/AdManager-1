package models.dataWrapper.report;

import models.data.Campaign;

public class DiagramData {

	
	private Campaign campaign;
	private String text;
	private String subtext;
	private String[] categories;
	private int[] click;
	private int[] impresi;
	
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
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public int[] getClick() {
		return click;
	}
	public void setClick(int[] click) {
		this.click = click;
	}
	public int[] getImpresi() {
		return impresi;
	}
	public void setImpresi(int[] impresi) {
		this.impresi = impresi;
	}
}

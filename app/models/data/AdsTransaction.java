package models.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.custom_helper.Angka;
import models.data.enumeration.PricingModelEnum;

import play.db.ebean.Model;

@Entity
@Table(name="ads_transaction")
public class AdsTransaction extends Model {

	@Id
	private int id_ads_transaction;
	@ManyToOne
	private BannerPlacement bannerPlacement;
	private PricingModelEnum transaction_type;
	private int amount;
	private int current_balance;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	public static Model.Finder<Integer,AdsTransaction> find = new Model.Finder(Integer.class, AdsTransaction.class);

	
	public  String getTimestamp_formatted(){
		SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
		return format.format(timestamp);
	}	
	public int getId_ads_transaction() {
		return id_ads_transaction;
	}

	public void setId_ads_transaction(int id_ads_transaction) {
		this.id_ads_transaction = id_ads_transaction;
	}
	
	public String getCurrent_balance_rupiah(){
		return Angka.toRupiah(current_balance);
	}
	public String getAmount_rupiah(){
		return Angka.toRupiah(amount);
	}

	public PricingModelEnum getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(PricingModelEnum transaction_type) {
		this.transaction_type = transaction_type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCurrent_balance() {
		return current_balance;
	}

	public void setCurrent_balance(int current_balance) {
		this.current_balance = current_balance;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



	public static Model.Finder<Integer, AdsTransaction> getFind() {
		return find;
	}

	public static void setFind(Model.Finder<Integer, AdsTransaction> find) {
		AdsTransaction.find = find;
	}

	public BannerPlacement getBannerPlacement() {
		return bannerPlacement;
	}

	public void setBannerPlacement(BannerPlacement bannerPlacement) {
		this.bannerPlacement = bannerPlacement;
	}
	
	
}

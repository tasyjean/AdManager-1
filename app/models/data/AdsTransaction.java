package models.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.data.enumeration.PricingModelEnum;

import play.db.ebean.Model;

@Entity
@Table(name="ads_transaction")
public class AdsTransaction extends Model {

	@Id
	private int id_ads_transaction;
	@ManyToOne
	private Banner banner;
	private PricingModelEnum transaction_type;
	private int amount;
	private int current_balance;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private boolean isDeleted;
	
	public static Model.Finder<Integer,AdsTransaction> find = new Model.Finder(Integer.class, AdsTransaction.class);

	
	
	public int getId_ads_transaction() {
		return id_ads_transaction;
	}

	public void setId_ads_transaction(int id_ads_transaction) {
		this.id_ads_transaction = id_ads_transaction;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static Model.Finder<Integer, AdsTransaction> getFind() {
		return find;
	}

	public static void setFind(Model.Finder<Integer, AdsTransaction> find) {
		AdsTransaction.find = find;
	}
	
	
}

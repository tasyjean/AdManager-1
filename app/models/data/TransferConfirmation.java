package models.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.custom_helper.Angka;
import models.data.enumeration.PaymentMethodEnum;
import play.db.ebean.Model;

@Entity
@Table(name="transfer_confirmation")
public class TransferConfirmation  extends Model {
	
	//diperuntukan untuk bukti transfer bank

	@Id
	private int id_transferConfirmation;
	@ManyToOne
	private User user;
	@ManyToOne
	private User user_validator;
	private int amount;
	@Column(columnDefinition="TEXT")
	private String description;
	@Column(columnDefinition="TEXT")
	private String manager_message;
	private Date transfer_date;
	private String senderBankAccount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp_created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp_validated;
	private boolean isValidated;
	private boolean isDeleted;
	
	public static Model.Finder<Integer,TransferConfirmation> find = new Model.Finder(Integer.class, TransferConfirmation.class);

	public  String getTimestamp_created_formatted(){
		SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
		return format.format(timestamp_created);
	}
	public  String getTransfer_date_formatted(){
		SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy hh:mm");
		return format.format(transfer_date);
	}
	public  String getTimestamp_validated_formatted(){
		SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy hh:mm");
		return format.format(timestamp_validated);
	}	
	public int getId_transferConfirmation() {
		return id_transferConfirmation;
	}

	public void setId_transferConfirmation(int id_transferConfirmation) {
		this.id_transferConfirmation = id_transferConfirmation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser_validator() {
		return user_validator;
	}

	public void setUser_validator(User user_validator) {
		this.user_validator = user_validator;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransfer_date() {
		return transfer_date;
	}

	public void setTransfer_date(Date transfer_date) {
		this.transfer_date = transfer_date;
	}

	public String getSenderBankAccount() {
		return senderBankAccount;
	}

	public void setSenderBankAccount(String senderBankAccount) {
		this.senderBankAccount = senderBankAccount;
	}

	public Date getTimestamp_created() {
		return timestamp_created;
	}

	public void setTimestamp_created(Date timestamp_created) {
		this.timestamp_created = timestamp_created;
	}

	public Date getTimestamp_validated() {
		return timestamp_validated;
	}

	public void setTimestamp_validated(Date timestamp_validated) {
		this.timestamp_validated = timestamp_validated;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getManager_message() {
		return manager_message;
	}

	public void setManager_message(String manager_message) {
		this.manager_message = manager_message;
	}
	public String getAmount_rupiah(){
		return Angka.toRupiah(amount);
	}
	
}

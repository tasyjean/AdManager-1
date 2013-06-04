package models.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.ebean.Model;

@Entity
@Table(name="deposito")

public class Deposito extends Model {

	@Id
	private int id_deposito;
	@ManyToOne
	private User user;
	@ManyToOne
	private User user_validator;
	private int current_balance;
	private int amount;
	@Column(columnDefinition="TEXT")
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp_created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp_validated;
	@Column(length=400)
	private String transfer_evidence;
	private String payment_method;
	private boolean isValidated;
	private boolean isDeleted;
	
	public static Model.Finder<Integer,Deposito> find = new Model.Finder(Integer.class, Deposito.class);

	public int getId_deposito() {
		return id_deposito;
	}
	public void setId_deposito(int id_deposito) {
		this.id_deposito = id_deposito;
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
	public int getCurrent_balance() {
		return current_balance;
	}
	public void setCurrent_balance(int current_balance) {
		this.current_balance = current_balance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getTransfer_evidence() {
		return transfer_evidence;
	}
	public void setTransfer_evidence(String transfer_evidence) {
		this.transfer_evidence = transfer_evidence;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	
}

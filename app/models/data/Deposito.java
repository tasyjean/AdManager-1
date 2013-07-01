package models.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.data.enumeration.PaymentMethodEnum;

import play.db.ebean.Model;

@Entity
@Table(name="deposito")

public class Deposito extends Model {

	@Id
	private int id_deposito;
	@ManyToOne
	private User user;
	private int current_balance;
	private int amount;
	@Column(columnDefinition="TEXT")
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	@Column(length=400)
	private PaymentMethodEnum payment_method;
	
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public PaymentMethodEnum getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(PaymentMethodEnum payment_method) {
		this.payment_method = payment_method;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}

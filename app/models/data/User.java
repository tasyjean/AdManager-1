package models.data;

import java.sql.Timestamp;
import java.util.*;
import javax.persistence.*;
import javax.validation.Constraint;

import org.jboss.logging.FormatWith;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Table(name="adsmanager.user")
@Entity
public class User extends Model {
	
	//identifikasi field
	@Id
	public int id_user;

	@Constraints.Required
	public String email;
	
	@Constraints.Required
	public String password;
	public String front_name;
	public String last_name;
	public String company;
	public String role;
	
	@Temporal(TemporalType.DATE)
	public Date join_date;
	public int current_balance;
	public boolean isActive;
	public String profile_photo;
	public String city;
	public String country;
	public String validation_key;

	public static Model.Finder<Long,User> find = new Model.Finder(Long.class, User.class);

	
	public int getId_user() {
		return id_user;
	}



	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getFront_name() {
		return front_name;
	}

	public void setFront_name(String front_name) {
		this.front_name = front_name;
	}



	public String getLast_name() {
		return last_name;
	}



	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public Date getJoin_date() {
		return join_date;
	}



	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}



	public int getCurrent_balance() {
		return current_balance;
	}



	public void setCurrent_balance(int current_balance) {
		this.current_balance = current_balance;
	}



	public boolean isActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}



	public String getProfile_photo() {
		return profile_photo;
	}



	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getValidation_key() {
		return validation_key;
	}



	public void setValidation_key(String validation_key) {
		this.validation_key = validation_key;
	}



	public void test(){
	
	}
	
}

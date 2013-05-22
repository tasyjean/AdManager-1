package models.data;

import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;
import javax.validation.Constraint;

import models.custom_helper.MD5;
import models.data.enumeration.RoleEnum;

import org.jboss.logging.FormatWith;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity
@Table(schema="adsmanager",name="user")
public class User extends Model {
	
	//identifikasi field
	@Id
	private int id_user;

	@Constraints.Required
	private String email;
	
	@Constraints.Required
	private String password;
	private String front_name;
	private String last_name;
	private String company;
	private RoleEnum role;
	
	@Temporal(TemporalType.DATE)
	private Date join_date;

	private int current_balance;
	private boolean isActive;
	private String profile_photo;
	private String city;
	private String country;
	private String validation_key;
	@OneToMany(cascade=CascadeType.ALL)
	private List<UserContact> userContact; 
	
	public static Model.Finder<Integer,User> find = new Model.Finder(Integer.class, User.class);

	public User(){
		
	}
	public User(String email, String password, String front_name,
			String last_name, RoleEnum role) {
		super();
		setEmail(email);
		setPassword(password);
		setFront_name(front_name);
		setLast_name(last_name);
		setRole(role);
	}
	//Override save
	@Override
	public void save(){
		join_date = Calendar.getInstance().getTime();
		current_balance =0;
		if(validation_key!=null){}else{
			Random x=new Random(); 
			validation_key=MD5.get().md5(String.valueOf(x.nextInt()));
		}
		super.save();
	}
	//Getter Setter
	//Set contact
	public void setUserContact(Collection<UserContact> userContact){
        final List<UserContact> clone = new ArrayList<UserContact>(this.userContact);
        //delete yang udah ada
        for(UserContact x:clone){
        	getUserContact().remove(x);
        	x.setId_user(null);
        }
        for(UserContact x:userContact){
        	getUserContact().add(x);
        	x.setId_user(this);
        }
	}
	//Mendapatkan daftar kontak user
	public List<UserContact> getUserContact(){
	    if (userContact == null) {
            userContact = new ArrayList<UserContact>();
        }
        return userContact;
    }
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
		this.password = MD5.get().md5(password);
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
	public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
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

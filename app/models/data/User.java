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

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

import com.avaje.ebean.*;

@Entity
@Table(name="user_data")
public class User extends Model implements Subject {
	
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
	@ManyToOne(cascade=CascadeType.ALL)
	private UserRole role;
	
	@Temporal(TemporalType.DATE)
	private Date join_date;
	private int current_balance;
	private boolean isActive;
	@ManyToOne
	private FileUpload profile_photo;
	private String city;
	private String country;
	private String validation_key;
	@OneToMany(cascade=CascadeType.ALL)
	private List<UserContact> userContact; 
    @ManyToMany(cascade=CascadeType.ALL)
	public List<UserPermission> permissions;

	//Blok untuk parameter subyek ---------------------
	
	
	public static Model.Finder<Integer,User> find = new Model.Finder(Integer.class, User.class);

	public User(){
		
	}
	public User(String email, String password, String front_name,
			String last_name, UserRole role) {
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
	
	//Method untuk subject Deadbolt
	@Override
	public String getIdentifier() {
		return email;
	}
	@Override
	public List<? extends Permission> getPermissions() {
		return permissions;
	}
	@Override
	public List<? extends Role> getRoles() {
		List<UserRole> roles=new ArrayList<UserRole>();
		roles.add(role);
		return roles;
	}
	
	//Getter Setter
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
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
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
	public FileUpload getProfile_photo() {
		return profile_photo;
	}

	public void setProfile_photo(FileUpload profile_photo) {
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

	
}

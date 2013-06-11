package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

import models.custom_helper.ToLink;
import models.data.enumeration.ContactTypeEnum;

@Entity
@Table(name="user_contact")
public class UserContact extends Model {

	@Id
	private int  id_user_contact;
	@ManyToOne
	private User user;
	private String contact_value;
	private ContactTypeEnum contact_type;
	@Column(columnDefinition = "TEXT")
	private String contact_description;
	
	public static Model.Finder<Integer,UserContact> find = new Model.Finder(Integer.class, UserContact.class);
		
	public UserContact(String contact_value, ContactTypeEnum contact_type,
			String contact_description) {
		super();
		this.contact_value = contact_value;
		this.contact_type = contact_type;
		this.contact_description = contact_description;
	}
	public int getId_user_contact() {
		return (int) id_user_contact;
	}
	public void setId_user_contact(int id_user_contact) {
		this.id_user_contact = id_user_contact;
	}
	public User getId_user() {
		return user;
	}
	public void setId_user(User id_user) {
		this.user = id_user;
	}
	public String getContact_value() {
		return contact_value;
	}
	public void setContact_value(String contact_value) {
		this.contact_value = contact_value;
	}
	public ContactTypeEnum getContact_type() {
		return contact_type;
	}
	public void setContact_type(ContactTypeEnum contact_type) {
		this.contact_type = contact_type;
	}
	public String getContact_description() {
		return contact_description;
	}
	public void setContact_description(String contact_description) {
		this.contact_description = contact_description;
	}
	public String getContact_value_link(){
		return ToLink.convert(contact_value);
	}
}

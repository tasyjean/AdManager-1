package models.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.ebean.Model;

import models.data.enumeration.ActionTypeEnum;

@Entity
@Table(name="ads_action", schema ="adsmanager")
public class AdsAction {

	@Id
	private Long id_ads_action;
	@ManyToOne
	private Impression impression;
	private ActionTypeEnum action_type;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	public static Model.Finder<Integer,AdsAction> find = new Model.Finder(Integer.class, AdsAction.class);

	public Long getId_ads_action() {
		return id_ads_action;
	}

	public void setId_ads_action(Long id_ads_action) {
		this.id_ads_action = id_ads_action;
	}

	public Impression getImpression() {
		return impression;
	}

	public void setImpression(Impression impression) {
		this.impression = impression;
	}

	public ActionTypeEnum getAction_type() {
		return action_type;
	}
	public void setAction_type(ActionTypeEnum action_type) {
		this.action_type = action_type;
	}
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}

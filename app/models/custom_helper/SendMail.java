package models.custom_helper;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

public class SendMail  {
	
	private String subject;
	private String recipient;
	private String content;
	private String sender="komputok@gmail.com";
	
	private  MailerAPI mail = play.Play.application()
			.plugin(MailerPlugin.class).email();
	
	public void sendHTML(){
		setData();
		mail.sendHtml(content);
	}
	public void sendText(){
		setData();
		mail.send(content);
	}
	private void setData(){
		mail.setSubject(subject);
		mail.addRecipient(recipient);
		mail.addFrom(sender);
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}

}

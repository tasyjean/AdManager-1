package HelperTest;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.custom_helper.SendMail;

import org.junit.Test;

public class EmailTest {

	@Test
	public void sendEmail(){
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				SendMail mail = new SendMail();
				mail.setRecipient("komputok@gmail.com");
				mail.setSubject("Testing subject");
				mail.setContent("ini konten");
				mail.setSender("xenovons@gmail.com");
				mail.sendText();
			}
		});
	}
}

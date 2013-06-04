package models.custom_helper;

import play.data.Form;
import play.mvc.Http;
import models.form.frontendForm.RegistrationForm;
import net.tanesha.recaptcha.ReCaptchaException;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
public class RecaptchaRegistration {
	
	ReCaptchaImpl reCapthca = new ReCaptchaImpl();
	
	static RecaptchaRegistration instance= new RecaptchaRegistration();
 
	public static RecaptchaRegistration get(){
		if(instance!=null){
			return instance;
		} else return new RecaptchaRegistration();
	}
	public boolean validate(Form<RegistrationForm> form, String source){
		
		try{
			reCapthca.setPrivateKey(play.Play.application().configuration().getString("capctha.private_key"));
			String challenge=form.get().recaptcha_challenge_field;
			String response=form.get().recaptcha_response_field;
			ReCaptchaResponse result = reCapthca.checkAnswer(source, challenge, response);
			return result.isValid()? true : false;
		}catch(ReCaptchaException e){
			e.printStackTrace();
			return true;
		}
	}
}

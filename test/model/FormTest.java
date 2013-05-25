package model;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.form.frontendForm.LoginForm;
import models.form.frontendForm.RegistrationForm;

import org.junit.Test;

public class FormTest {


	public void loginTest(){
        running(fakeApplication(), new Runnable() {
        	@Override
        	public void run() {

        		LoginForm login=new LoginForm();
        		login.email="komputok@gmail.com";
        		login.password="password";
        		
		        assertThat(login.validate()).isNotNull();

        	}
        });

	}
	@Test
	public void duplicateTest(){
        running(fakeApplication(), new Runnable() {
        	@Override
        	public void run() {
        		RegistrationForm form=new RegistrationForm();
        		form.email="kftok@gmail.com";
        		assertThat(form.isDuplicate()).isTrue();
        	}
        });

	}

}

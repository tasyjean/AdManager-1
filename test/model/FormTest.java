package model;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.form.LoginForm;

import org.junit.Test;

public class FormTest {


	@Test
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

}

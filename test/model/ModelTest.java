package model;


import junit.runner.BaseTestRunner;

import org.junit.Before;
import models.*;
import models.data.BannerSize;
import models.data.User;
import models.data.enumeration.RoleEnum;

import org.junit.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ModelTest {

    @Test
    public void createAndRetrieveUser() {
		
        running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				BannerSize size=new BannerSize("anu2", 335, 1113, "Untuk ukuran ngasal sumpah");
				
				size.save();
				BannerSize biob=BannerSize.find.where().eq("name", "anu2").findUnique();
				
		        assertThat(biob.getName()).isEqualTo("anu2");
		        				
			}
	
    	
        });
    }
}

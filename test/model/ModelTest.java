package model;


import org.junit.Before;
import models.*;
import models.data.AdsSize;
import models.data.User;
import models.data.enumeration.RoleEnum;

import org.junit.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ModelTest {
    @BeforeClass
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void createAndRetrieveUser() {
		AdsSize size=new AdsSize("anu2", 335, 1113, "Untuk ukuran ngasal sumpah");
		
		size.save();
		AdsSize biob=AdsSize.find.where().eq("name", "anu2").findUnique();
		
        assertThat(biob.getName()).isEqualTo("anu2");
       
    }
}

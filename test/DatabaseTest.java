import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import models.data.User;

import org.junit.Assert;
import org.junit.Test;

import play.db.DB;
import play.mvc.Result;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class DatabaseTest {

    @Test 
    public void testDataSource() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                DataSource ds = DB.getDataSource();
                assertThat(ds).isNotNull();
            }
        });
        

  }
  
    public void testGetConnection() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Connection conn = DB.getConnection();
                assertThat(conn).isNotNull();
                try {
					conn.createStatement().execute("insert into adsmanager.user(email,password) values('anu','anu')");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    
    @Test
    public void setData(){
        running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				  Result result = route(fakeRequest(GET,"/home"));
				  assertThat(result).isNotNull();
				  System.out.println(result);

			}
        
        });
    }
    @Test
    public void testGetData() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Connection conn = DB.getConnection();
                assertThat(conn).isNotNull();
                try {
					ResultSet result;
					result= conn.createStatement().executeQuery("select email from adsmanager.user");
					int i=0;
					while(result.next())
					{
						System.out.println(result.getString(1));
						i++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
    }
}
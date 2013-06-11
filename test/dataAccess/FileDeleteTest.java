package dataAccess;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.data.FileUpload;

import org.junit.Test;

public class FileDeleteTest {


	@Test
	public void testDelete(){
        running(fakeApplication(), new Runnable() {
        	
        	@Override
        	public void run() {
        		FileUpload upload=FileUpload.find.byId(101);
        		System.out.println(upload.getThumbnailPath());
        		upload.delete();
        		
        		
        	}
	});
        }
}

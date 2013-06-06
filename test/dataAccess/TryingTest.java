package dataAccess;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;

import org.junit.Test;

import models.data.BannerSize;

public class TryingTest {

	@Test
	public void main() {
		running(fakeApplication(), new Runnable() {
			@Override
			public void run() {
				List<BannerSize> banner= BannerSize.find.all();
				System.out.print(banner.get(1).getHeight());				
			}
		});


		
	}
	
	

}

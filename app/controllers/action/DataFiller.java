package controllers.action;

import models.dataWrapper.TemplateData;
import models.service.notification.NotificationCenter;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;

//Untuk mengisi variabel templatedata
public class DataFiller extends Action.Simple {
	NotificationCenter notif=new NotificationCenter();
	
	@Override
	public Result call(Context context) throws Throwable {
		TemplateData data=new TemplateData(context, notif);
		data.setUserData();
		context.args.put("templateData", data);
		
		return delegate.call(context);
	}
}

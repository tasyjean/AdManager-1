package controllers.action;

import models.dataWrapper.TemplateData;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;

//Untuk mengisi variabel templatedata
public class DataFiller extends Action.Simple {

	@Override
	public Result call(Context context) throws Throwable {
		TemplateData data=new TemplateData(context);
		data.setUserData();
		context.args.put("templateData", data);
		
		return delegate.call(context);
	}

}

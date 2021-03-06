package models.service.user;

import com.avaje.ebean.Page;

import models.data.User;
import models.data.Zone;

public class UserFetch {
	
	public User getSingleUser(int id_user){
		return User.find.byId(id_user);
	}
	public Page<User> getUser(int page, int pageSize, String sortBy, String order){
        return 
                User.find.where()
                    .orderBy(sortBy + " " + order)
                    .findPagingList(pageSize)
                    .getPage(page);		
	}
}

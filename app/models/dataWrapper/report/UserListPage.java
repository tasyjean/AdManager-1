package models.dataWrapper.report;

import java.util.List;

import models.data.User;

import com.avaje.ebean.Page;

public class UserListPage {

	private List<UserList> userList;
	private Page<User> page;
	public UserListPage(List<UserList> list, Page<User> page) {
		this.userList=list;
		this.page=page;
	}
	public List<UserList> getUserList() {
		return userList;
	}
	public Page<User> getPage() {
		return page;
		
	}

	
	

}

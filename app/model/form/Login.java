package model.form;

import java.util.*;

import javax.validation.*;

import play.data.validation.Constraints.*;

public class Login {

	@Required
	public String email;
	
	@Required
	public String password;
	
}

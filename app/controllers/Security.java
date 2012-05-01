package controllers;

import javax.jws.soap.SOAPBinding.Use;

import models.User;

public class Security extends Secure.Security {

	static boolean authenticate(String username, String password){
		return User.connect(username, password) != null;
		//return true;
	}

	static boolean check(String profile){
		if("admin".equals(profile)){
			return User.find("byEmail", connected()).<User>first().isAdmin;
		}

		return false;
	}


}

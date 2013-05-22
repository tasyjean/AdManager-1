package models.custom_helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;;

public class MD5 {

	private final String KEYVAR="Unicantik";
	private static MD5 instance = null;
	public static MD5 get(){ //mempersingkat getInstance
		if(instance==null){
			instance= new MD5();
		}
		return instance;
	}
	public String md5(String input){
		String result=DigestUtils.md5Hex(KEYVAR + input);
		return result;
	}

}

package org.o7planning.sbsecurity.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {
	
	public static String encryptPassword(String password){
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}
	
	public static void main(String[] args) {
		String passwrod = "123";
		System.out.println(encryptPassword(passwrod));
	}

}

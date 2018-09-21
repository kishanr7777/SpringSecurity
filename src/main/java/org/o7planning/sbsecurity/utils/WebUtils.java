package org.o7planning.sbsecurity.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class WebUtils {

	public static String toString(User loginUser) {
		// TODO Auto-generated method stub
		StringBuilder sBuilder = new StringBuilder();
		
		sBuilder.append("username : ").append(loginUser.getUsername());
		
		Collection<GrantedAuthority> authorities = loginUser.getAuthorities();
		
		if(authorities != null && !authorities.isEmpty()){
			sBuilder.append(" (");
			boolean first = true;
			for(GrantedAuthority authority : authorities){
				if(first){
					sBuilder.append(authority.getAuthority());
					first = false;
				}else{
					sBuilder.append(", ").append(authority.getAuthority());
				}
			}
			sBuilder.append(")");
		}
		System.out.println(sBuilder.toString());
		return sBuilder.toString();
	}
	
	

}

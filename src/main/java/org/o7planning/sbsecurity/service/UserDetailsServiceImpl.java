package org.o7planning.sbsecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.o7planning.sbsecurity.dao.AppRoleDAO;
import org.o7planning.sbsecurity.dao.AppUserDAO;
import org.o7planning.sbsecurity.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	AppRoleDAO appRoleDAO;
	
	@Autowired
	AppUserDAO appUserDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		AppUser appUser = appUserDAO.findUserAccount(username);
		
		if(appUser == null){
			System.out.println("user not found" + username);
			throw new UsernameNotFoundException("User "+ username + " was not found in the database");
		}
		System.out.println("found user" + username);
		
		//role user and role admin
		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if(roleNames != null){
			for(String role: roleNames){
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantedAuthorities.add(authority);
			}
		}
		
		UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), appUser.getEncrytedPassword(), grantedAuthorities);
		return userDetails;
	}

}

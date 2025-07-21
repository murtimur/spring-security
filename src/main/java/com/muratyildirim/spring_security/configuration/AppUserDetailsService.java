package com.muratyildirim.spring_security.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.muratyildirim.spring_security.user.User;
import com.muratyildirim.spring_security.user.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService {

	UserService userService;

	public AppUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User inDB = userService.getUser(username);
		if (inDB == null) {
			throw new UsernameNotFoundException(username + " is not found");
		}
		return new CurrentUser(inDB);
	}

}

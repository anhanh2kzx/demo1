package com.trungtamjava.springbootdemo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trungtamjava.springbootdemo.dao.UserRepository;
import com.trungtamjava.springbootdemo.entity.User;
import com.trungtamjava.springbootdemo.model.UserPrincipal;
@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Not Found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		UserPrincipal userDetails = new UserPrincipal(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
		userDetails.setId(user.getId());
		return userDetails;
	}

}

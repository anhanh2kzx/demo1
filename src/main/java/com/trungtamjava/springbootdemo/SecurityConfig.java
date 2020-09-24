package com.trungtamjava.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trungtamjava.springbootdemo.service.UserServiceImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserServiceImpl userServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers("/product/search").hasAnyRole("ADMIN").anyRequest()
//				.permitAll().and().formLogin().loginPage("/login").defaultSuccessUrl("/product/search")
//				.failureUrl("/login?e=error").permitAll().and().logout().permitAll().and().exceptionHandling()
//				.accessDeniedPage("/login?e=deny");

		
		  http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasAnyAuthority(
		  "ROLE_ADMIN").antMatchers("/member/**").
		  authenticated().anyRequest().permitAll() .and().formLogin()
		  .loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl(
		  "/member/home")
		  .failureUrl("/login?err").and().logout().logoutUrl("/logout").
		  logoutSuccessUrl("/login")
		 .permitAll().and().exceptionHandling().accessDeniedPage("/login?deny");
		 
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
		return bCryptPasswordEncoder;
	}
}

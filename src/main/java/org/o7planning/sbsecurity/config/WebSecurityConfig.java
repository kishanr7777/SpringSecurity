package org.o7planning.sbsecurity.config;

import javax.sql.DataSource;

import org.o7planning.sbsecurity.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsServiceImpl userService;
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Autowired
	public void configerGlobal(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		
		//pages that does not require login
		http.authorizeRequests().antMatchers("/", "/login","/logout","temp").permitAll();
		
		//accessible by admin and user roles
		http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')");
		
		//accessible only by admin role
		http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
		
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		http.authorizeRequests().and().formLogin()//
			.loginProcessingUrl("/j_spring_security_check")//
			.loginPage("/login")//
			.defaultSuccessUrl("/userInfo")//
			.failureUrl("/login?error=true")//
			.usernameParameter("username")//
			.passwordParameter("password")//
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
		
		http.authorizeRequests().and()//
			.rememberMe().tokenRepository(this.persistentTokenRepository())//
			.tokenValiditySeconds(1*60*60);

	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl dJdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		dJdbcTokenRepositoryImpl.setDataSource(dataSource);
		return dJdbcTokenRepositoryImpl;
	}

}

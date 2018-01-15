package com.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.bean.User;
import com.dao.UserDao;

@Path("/login")
public class LoginService {
	
	 private static final transient org.slf4j.Logger log = LoggerFactory.getLogger(LoginService.class);

	public LoginService() {
		/*
		UserRealm realm = new UserRealm();
		org.apache.shiro.mgt.SecurityManager securityManager = new DefaultSecurityManager(realm);
		//Make the SecurityManager instance available to the entire application via static memory: 
		SecurityUtils.setSecurityManager(securityManager);*/
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saveuser")
	public String saveUser(final User user)
	{	
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		Object salt = rng.nextBytes();
 
		String hashedPasswordBase64 = new Sha256Hash(user.getPassword(), salt, 1024).toBase64();

		user.setPassword(hashedPasswordBase64);
		user.setSalt(salt.toString());
		System.out.println(user.toString());	
		int status = UserDao.saveUser(user);
		JSONObject json = new JSONObject();
		json.put("status", status);
		return json.toString();
	}
	
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String validate(final User user)
	{
		
		/*//System.out.println(user.toString());
		if(user.getMobile().equals("123") && user.getPassword().equals("123"))
		{
			return "{valid:true}";
		}
		
		JSONObject json = new JSONObject();
		boolean valid = UserDao.validate(user);
		json.put("valid", valid);
		return json.toString();*/
		
		boolean status = true;
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		System.out.println("sssssssss--1");
		if(!currentUser.isAuthenticated())
		{
			System.out.println("sssssssss--2");
			UsernamePasswordToken token = new UsernamePasswordToken(user.getMobile(), user.getPassword());
			//System.out.println("ssssss2.1"+token);
			token.setRememberMe(true);
			try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
            	status = false;
            	System.out.println("There is no user with username of " + token.getPrincipal()+" exception"+uae.getMessage());
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
            	status = false;
            	System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
            	status = false;
            	System.out.println("sssssssss--5");
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }            
            catch (AuthenticationException ae) {
            	status = false;
            	System.out.println("sssssssss--6");
                log.info(ae.getMessage());
            }
		}
		JSONObject json = new JSONObject();
		json.put("valid", status);
		return json.toString();		
	}
}


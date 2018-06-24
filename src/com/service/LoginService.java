package com.service;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import com.dao.DBFacade;
import com.dao.UserDao;
import com.dao.UserRealm;
import com.utility.Errorcode;

@Path("/login")
public class LoginService {
	
	private static final transient org.slf4j.Logger log = LoggerFactory.getLogger(LoginService.class);
	
	public static String statusStr = "status";
	public static String errmsgStr = "errmsg";
	public LoginService() {
		/*
		UserRealm realm = new UserRealm();
		org.apache.shiro.mgt.SecurityManager securityManager = new DefaultSecurityManager(realm);
		//Make the SecurityManager instance available to the entire application via static memory: 
		SecurityUtils.setSecurityManager(securityManager);*/
	}
	
	public String sendResponse(int status)
	{
		String response = Errorcode.errmsgstr[status];
		
		JSONObject json = new JSONObject();
		json.put(statusStr, status);
		json.put(errmsgStr, response);		
		
		return json.toString();		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saveuser")
	public String saveUser(final User user) throws IOException
	{	
		
		System.out.println("aaaaaa");
		/*Wrap it in new class*/
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		Object salt = rng.nextBytes();
		String hashedPasswordBase64 = new Sha256Hash(user.getPassword(), salt, 1024).toBase64();
		UUID uuid = UUID.randomUUID();
		
		user.setPassword(hashedPasswordBase64);
		user.setSalt(salt.toString());
		user.setUuid(uuid.toString());
		user.setActive(0);
				
		int status = Errorcode.EC_SUCCESS.getValue();
		do
		{
			status = DBFacade.saveUser(user);
			if(status != Errorcode.EC_SUCCESS.getValue())
				break;
			
			status = UserRealm.sendVerificationEmail(user);
		
		}while(false);		
		
		return sendResponse(status);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/profileupdate")
	public String profileupdate(final User user) throws IOException
	{						
		User olduser = UserRealm.getLoggedInUser();				
		
		int status = Errorcode.EC_SUCCESS.getValue();		
		
		status = DBFacade.updateProfile(olduser, user);
		
		if(status != Errorcode.EC_SUCCESS.getValue())
		{
			return sendResponse(status);
		}
				
		if(user.getEmail() != null)
		{			
			user.setUuid(olduser.getUuid());
			user.setUsername(olduser.getUsername());
			status = UserRealm.sendVerificationEmail(user);
			
			/*Invalidate current logged in user session*/
			UserRealm.invalidateCurrentSession();
		}		
		return sendResponse(status);
	}
	
	
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String validate(final User user)
	{
			
    		int status = Errorcode.EC_SUCCESS.getValue();
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();			
		
		User usrDetails = DBFacade.isUserAccountActive(User.emailStr , user.getEmail());
		
		int accountActive = usrDetails.getActive();
		
		if(accountActive == 1)
		{
			if(!currentUser.isAuthenticated())
			{						
				UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());					
			
				token.setRememberMe(true);
				try {
					currentUser.login(token);            
				} catch (UnknownAccountException uae) {
					status = Errorcode.EC_ACCOUNT_UNKNOWN.getValue();            
					log.info("There is no user with an email " + token.getPrincipal());
					
				} catch (IncorrectCredentialsException ice) {
					status = Errorcode.EC_INCORRECT_CREDENTIALS.getValue();            	            
					log.info("Password for account " + token.getPrincipal() + " was incorrect!");
					
				} catch (LockedAccountException lae) {
					status = Errorcode.EC_ACCOUNT_LOCKED.getValue();            	            	
                
					log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
				}            
				catch (AuthenticationException ae) {
					status = Errorcode.EC_USER_NOT_AUTHENTICATED.getValue();            	            	
                
					log.info(ae.getMessage());
				}
			}
		}
		else
		{
			status = Errorcode.EC_USER_NOT_VERIFIED.getValue();
		}
		//this logic needs to check

		JSONObject json = new JSONObject();
		json.put(statusStr, status);
		json.put(errmsgStr, Errorcode.errmsgstr[status]);
		json.put("accountStatus", accountActive);		
		json.put("userEmail", usrDetails.getEmail());
		
		return json.toString();		
	}
	
	
	@GET
	@Path("/verify/{username}/{hash}")
	@Produces(MediaType.TEXT_PLAIN)
	public String verifyUser(@PathParam("username") String username, @PathParam("hash") String hash)
	{
		int status = Errorcode.EC_SUCCESS.getValue();
		String response = null;
		status = UserRealm.verify(username,hash);
		do
		{
			if(status == Errorcode.EC_SUCCESS.getValue())
			{				
				response = "User is verified successfully, Please login";
				break;
			}
			else if(status ==  Errorcode.EC_USER_VERIFICATION_FAILED.getValue())
			{
				response = "Verification failed, Please signup again";
				break;
			}
			else
			{
				response = "User already active, Please login";
				break;
			}
		}while(false);
		
		return response;
	}
	
	@GET
	@Path("/forgetpassword/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public String forgetpassword(@PathParam("email") String email)
	{		
		int status = Errorcode.EC_SUCCESS.getValue();
		status = UserRealm.sendResetPasswordLink(email);				
		return sendResponse(status);
	}
	
	@GET
	@Path("/resetpassword/{uuid}/{newpassword}")
	@Produces(MediaType.APPLICATION_JSON)
	public String resetpassword(@PathParam("uuid") String uuid, @PathParam("newpassword") String newpassword)
	{		

		/*Wrap it in new class*/
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		Object salt = rng.nextBytes();
		String hashedPasswordBase64 = new Sha256Hash(newpassword, salt, 1024).toBase64();		

		int status = DBFacade.resetPassword(uuid,hashedPasswordBase64, salt.toString());
		
		return sendResponse(status);
	}
}


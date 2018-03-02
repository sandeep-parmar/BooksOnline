package com.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import com.bean.User;
import com.utility.UserField;

public class UserRealm extends AuthorizingRealm{

	public UserRealm() {
		System.out.println("initializing userrealm");
		
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	
		System.out.println("In doGetAuthenticationInfo");
		String principalAsMobile = token.getPrincipal().toString();
		//User user = UserDao.getUserAccount(principal,UserField.MOBILE);
		User user = DBFacade.getUserAccount(principalAsMobile,"mobile");
		if(user != null){
			SaltedAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(), 
			ByteSource.Util.bytes(Base64.getDecoder().decode(user.getSalt().getBytes())),getName());
			return info;
		}else{
			throw new UnknownAccountException();
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		return null;
	}
	
	
	public static User getLoggedInUser()
	{
		/*Get current logged in user(subject)*/
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		
		/*Get current logged in user object from shiro*/
		User user = (User)currentUser.getPrincipal();
		
		return user;
	}
	public static String sendVerificationEmail(User user) throws IOException
	{
		String result;
		//InputStream eamilProps = getCl;
		Properties properties = new Properties();
		String fromProp = new String();
		String passwordProp = new String();
		ClassLoader loader = Thread.currentThread().getContextClassLoader(); 
		InputStream fstream = loader.getResourceAsStream("/com/properties/email.properties");
		properties.load(fstream);
		
		if(null != fstream){
			fromProp = properties.getProperty("username");
			passwordProp = properties.getProperty("password");
		}
		
	   // Recipient's email ID needs to be mentioned.
	   String to = user.getEmail();
	   
	   // Assuming you are sending email from localhost
	   String host = "localhost";

	   // Get system properties object
	   properties = System.getProperties();

	   final String from = fromProp;
	   final String password = passwordProp;
	   final String verificationUrl = "http://localhost:8080/BooksOnline/rest/login/verify/" + user.getUsername() + "/" + user.getUuid();
		
	   // Setup mail server
	   	properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		properties.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
		properties.put("mail.smtp.port", "465"); //SMTP Port
		
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
	   // Get the default Session object.
	   Session mailSession = Session.getDefaultInstance(properties, auth);

	   try {
	      // Create a default MimeMessage object.
	      MimeMessage message = new MimeMessage(mailSession);
	      
	      // Set From: header field of the header.
	      message.setFrom(new InternetAddress(from));
	      
	      // Set To: header field of the header.
	      message.addRecipient(Message.RecipientType.TO,
	                               new InternetAddress(to));
	      // Set Subject: header field
	      message.setSubject("OnlineBook verification");
	      
	      // Now set the actual message
	      message.setContent("<h3>Hello " + user.getUsername() +"</h3>"
	      		+ "<h3>Please click on the link below to verify your email address.</h3>"
	    		 + "<a href=" + verificationUrl + ">Verify Account</a>", "text/html" );
	      
	      // Send message
	      Transport.send(message);
	      result = "Verification email sent";
	   } catch (MessagingException mex) {
	      mex.printStackTrace();
	      result = "Error: unable to send email....";
	   }
	   return result;
	}

	public static int verify(String username, String hash) {
	
		int status = 0;
		User user = DBFacade.getUserAccount(username, "username");
		if(user.getActive() == 0)
		{
			if(user.getUuid().equals(hash))
			{
				DBFacade.activateUserAccount(username);
				status =  0;
			}
			else
			{
				status = -1;
			}
		}
		else
		{
			status = 1;
		}
		return status;
	}
}

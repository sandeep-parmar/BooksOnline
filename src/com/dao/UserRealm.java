package com.dao;

import java.util.Base64;

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

public class UserRealm extends AuthorizingRealm{

	public UserRealm() {
		System.out.println("initializing userrealm");
		
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
	
		System.out.println("In doGetAuthenticationInfo");
		String principal = token.getPrincipal().toString();
		User user = UserDao.getUserAccount(principal);
		if(user != null)
		{
			//SimpleAuthenticationInfo info= new SimpleAuthenticationInfo(user.getMobile(),user.getPassword(),"UserRealm");
			SaltedAuthenticationInfo info = new SimpleAuthenticationInfo(user.getMobile(),
																		 user.getPassword(), 
										ByteSource.Util.bytes(Base64.getDecoder().decode(user.getSalt().getBytes())),
										"userRealm");
			return info;
		}
		else
		{
			throw new UnknownAccountException();
		}
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		// TODO Auto-generated method stub
		System.out.println("In support");
		return super.supports(token);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

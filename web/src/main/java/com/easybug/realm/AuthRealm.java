package com.easybug.realm;

import com.easybug.service.login.ILogin;
import com.easybug.model.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
public class AuthRealm extends AuthorizingRealm{
	@Autowired
	private ILogin loginService;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		// TODO Auto-generated method stub
		//获取session中的用户
		User user = (User) principal.fromRealm(this.getClass().getName()).iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		SimpleAuthenticationInfo info = null;
		
		//获取用户输入的token
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;
		User lu = new User();
		lu.setPassword(new String(utoken.getPassword()));
		lu.setuId(Integer.valueOf(utoken.getUsername()));
		User user = loginService.login(lu);
		//MD5加盐
		if(user != null){
			Object principal = user.getuId();
			Object credentials = user.getPassword();
			info = new SimpleAuthenticationInfo(user, credentials,this.getClass().getName());
		}
		return info;
	}

}

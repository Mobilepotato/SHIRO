package Realm;

import entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class realm extends AuthorizingRealm {
    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //通过令牌获取用户名
        String username= (String) authenticationToken.getPrincipal();
       //获取密码
        String password=new String((char[])authenticationToken.getCredentials());
        //模拟从数据库获取数据
        User user=new User();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setStatus("1");
        if(!username.equals(user.getUsername())){
            throw  new UnknownAccountException("用户名不存在！");
        }
        else if(!password.equals(user.getPassword())){
            throw new IncorrectCredentialsException("密码错误！");
        }else if("0".equals(user.getStatus())){
            throw new LockedAccountException("用户被锁定！");
        }



        return new SimpleAuthenticationInfo(user,password,getName());
    }


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //初始化简单的授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //初始化角色信息表
        Set<String> roles=new HashSet<String>();
        roles.add("admin");
        Set<String>permission=new HashSet<String>();
        permission.add("user:create");
        permission.add("user:delete");
        permission.add("user:update");
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permission);

        return simpleAuthorizationInfo;
    }

}

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class RealmText {
    @Test
    public void doGetAuthenticationInfo() {
        //初始化工厂
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //创建安全管理器
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        //设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建主体
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
        subject.login(token);
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.hasRole("sadmin"));
        System.out.println(subject.isPermitted("product:create"));
        System.out.println(subject.isPermitted("user:create"));

    }
}

package cn.tet.shirotest2.shirotest2.shiro;

import cn.tet.shirotest2.shirotest2.entity.SysPermission;
import cn.tet.shirotest2.shirotest2.entity.SysUser;
import cn.tet.shirotest2.shirotest2.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    public void setName(String name) {
        super.setName("myrealm");
    }

    /**
     * 认证程序
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取认证subject信息中的用户名
        String usercode= (String) authenticationToken.getPrincipal();
        SysUser user=sysUserService.getUserByCode(usercode);
        System.out.println("认证====================");
        if(user==null){
            throw new UnknownAccountException("用户名密码不存在");
        }
        if(user.getLocked()==1){
            throw new LockedAccountException("账户已经锁定请联系管理员");
        }
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),ByteSource.Util.bytes(user.getSalt()),getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user= (SysUser) principalCollection.getPrimaryPrincipal();
        System.out.println("进行授权===================="+user.getUsercode());
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        List<SysPermission> permissions = new ArrayList<>();
        try {
            permissions = sysUserService.getPermissions(user.getUsercode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("permissions================="+permissions.size());

        ///在认证成功之后返回.
        //设置角色信息.
        //支持 Set集合,
        //用户的角色对应的所权限，如果只使用角色定义访问权限，下面的四行可以不要
        Set<String> permissionsList = new HashSet<>();
        for (SysPermission permission : permissions) {
            System.out.print(user.getUsercode()+" 的permission信息"+permission.getPercode());
            permissionsList.add(permission.getPercode());
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //设置权限信息.
        authorizationInfo.setStringPermissions(permissionsList);
        return authorizationInfo;
    }

}

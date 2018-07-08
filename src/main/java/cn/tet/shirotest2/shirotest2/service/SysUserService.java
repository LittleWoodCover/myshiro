package cn.tet.shirotest2.shirotest2.service;


import cn.tet.shirotest2.shirotest2.entity.SysPermission;
import cn.tet.shirotest2.shirotest2.entity.SysUser;

import java.util.List;

public interface SysUserService {
   SysUser getUserByName(String name);
   SysUser getUserByCode(String usercode);
   SysUser doLogin(String name, String password);
   List<SysPermission> getPermissions(String username);
   List<SysUser> getAllUsers();
}

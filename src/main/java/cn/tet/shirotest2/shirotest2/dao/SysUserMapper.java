package cn.tet.shirotest2.shirotest2.dao;


import cn.tet.shirotest2.shirotest2.entity.SysPermission;
import cn.tet.shirotest2.shirotest2.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysUserMapper {
     SysUser getUserByName(String name);
     SysUser getUserByCode(String usercode);
     SysUser doLogin(@Param("name") String name, @Param("password") String password);
     List<SysPermission> getPermissions(String usercode);
     List<SysUser> getAllUsers();
}

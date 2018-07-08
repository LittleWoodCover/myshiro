package cn.tet.shirotest2.shirotest2.controller;

import cn.tet.shirotest2.shirotest2.entity.SysUser;
import cn.tet.shirotest2.shirotest2.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class SyUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/users")
    @ResponseBody
    public List<SysUser> getUser(){
        return sysUserService.getAllUsers();
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/userlist")
    @RequiresPermissions("item:query")
    public String index(){
        return "userlist";
    }
}

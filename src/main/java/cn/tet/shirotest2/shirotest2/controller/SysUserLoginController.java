package cn.tet.shirotest2.shirotest2.controller;


import cn.tet.shirotest2.shirotest2.utils.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class SysUserLoginController {

    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    public String doLogin(@RequestParam String usercode,@RequestParam String password){
        UsernamePasswordToken token=new UsernamePasswordToken(usercode,password);
        System.out.println("==============用户"+usercode);
        Subject subject=SecurityUtils.getSubject();
        try {
            subject.login(token);

        }catch (Exception e){
            return "login";
        }

        return "index";
    }
}

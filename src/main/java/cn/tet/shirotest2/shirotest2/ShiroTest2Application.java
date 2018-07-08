package cn.tet.shirotest2.shirotest2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.tet.shirotest2.shirotest2.dao")
public class ShiroTest2Application {

    public static void main(String[] args) {
        SpringApplication.run(ShiroTest2Application.class, args);
    }
}

package cn.tet.shirotest2.shirotest2.shiro;

import cn.tet.shirotest2.shirotest2.session.RedisSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {


    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        System.out.println("初始化deleFitler==================================");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());

        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/home");

        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/dologin", "anon");
        filterChainDefinitionMap.put("/logout*","anon");
        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }




    @Bean(name = "securityManager")
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
      //  securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    @Bean(name="myShiroRealm")
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm=new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(credentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher=new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        return credentialsMatcher;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 自定义sessionManager
     * 注意,spirngboot和springnvc使用的jedis操作不一样.
     * 如果自己用到redis实现session共享这部分代码就需要
     * @return
     */
//    @Bean
//    public SessionManager sessionManager(){
//        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
////        sessionManager.setSessionDAO(sessionDao());
//        return sessionManager;
//    }
//
//    /**
//     * 自定义的sessionDao,实现session的crud
//     * @return
//     */
//    @Bean
//    public RedisSessionDao sessionDao() {
//            return new RedisSessionDao();
//    }
}

package cn.tet.shirotest2.shirotest2.config;

import cn.tet.shirotest2.shirotest2.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    @Bean(name = "jedisPool")
    public JedisPool jedisPool1(){
       return new JedisPool("192.168.25.133",6379);
    }

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }

}

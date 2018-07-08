package cn.tet.shirotest2.shirotest2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class JedisUtils {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }
    //向redis中添加
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis=getResource();
        try{
            jedis.set(key,value);
            return value;
        }finally {
            jedis.close();
        }
    }

    //设置过期时间;
    public void expire(byte[] key, int i) {
        Jedis jedis=getResource();
        try{
            jedis.expire(key,i);
        }finally {
            jedis.close();
        }
    }
    //删除session
    public void logout(byte[] key){
        Jedis jedis=getResource();
        try{
           jedis.del(key);
        }finally {
            jedis.close();
        }
    }
    public byte[] get(byte[] key){
        Jedis jedis=getResource();
        try{
           byte[] value= jedis.get(key);
           return value;
        }finally {
            jedis.close();
        }
    }

    public void delete(byte[] key) {
        Jedis jedis=getResource();
        try{
           jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys() {

        Jedis jedis=getResource();
        try{
            Set<byte[]> keys = jedis.keys((CommonUtils.SESSION_PRE + "*").getBytes());
            return keys;
        }finally {
            jedis.close();
        }

    }
}

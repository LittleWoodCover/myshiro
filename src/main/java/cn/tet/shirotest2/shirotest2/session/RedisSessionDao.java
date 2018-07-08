package cn.tet.shirotest2.shirotest2.session;


import cn.tet.shirotest2.shirotest2.utils.CommonUtils;
import cn.tet.shirotest2.shirotest2.utils.JedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private JedisUtils jedisUtils;
    public void saveSession(Session session){
        if(session!=null&&session.getId()!=null){
            //用自定义的方法产生一个key
            byte[] key=CommonUtils.getSessionKey(session.getId().toString());
            //将session序列化为一个byte[]
            byte[] value=SerializationUtils.serialize(session);
            //c存入session并且设置过期时间
            jedisUtils.set(key,value);
            jedisUtils.expire(key,600);
        }
    }

    /**
     * 创建一个sessin并存入session中
     * 1:创建一个sessionid
     * 2将session作为key设置为redis中的Key
     * 3存入redis
     * @param session
     * @return
     */
    protected Serializable doCreate(Session session) {
        //通过自带的generateSessionId获取sessionid
        Serializable sessionid=generateSessionId(session);
        //生成sesision之后要将它与sessionid捆绑
        assignSessionId(session,sessionid);
        saveSession(session);
        return sessionid;
    }

    /**
     *通过sessinid从redis中获取session,
     * @param sessionid
     * @return
     */
    protected Session doReadSession(Serializable sessionid) {
        if(sessionid==null){
            return null;
        }
        //
        byte[] value=jedisUtils.get(CommonUtils.getSessionKey(sessionid.toString()));
        //使用spring自带的反序列化工具将sessin中存的sessin返回
        return (Session) SerializationUtils.deserialize(value);
    }

    public void update(Session session) throws UnknownSessionException {
       saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if(session==null||session.getId()==null){
            return ;
        }
        byte[] key=CommonUtils.getSessionKey(session.getId().toString());
        jedisUtils.delete(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys=jedisUtils.keys();
        Set<Session> sessions=new HashSet<>();
        if(CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for(byte[] key:keys){
            sessions.add((Session) SerializationUtils.deserialize(jedisUtils.get(key)));
        }
        return sessions;
    }
}

package cn.tet.shirotest2.shirotest2.utils;

public class CommonUtils {
    public static final  String  SESSION_PRE="sessionid:";

    public static byte[] getSessionKey(String key){
        return (SESSION_PRE+key).getBytes();
    }
}

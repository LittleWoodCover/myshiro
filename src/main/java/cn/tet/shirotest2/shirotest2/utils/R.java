package cn.tet.shirotest2.shirotest2.utils;

public class R {

    private Integer status;
    private String msg;
    private Object object;

    public R(Integer status, String msg, Object object) {
        this.status = status;
        this.msg = msg;
        this.object = object;
    }

    public static R ok(){
        return new R(1,"ok","");
    }

    public static R error(){
        return new R(0,"error","");
    }
}

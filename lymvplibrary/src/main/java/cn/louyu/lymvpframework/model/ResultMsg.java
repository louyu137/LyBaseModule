package cn.louyu.lymvpframework.model;

import java.io.Serializable;

/**
 * Created by LOUYU on 2018/4/12.
 */

public class ResultMsg<T extends Object> implements Serializable {
    public boolean Success;
    public String Msg;
    public T Data;
    public int Status;
    public int Count;

    public final String acquireNotNullMsg(){
        return Msg==null?"":Msg;
    }
}

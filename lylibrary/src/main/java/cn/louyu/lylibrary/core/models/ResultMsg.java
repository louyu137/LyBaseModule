package cn.louyu.lylibrary.core.models;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by LOUYU on 2018/4/12.
 */

public class ResultMsg implements Serializable {
    public boolean Success;
    public String Msg;
    public JSONObject Data;
    public int Status;
}

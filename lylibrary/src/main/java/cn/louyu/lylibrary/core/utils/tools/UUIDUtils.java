package cn.louyu.lylibrary.core.utils.tools;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}

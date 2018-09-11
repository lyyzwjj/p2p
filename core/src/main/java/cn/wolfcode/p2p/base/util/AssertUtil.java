package cn.wolfcode.p2p.base.util;

import java.math.BigDecimal;

import cn.wolfcode.p2p.base.exception.DisplayableException;

/**
 * Created by WangZhe on 2018/7/20.
 */
public class AssertUtil {
    /**
     *
     * @param param
     * @param message
     */
    public static void isNotNull(String param,String message){
        if (param==null || "".equals(param)){
            throw new DisplayableException(message);
        }
    }
    public static void isNotNull(BigDecimal param, String message){
        if (param==null || "".equals(param)){
            throw new DisplayableException(message);
        }
    }
    public static void isNotNull(Integer param,String message){
        if (param==null || "".equals(param)){
            throw new DisplayableException(message);
        }
    }
    public static void isNotNull(Object param,String message){
    	if (param==null || "".equals(param)){
    		throw new DisplayableException(message);
    	}
    }
    public static void isTrue(boolean param,String message){
    	if (param==false){
    		throw new DisplayableException(message);
    	}
    }

}

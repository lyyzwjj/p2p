package cn.wolfcode.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by WangZhe on 2018/7/20.
 */
@Getter
@Setter
public class JSONResult {
    private boolean success = true;
    private String message;
    public JSONResult(){}
    public JSONResult(String message){
        this.message = message;
    }
    public JSONResult(boolean success,String message){
        this.success = success;
        this.message = message;
    }
}

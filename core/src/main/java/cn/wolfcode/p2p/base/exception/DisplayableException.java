package cn.wolfcode.p2p.base.exception;

/**
 * Created by WangZhe on 2018/7/20.
 */
public class DisplayableException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public DisplayableException() {
        super();
    }
    public DisplayableException(String message) {
        super(message);
    }
    public DisplayableException(String message,Throwable cause) {
        super(message, cause);
    }
}

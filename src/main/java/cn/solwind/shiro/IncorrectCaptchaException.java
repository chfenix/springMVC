package cn.solwind.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
* @author Zln
* @version 2018-12-20 15:15
* 
* 校验码异常
*/

public class IncorrectCaptchaException extends AuthenticationException {
	
	private static final long serialVersionUID = -3118977367857951328L;

	public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}

package cn.solwind.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
* @author Zln
* @version 2019-01-03 14:07
* 
* 拓展登陆验证字段
*/

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 8145371964801899608L;
	
	//验证码字符串
    private String captcha;

    public CaptchaUsernamePasswordToken(String username, char[] password, boolean rememberMe, String captcha) {
        super(username,password,rememberMe);
        this.captcha = captcha;
    }


    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}

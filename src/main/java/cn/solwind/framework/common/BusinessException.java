package cn.solwind.framework.common;

import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -9285694032800371L;

	private String code;

	private String message;
	
	private String appendMsg;

	private static Properties P;
	
	private static final String PFILE_BUSINESS_EXCETPION = "business_exception.properties";
	
	private static final String PRFIX_BUSINESS_EXCEPTION = "business_exception_";

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String code) {
		this.code = code;
	}
	
	public BusinessException(String code,String message){
		this.code=code;
		this.message=message;
	}
	
	public BusinessException(String code,String message,Throwable cause){
		super(cause);
		this.code=code;
		this.message=message;
	}
	
	public BusinessException appendMsg(String appendMsg) {
		this.appendMsg = appendMsg;
		return this;
	}

	public String getMessage() {
		if(StringUtils.isBlank(this.message)) {
			// 消息为空，尝试获取business_excetpion.properties文件异常信息
			try {
				if(P == null) {
					P = new Properties();
					P.load(new InputStreamReader(BusinessException.class.getClassLoader().getResourceAsStream(PFILE_BUSINESS_EXCETPION), "UTF-8"));
				}
				if (P.containsKey(PRFIX_BUSINESS_EXCEPTION + this.code)) {
					this.message = new String(P.getProperty(PRFIX_BUSINESS_EXCEPTION + this.code).getBytes("UTF-8"), "UTF-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return this.message + StringUtils.defaultString(this.appendMsg);
	}

	public String getCode() {
		return this.code;
	}
}

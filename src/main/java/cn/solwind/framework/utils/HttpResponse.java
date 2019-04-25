package cn.solwind.framework.utils;

/**
* @author Zhouluning
* @version 创建时间：2019-04-12
*
* 类说明
*/

public class HttpResponse {
	
	 /** http status */
    private Integer code;
    /** http response content */
    private String body;

    public HttpResponse() { }

    public HttpResponse(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

package cn.solwind.framework.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * HttpClient 工具类
 * 
 */
@Component
public class HttpClientUtils {
	
	Logger log = LogManager.getLogger();

	// 编码格式。发送编码格式统一用UTF-8
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	@Autowired
    private CloseableHttpClient closeableHttpClient;

    @Autowired
    private RequestConfig config;
    
    // 以下设置为不使用Spring托管方式时使用
    // 设置连接超时时间，单位毫秒。
 	private static final int CONNECT_TIMEOUT = 6000;
 	
 	// 请求获取数据的超时时间(即响应时间)，单位毫秒。
 	private static final int SOCKET_TIMEOUT = 6000;
    
 	/**
 	 * 不使用Spring托管时获取实例
 	 * @return
 	 */
    public static HttpClientUtils getInstance() {
    	/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。
		 * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
		 * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
    	HttpClientUtils httpClient = new HttpClientUtils();
    	httpClient.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build());
    	httpClient.setCloseableHttpClient(HttpClients.createDefault());
		
		return httpClient;
    }
    
	/**
	 * 发送get请求；不带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @return HttpClientResult
	 * @throws Exception
	 */
	public HttpClientResult doGet(String url) throws Exception {
		return doGet(url, null, null);
	}
	
	/**
	 * 发送get请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
		return doGet(url, null, params);
	}
	
	/**
     * 用于JSON格式的get请求
     * @param url url地址
     * @param params  请求参数
     * @param clazz 接口返回值的类型
     * @return
     * @throws Exception
     */
    public <T> T doGet4JSON(String url, Map<String, String> params, Class<T> clazz) throws Exception {
		return doGet4JSON(url, null, params, clazz);
    }
    
    /**
     * 用于JSON格式的get请求
     * @param url url地址
     * @param headers 请求头集合
     * @param params  请求参数
     * @param clazz 接口返回值的类型
     * @return
     * @throws Exception
     */
    public <T> T doGet4JSON(String url, Map<String, String> headers, Map<String, String> params, Class<T> clazz) throws Exception {
		String responseJson = doGet4JSON(url, headers, params);
        T response = JSONObject.parseObject(responseJson, clazz);
        return response;
    }
    
    /**
     * 用于JSON格式的get请求
     * @param url url地址
     * @param params  请求参数
     * @return
     * @throws Exception
     */
    public String doGet4JSON(String url, Map<String, String> params) throws Exception {
		return doGet4JSON(url, null, params);
    }
    
    /**
     * 用于JSON格式的get请求
     * @param url url地址
     * @param headers 请求头集合
     * @param params  请求参数
     * @return
     * @throws Exception
     */
    public String doGet4JSON(String url, Map<String, String> headers,Map<String, String> params) throws Exception {
    	
    	if(headers == null) {
    		headers = new HashMap<String, String>();	
    	}
        
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        
        return doGet(url,headers,params).getContent();
    }

	/**
	 * 发送get请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// 创建访问的地址
		URIBuilder uriBuilder = new URIBuilder(url);
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		// 创建http对象
		log.debug("Request Get Url {}",uriBuilder.build());
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setConfig(config);
		
		// 设置请求头
		packageHeader(headers, httpGet);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, closeableHttpClient, httpGet);
		} finally {
			// 释放资源
			release(httpResponse, closeableHttpClient);
		}
	}

	/**
	 * 发送post请求；不带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doPost(String url) throws Exception {
		return doPost(url, null, null);
	}
	
	/**
	 * 发送post请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doPost(String url, Map<String, String> params) throws Exception {
		return doPost(url, null, params);
	}
	
	/**
	 * 用于JSON格式的post请求
	 * 
	 * @param url  请求地址
	 * @param params  请求头集合
	 * @param clazz  接口返回值的类型
	 * @return
	 * @throws Exception
	 */
	public <T> T doPost4JSON(String url, Map<String, String> params, Class<T> clazz) throws Exception {
        return doPost4JSON(url,null,params,clazz);
    }
	
	/**
	 * 用于JSON格式的post请求
	 * 
	 * @param url  请求地址
	 * @param headers 请求头集合
	 * @param params  请求头集合
	 * @param clazz  接口返回值的类型
	 * @return
	 * @throws Exception
	 */
	public <T> T doPost4JSON(String url, Map<String, String> headers, Map<String, String> params, Class<T> clazz) throws Exception {
        
        // 创建http对象
 		HttpPost httpPost = new HttpPost(url);
 		httpPost.setConfig(config);
 		// 设置请求头
 		if(headers == null) {
			headers = new HashMap<String, String>();
		}
		
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
 		packageHeader(headers, httpPost);
 		
		if (params != null) {
			String requestBody = JSONObject.toJSONString(params);
			StringEntity postEntity = new StringEntity(requestBody, DEFAULT_CHARSET);
			httpPost.setEntity(postEntity);
		}

 		// 创建httpResponse对象
 		CloseableHttpResponse httpResponse = null;

 		try {
 			// 执行请求并获得响应结果
 			HttpClientResult result = getHttpClientResult(httpResponse, closeableHttpClient, httpPost);
 			T response = JSONObject.parseObject(result.getContent(), clazz);
 			return response;
 		} finally {
 			// 释放资源
 			release(httpResponse, closeableHttpClient);
 		}
    }

	/**
	 * 发送post请求；带请求头和请求参数
	 * 
	 * @param url 请求地址
	 * @param headers 请求头集合
	 * @param params 请求参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(config);
		// 设置请求头
		packageHeader(headers, httpPost);
		
		// 封装请求参数
		packageParam(params, httpPost);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, closeableHttpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, closeableHttpClient);
		}
	}

	/**
	 * 发送put请求；不带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doPut(String url) throws Exception {
		return doPut(url);
	}

	/**
	 * 发送put请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doPut(String url, Map<String, String> params) throws Exception {
		HttpPut httpPut = new HttpPut(url);

		httpPut.setConfig(config);
		
		packageParam(params, httpPut);

		CloseableHttpResponse httpResponse = null;

		try {
			return getHttpClientResult(httpResponse, closeableHttpClient, httpPut);
		} finally {
			release(httpResponse, closeableHttpClient);
		}
	}

	/**
	 * 发送delete请求；不带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doDelete(String url) throws Exception {
		HttpDelete httpDelete = new HttpDelete(url);

		httpDelete.setConfig(config);

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, closeableHttpClient, httpDelete);
		} finally {
			release(httpResponse, closeableHttpClient);
		}
	}

	/**
	 * 发送delete请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult doDelete(String url, Map<String, String> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, String>();
		}

		params.put("_method", "delete");
		return doPost(url, params);
	}
	
	/**
	 * Description: 封装请求头
	 * @param params
	 * @param httpMethod
	 */
	public void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
		// 封装请求头
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				// 设置到请求头到HttpRequestBase对象中
				httpMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Description: 封装请求参数
	 * 
	 * @param params
	 * @param httpMethod
	 * @throws UnsupportedEncodingException
	 */
	public void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
			throws UnsupportedEncodingException {
		// 封装请求参数
		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			// 设置到请求的http对象中
			httpMethod.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
		}
	}

	/**
	 * Description: 获得响应结果
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
			CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
		// 执行请求
		httpResponse = httpClient.execute(httpMethod);

		// 获取返回结果
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = "";
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString(httpResponse.getEntity(), DEFAULT_CHARSET);
			}
			return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
		}
		return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Description: 释放资源
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	public void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		// 释放资源
		if (httpResponse != null) {
			httpResponse.close();
		}
		// 使用连接池时不关闭httpclient，不然再次调用会导致报错
		/*if (httpClient != null) {
			httpClient.close();
		}*/
	}

	public void setCloseableHttpClient(CloseableHttpClient closeableHttpClient) {
		this.closeableHttpClient = closeableHttpClient;
	}

	public void setConfig(RequestConfig config) {
		this.config = config;
	}

}
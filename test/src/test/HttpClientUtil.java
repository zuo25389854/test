package test;

import java.io.IOException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
//import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Module: HttpClientUtil.java Description:
 * 以get/post的方式发送数据到指定的http接口---利用httpclient.jar包---HTTP接口的调用 Company: Author:
 * ptp Date: Feb 22, 2012
 */

public class HttpClientUtil {

	private static final Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * get方式
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String getHttp(String param1, String param2) {
		String strUrl = "http://apidata.datatang.com/data/traffic_violation";
		// dtkey 通过页面申请的API KEY。(必须项目)
		String strKey = "XXXXXXXXXXXXXXXXXXXXXX";
		// apicode 各API的代码 (必须项目)
		String strApicode = "traffic_violation";
		// rettype 需要返回的格式（支持XML及JSON）(必须项目)
		String strRettype = "json";
		// 各API需要参数（详细参考画面-各API参数不同）
		String strparam = "";
		// 例如
		strparam = "cartype=02&carnumber=晋A376A7&cardrivenumber=L021886&carcode=LFMABE6A8E0022424";
		// 访问URL地址
		String url = strUrl + "?apikey=" + strKey + "&apicode=" + strApicode
				+ "&rettype=" + strRettype + "&" + strparam;
		System.out.println("*******************" + url);

		String responseMsg = "";

		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		// 用于测试的http接口的url
		// String
		// url="http://localhost:8080/UpDown/httpServer?param1="+param1+"&param2="+param2;

		// 2.创建GetMethod的实例
		GetMethod getMethod = new GetMethod(url);

		// 使用系统系统的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		try {
			// 3.执行getMethod,调用http接口
			httpClient.executeMethod(getMethod);

			// 4.读取内容
			byte[] responseBody = getMethod.getResponseBody();

			// 5.处理返回的内容
			responseMsg = new String(responseBody);
			log.info(responseMsg);

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 6.释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * post方式
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String postHttp(String param1, String param2) {
		String responseMsg = "";

		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		httpClient.getParams().setContentCharset("GBK");

		String url = "http://localhost:8080/UpDown/httpServer";

		// 2.构造PostMethod的实例
		PostMethod postMethod = new PostMethod(url);

		// 3.把参数值放入到PostMethod对象中
		// 方式1：
		/*
		 * NameValuePair[] data = { new NameValuePair("param1", param1), new
		 * NameValuePair("param2", param2) }; postMethod.setRequestBody(data);
		 */

		// 方式2：
		postMethod.addParameter("param1", param1);
		postMethod.addParameter("param2", param2);

		try {
			// 4.执行postMethod,调用http接口
			httpClient.executeMethod(postMethod);// 200

			// 5.读取内容
			responseMsg = postMethod.getResponseBodyAsString().trim();
			log.info(responseMsg);

			// 6.处理返回的内容

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * 测试的main方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String param1 = "111";
		String param2 = "222";
		// get
		// System.out.println("get方式调用http接口\n"+getHttp(param1, param2));

		// post
		System.out.println("post方式调用http接口\n" + getHttp(param1, param2));
	}
}

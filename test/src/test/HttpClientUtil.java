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
 * ��get/post�ķ�ʽ�������ݵ�ָ����http�ӿ�---����httpclient.jar��---HTTP�ӿڵĵ��� Company: Author:
 * ptp Date: Feb 22, 2012
 */

public class HttpClientUtil {

	private static final Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * get��ʽ
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String getHttp(String param1, String param2) {
		String strUrl = "http://apidata.datatang.com/data/traffic_violation";
		// dtkey ͨ��ҳ�������API KEY��(������Ŀ)
		String strKey = "XXXXXXXXXXXXXXXXXXXXXX";
		// apicode ��API�Ĵ��� (������Ŀ)
		String strApicode = "traffic_violation";
		// rettype ��Ҫ���صĸ�ʽ��֧��XML��JSON��(������Ŀ)
		String strRettype = "json";
		// ��API��Ҫ��������ϸ�ο�����-��API������ͬ��
		String strparam = "";
		// ����
		strparam = "cartype=02&carnumber=��A376A7&cardrivenumber=L021886&carcode=LFMABE6A8E0022424";
		// ����URL��ַ
		String url = strUrl + "?apikey=" + strKey + "&apicode=" + strApicode
				+ "&rettype=" + strRettype + "&" + strparam;
		System.out.println("*******************" + url);

		String responseMsg = "";

		// 1.����HttpClient��ʵ��
		HttpClient httpClient = new HttpClient();

		// ���ڲ��Ե�http�ӿڵ�url
		// String
		// url="http://localhost:8080/UpDown/httpServer?param1="+param1+"&param2="+param2;

		// 2.����GetMethod��ʵ��
		GetMethod getMethod = new GetMethod(url);

		// ʹ��ϵͳϵͳ��Ĭ�ϵĻָ�����
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		try {
			// 3.ִ��getMethod,����http�ӿ�
			httpClient.executeMethod(getMethod);

			// 4.��ȡ����
			byte[] responseBody = getMethod.getResponseBody();

			// 5.�����ص�����
			responseMsg = new String(responseBody);
			log.info(responseMsg);

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 6.�ͷ�����
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * post��ʽ
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String postHttp(String param1, String param2) {
		String responseMsg = "";

		// 1.����HttpClient��ʵ��
		HttpClient httpClient = new HttpClient();

		httpClient.getParams().setContentCharset("GBK");

		String url = "http://localhost:8080/UpDown/httpServer";

		// 2.����PostMethod��ʵ��
		PostMethod postMethod = new PostMethod(url);

		// 3.�Ѳ���ֵ���뵽PostMethod������
		// ��ʽ1��
		/*
		 * NameValuePair[] data = { new NameValuePair("param1", param1), new
		 * NameValuePair("param2", param2) }; postMethod.setRequestBody(data);
		 */

		// ��ʽ2��
		postMethod.addParameter("param1", param1);
		postMethod.addParameter("param2", param2);

		try {
			// 4.ִ��postMethod,����http�ӿ�
			httpClient.executeMethod(postMethod);// 200

			// 5.��ȡ����
			responseMsg = postMethod.getResponseBodyAsString().trim();
			log.info(responseMsg);

			// 6.�����ص�����

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7.�ͷ�����
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * ���Ե�main����
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String param1 = "111";
		String param2 = "222";
		// get
		// System.out.println("get��ʽ����http�ӿ�\n"+getHttp(param1, param2));

		// post
		System.out.println("post��ʽ����http�ӿ�\n" + getHttp(param1, param2));
	}
}

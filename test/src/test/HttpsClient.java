package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class HttpsClient {
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	public static String get(String url, String charset, String keystore,String keystorePassword, String truestore, String truestorePassword){
		StringBuffer strb = new StringBuffer();
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(keystore), keystorePassword.toCharArray());
			tks.load(new FileInputStream(truestore), truestorePassword.toCharArray());
			kmf.init(ks, keystorePassword.toCharArray());
			tmf.init(tks);
			sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("GET");
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				strb.append(line);
			}
		} catch (Exception e) {
		}
		return strb.toString();
	}

	public static String post(String url, String charset, String keystore,String keystorePassword, String truestore,String truestorePassword, String content){
		StringBuffer buffer = new StringBuffer();
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(keystore), keystorePassword.toCharArray());
			tks.load(new FileInputStream(truestore), truestorePassword.toCharArray());
			kmf.init(ks, keystorePassword.toCharArray());
			tmf.init(tks);
			sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(content.getBytes(charset));
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}

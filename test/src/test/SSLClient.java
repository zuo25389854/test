package test;  
  
import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.security.KeyStore;  
  
import javax.net.ssl.KeyManagerFactory;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocket;  
import javax.net.ssl.TrustManagerFactory;  
  
/**  
 * SSL Client  
 *   
 */  
public class SSLClient {  
  
    private static final String DEFAULT_HOST                    = "127.0.0.1";  
    private static final int    DEFAULT_PORT                    = 7777;  
  
    private static final String CLIENT_KEY_STORE_PASSWORD       = "123456";  
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";  
  
    private SSLSocket           sslSocket;  
  
    /**  
     * �����ͻ��˳���  
     *   
     * @param args  
     */  
    public static void main(String[] args) {  
        SSLClient client = new SSLClient();  
        client.init();  
        client.process();  
    }  
  
    /**  
     * ͨ��ssl socket�����˽�������,���ҷ���һ����Ϣ  
     */  
    public void process() {  
        if (sslSocket == null) {  
            System.out.println("ERROR");  
            return;  
        }  
        try {  
            InputStream input = sslSocket.getInputStream();  
            OutputStream output = sslSocket.getOutputStream();  
  
            BufferedInputStream bis = new BufferedInputStream(input);  
            BufferedOutputStream bos = new BufferedOutputStream(output);  
  
            bos.write("Client Message".getBytes());  
            bos.flush();  
  
            byte[] buffer = new byte[20];  
            bis.read(buffer);  
            System.out.println(new String(buffer));  
  
            sslSocket.close();  
        } catch (IOException e) {  
            System.out.println(e);  
        }  
    }  
  
    /**  
     * <ul>  
     * <li>ssl���ӵ��ص�:</li>  
     * <li>��ʼ��SSLSocket</li>  
     * <li>����ͻ���˽ԿKeyStore������ͻ��������ε�KeyStore(����˵�֤��)</li>  
     * </ul>  
     */  
    public void init() {  
        try {  
            SSLContext ctx = SSLContext.getInstance("SSL");  
  
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");  
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");  
  
            KeyStore ks = KeyStore.getInstance("JKS");  
            KeyStore tks = KeyStore.getInstance("JKS");  
  
            ks.load(new FileInputStream("E://kclient.keystore"), CLIENT_KEY_STORE_PASSWORD.toCharArray());  
            tks.load(new FileInputStream("E://tclient.keystore"), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());  
  
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());  
            tmf.init(tks);  
  
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);  
  
            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
    }  
  
}  
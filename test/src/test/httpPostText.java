package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.Test;
 
public class httpPostText {
    public static final String GET_URL = "http://apidata.datatang.com/data/traffic_violation?apikey=XXXXXXXXXXXXXXXXXXXXXX&apicode=traffic_violation&rettype=json&cartype=02&carnumber=��A376A7&cardrivenumber=L021886&carcode=LFMABE6A8E0022424";
    public static final String POST_URL = "http://218.244.147.187:8080/dwcedi/ife";
//    public static final String POST_URL = "https://218.244.147.187:8080/dwcedi/ife";
    //get()���� 
    //http://www.kuaidi100.com/query?type=shunfeng&postid=58485485654887
    
    public void readContentFromGet()throws IOException{
        // ƴ��get�����URL�ִ���ʹ��URLEncoder.encode������Ͳ��ɼ��ַ����б��� 
        String getURL = GET_URL +"&activatecode="+URLEncoder.encode("�й�����", "utf-8");
        URL getUrl =new URL(GET_URL);
        // ����ƴ�յ�URL�������ӣ�URL.openConnection���������URL�����ͣ�
        // ���ز�ͬ��URLConnection����Ķ�������URL��һ��http�����ʵ�ʷ��ص���HttpURLConnection 
        HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
        // �������ӣ�����ʵ����get requestҪ����һ���connection.getInputStream()�����вŻ���������
        // ������ 
        connection.connect();
        // ȡ������������ʹ��Reader��ȡ 
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
        //���ñ���,������������ 
        System.out.println("=============================");
        System.out.println("Contents of get request");
        System.out.println("=============================");
        String lines;
        while((lines =reader.readLine())!=null){
            //lines = new String(lines.getBytes(), "utf-8"); 
            System.out.println(lines);
        }
        reader.close();
        // �Ͽ����� 
        connection.disconnect();
        System.out.println("=============================");
        System.out.println("Contents of get request ends");
        System.out.println("=============================");
    }

     //post()���� 
     @Test
     public void readContentFromPost()throws IOException{
        // Post�����url����get��ͬ���ǲ���Ҫ������ 
        URL postUrl = new URL(POST_URL);
        // ������ 
        HttpURLConnection connection = (HttpURLConnection)postUrl.openConnection();
        // �����Ƿ���connection�������Ϊ�����post���󣬲���Ҫ����
        // http�����ڣ������Ҫ��Ϊtrue 
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        // Post ������ʹ�û��� 
        connection.setUseCaches(false);
        // URLConnection.setFollowRedirects��static���������������е�URLConnection����
        // URLConnection.setInstanceFollowRedirects�ǳ�Ա�������������ڵ�ǰ���� 
        connection.setInstanceFollowRedirects(true);
        // ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
        // ��˼��������urlencoded�������form�������������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode
        // ���б��� 
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        // ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
        // Ҫע�����connection.getOutputStream�������Ľ���connect�� 
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        // The URL-encoded contend
        // ���ģ�����������ʵ��get��URL��'?'��Ĳ����ַ���һ�� 
        String content ="method=getOrder&appkey=64c22efe5bf6311c8c656abb61ecceed&sign=4297f44b13955235245b2497399d7a93&data={'vendorcode':'V12345'}";
        // DataOutputStream.writeBytes���ַ����е�16λ��unicode�ַ���8λ���ַ���ʽд�������� 
        out.writeBytes(content);
        out.flush();
        out.close();
 // flush and close 
        BufferedReader reader =new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//���ñ���,������������ 
        String line="";
        System.out.println("=============================");
        while((line =reader.readLine())!=null){
            //line = new String(line.getBytes(), "utf-8"); 
            System.out.println(line);
        }
        System.out.println("=============================");
        reader.close();
        connection.disconnect();
    }
}
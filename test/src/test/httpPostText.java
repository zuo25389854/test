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
    public static final String GET_URL = "http://apidata.datatang.com/data/traffic_violation?apikey=XXXXXXXXXXXXXXXXXXXXXX&apicode=traffic_violation&rettype=json&cartype=02&carnumber=晋A376A7&cardrivenumber=L021886&carcode=LFMABE6A8E0022424";
    public static final String POST_URL = "http://218.244.147.187:8080/dwcedi/ife";
//    public static final String POST_URL = "https://218.244.147.187:8080/dwcedi/ife";
    //get()请求 
    //http://www.kuaidi100.com/query?type=shunfeng&postid=58485485654887
    
    public void readContentFromGet()throws IOException{
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码 
        String getURL = GET_URL +"&activatecode="+URLEncoder.encode("中国聚龙", "utf-8");
        URL getUrl =new URL(GET_URL);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection 
        HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器 
        connection.connect();
        // 取得输入流，并使用Reader读取 
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
        //设置编码,否则中文乱码 
        System.out.println("=============================");
        System.out.println("Contents of get request");
        System.out.println("=============================");
        String lines;
        while((lines =reader.readLine())!=null){
            //lines = new String(lines.getBytes(), "utf-8"); 
            System.out.println(lines);
        }
        reader.close();
        // 断开连接 
        connection.disconnect();
        System.out.println("=============================");
        System.out.println("Contents of get request ends");
        System.out.println("=============================");
    }

     //post()请求 
     @Test
     public void readContentFromPost()throws IOException{
        // Post请求的url，与get不同的是不需要带参数 
        URL postUrl = new URL(POST_URL);
        // 打开连接 
        HttpURLConnection connection = (HttpURLConnection)postUrl.openConnection();
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true 
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        // Post 请求不能使用缓存 
        connection.setUseCaches(false);
        // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
        // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数 
        connection.setInstanceFollowRedirects(true);
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码 
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。 
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致 
        String content ="method=getOrder&appkey=64c22efe5bf6311c8c656abb61ecceed&sign=4297f44b13955235245b2497399d7a93&data={'vendorcode':'V12345'}";
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面 
        out.writeBytes(content);
        out.flush();
        out.close();
 // flush and close 
        BufferedReader reader =new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//设置编码,否则中文乱码 
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
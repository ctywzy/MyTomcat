package niotomcat.tomcatobj;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServletRequest {
    private static final int BYTE = 1024;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求地址
     */
    private String url;

    private String jsession = null;

    /**
     * 请求体
     */
    private String content;

    /**
     * 请求头
     */
    private Map<String, String> headers = new HashMap<>();

    /**
     * 请求参数
     */
    private Map<String,String> parameter = new HashMap<>();

    /**
     * 请求cookie信息
     */
    private List<Cookie> cookies = new ArrayList<>();

    public String getMethod(){
        return this.method;
    }

    public String getJsession(){
        return this.jsession;
    }

    public List<Cookie> getCookies(){
        return this.cookies;
    }

    public String getParameter(String key){
        return parameter.get(key);
    }

    public String getUrl(){
        return this.url;
    }

    /**
     * 处理请求信息
     * @param inputStream
     */
    public HttpServletRequest(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = -1;
        //处理字节上限
        byte[] bytes = new byte[100 * BYTE];

        try{
            length = inputStream.read(bytes);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("读取客户请求异常");
        }

        //将bytes中的字节转化为char

        for(int i = 0; i< length; i++){
            stringBuilder.append((char)bytes[i]);
        }

        content = stringBuilder.toString();
    }
}

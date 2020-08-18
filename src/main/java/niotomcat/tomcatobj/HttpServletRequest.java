package niotomcat.tomcatobj;

import niotomcat.common.Constant;
import niotomcat.server.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class HttpServletRequest {


    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求地址
     */
    private String uri;

    private String jsessionid = null;

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

    public String getJsessionid() {
        return this.jsessionid;
    }

    public List<Cookie> getCookies(){
        return this.cookies;
    }

    public String getParameter(String key){
        return parameter.get(key);
    }

    public String getUri(){
        return this.uri;
    }

    /**
     * 处理请求信息
     * @param inputStream
     */
    public HttpServletRequest(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = -1;
        //处理字节上限
        byte[] bytes = new byte[100 * Constant.BYTE];

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

    public HttpSession getSession(){
        HttpSession session;

        //若不存在或者不在这次连接中，创建再返回
        if(jsessionid == null || !ServletContext.sessions.containsKey(jsessionid)){

            jsessionid = UUID.randomUUID().toString();

            session = new HttpSession(jsessionid);
            ServletContext.sessions.put(jsessionid,session);

        }else{

            return ServletContext.sessions.get(jsessionid);
        }

        return session;
    }

}

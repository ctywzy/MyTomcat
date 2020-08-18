package niotomcat.tomcatobj;

import niotomcat.common.Constant;

import java.io.*;
import java.util.Date;

public class HttpServletResponse {

    private HttpServletRequest httpServletRequest;
    private OutputStream outputStream;

    // 定义资源地址
    private static final String WEB_ROOT_PATH = System.getProperty("user.dir") + File.separator + "static";

    public HttpServletResponse(HttpServletRequest httpServletRequest, OutputStream outputStream) {
        this.httpServletRequest = httpServletRequest;
        this.outputStream = outputStream;
    }

    /**
     * 响应文件输出HTML，JPEG，gif
     * 响应码404,500,200
     */
    public void sendRedirect() {

        // response中的网络状态码
        int code = 200;
        //1.取出request中的uri
        String uri = this.httpServletRequest.getUri();
        File file;
        String fileName;
        if(uri.endsWith("/")){
            fileName = uri + Constant.INDEX;
        }else{
            fileName = uri;
        }

        file = new File(WEB_ROOT_PATH, fileName);
        // 2. 拼装file，查看是否存在
        // 不存在则404

        if(!file.exists()){
            file = new File(WEB_ROOT_PATH, Constant.CNAT_FIND);
            code = 404;
        }
        // 3 存在找到这个文件，读出来
        // 4 发送文件响应，不同的文件返回不同类型
        // TODO 选择结美化 ,截取.号后的字符，并改为switch
        if(file.getName().endsWith(".jpg")){
            send(file,"application/x-jpg",code);
        }else if(file.getName().endsWith(".jpe")||file.getName().endsWith(".jpeg")){
            send(file,"image/jpeg",code);
        }else if(file.getName().endsWith(".gif")){
            send(file,"image/gif",code);
        }else if(file.getName().endsWith(".css")){
            send(file,"text/css",code);
        }else if(file.getName().endsWith(".js")){
            send(file,"application/x-javascript",code);
        }else if(file.getName().endsWith(".swf")){
            send(file,"application/x-shockwave-flash",code);
        }else{
            send(file,"text/html",code);
        }
    }

    /**
     * 返回响应流
     * @param file
     * @param contentType
     * @param code
     */
    private void send(File file, String contentType, int code) {
        try{
            String responseHeaders = splicProtocol(file.length(), contentType, code);
            byte[] bytes = readFile(file);
            outputStream.write(responseHeaders.getBytes());
            outputStream.flush();
            outputStream.write(bytes);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件信息
     * @param file
     * @return
     */
    private byte[] readFile(File file) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[Constant.BYTE];
            int length;
            while((length = fis.read(bytes, 0, bytes.length)) != -1){
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

    /**
     * 拼接协议
     * @param length
     * @param contentType
     * @param code
     * @return
     */
    private String splicProtocol(long length, String contentType, int code) {

        String result = "HTTP/1.1 "+code+" OK\r\n";
        result+="Server: myTomcat\r\n";
        result+="Content-Type: "+contentType+";charset=utf-8\r\n";
        result+="Content-Length: "+length+"\r\n";
        result+="Date: "+new Date()+"\r\n";
        result+="\r\n";
        return result;
    }

    public PrintWriter getWriter() {
        return new PrintWriter(this.outputStream);
    }
}

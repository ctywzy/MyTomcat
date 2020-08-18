package niotomcat.server;

import niotomcat.server.impl.DynamicProcessor;
import niotomcat.server.impl.StaticProcessor;
import niotomcat.tomcatobj.HttpServletRequest;
import niotomcat.tomcatobj.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerService implements Runnable{

    private Socket socket;

    ServerService(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("打印线程编号：" + Thread.currentThread().getId());
        InputStream inputStream;
        OutputStream outputStream;

        try{
            inputStream = this.socket.getInputStream();
            outputStream = this.socket.getOutputStream();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("客户端"+this.socket.getRemoteSocketAddress()+"掉线");
            return;
        }
        // 处理http请求，http请求是一个基于请求与响应的协议
        // 创建一个请求对象
        HttpServletRequest httpServletRequest = new HttpServletRequest(inputStream);
        // 创建一个响应对象
        HttpServletResponse httpServletResponse = new HttpServletResponse(httpServletRequest, outputStream);
        String uri = httpServletRequest.getUrl();
        // 判断是动态还是静态请求
        Processor processor;

        if(uri.indexOf(".action") > 0){
            processor = new DynamicProcessor();
        }else {
            processor = new StaticProcessor();
        }

        processor.process(httpServletRequest, httpServletResponse);

        try{
            //http特性，无状态短连接
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

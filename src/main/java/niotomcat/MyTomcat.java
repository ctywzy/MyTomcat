package niotomcat;

import niotomcat.server.ServerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyTomcat {


    private void startServer(){

        System.out.println("准备启动tomcat");
        // TODO 这里可以搞一个类似xml配置文件的东西，用来读取端口信息

        int port = getPort();
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(port);
            System.out.println("服务启动成功，正在监听" + port + "端口");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(port + "端口号被占用");
            System.exit(0);
        }

        //监听请求
        while(true){
            listen(serverSocket);
        }

    }

    public void listen(ServerSocket serverSocket){
        Socket socket = null;
        try{
            socket = serverSocket.accept();
            /**
             * 这里用到的是一套堵塞式的请求，只有建立连接，这个县城才会进行下面的操作
             */
            System.out.println("线程号"+Thread.currentThread().getId()+"正在操作");
            System.out.println("客户端"+socket.getRemoteSocketAddress()+"连接成功");

            ServerService serverService = new ServerService(socket);
            // TODO 改成利用线程池技术来进行连接管理

            Thread t = new Thread(serverService);

            t.start();

        } catch (IOException e){
            e.printStackTrace();

            System.out.println("客户端"+socket.getRemoteSocketAddress()+"掉线了");
            System.out.println("");
        }
    }
    private int getPort() {
        return 8080;
    }


    public static void main(String[] args) {
        new MyTomcat().startServer();
    }
}

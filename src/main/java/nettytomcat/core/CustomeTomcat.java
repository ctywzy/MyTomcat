package nettytomcat.core;

import nettytomcat.custome.CustomServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomeTomcat {
    private Integer PORT = 8080;
    private Map<String, CustomServlet> servletMap = new HashMap<>();
    private Properties webXml = new Properties();
    private static final String CLASS_PATH_PRE = "nettytomcat.servlet.";

    public void init(){
        try{
            String WEB_INF = this.getClass().getResource("/webxml").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "/web.properties");
            webXml.load(fis);
            for(Object key : webXml.keySet()){
                String keyString = key.toString();
                if(keyString.endsWith("url")){
                    String mapKey = webXml.getProperty(keyString);
                    String servletClassName = webXml.getProperty(keyString.replace(".url",".class"));
                    CustomServlet customServlet = (CustomServlet) Class.forName(CLASS_PATH_PRE + servletClassName).newInstance();
                    servletMap.put(mapKey, customServlet);
                }
            }
        }catch (Exception e){
            System.out.println("初始化错误:" + e.getCause().getCause());
        }
    }

    /**
     * 启动netty
     */
    private void doStart() {
        init();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();

        try{
            server.group(bossGroup, workGroup)
                  .channel(NioServerSocketChannel.class)
                  // 客户端连接时启动
                  .childHandler(new ChannelInitializer<SocketChannel>() {

                      @Override
                      protected void initChannel(SocketChannel socketChannel) throws Exception {
                          //相应编码器
                          socketChannel.pipeline().addLast(new HttpResponseEncoder());
                          //请求编码器
                          socketChannel.pipeline().addLast(new HttpRequestDecoder());
                          //自定义处理器
                          socketChannel.pipeline().addLast(new TomcatHandler(servletMap));
                      }
                  })
                  .option(ChannelOption.SO_BACKLOG, 128)
                  .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = server.bind(PORT).sync();
            System.out.println("测试tomcat启动");
            //监听关闭状态启动
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            bossGroup.shutdownGracefully();

            workGroup.shutdownGracefully();
        }
    }

    public void start(){
        doStart();
    }

    public void start(int port){
        this.PORT = port;
        doStart();
    }

    public static void main(String[] args) {
        new CustomeTomcat().start();
    }
}

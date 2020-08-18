package nettytomcat.core;

import nettytomcat.custome.CustomServlet;
import nettytomcat.custome.CustomeRequest;
import nettytomcat.custome.CustomeResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class TomcatHandler extends ChannelInboundHandlerAdapter {

    private Map<String, CustomServlet> servletMap = new HashMap<>();

    TomcatHandler(Map<String, CustomServlet> servletMap){
        this.servletMap = servletMap;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest request = (HttpRequest) msg;

            CustomeRequest customeRequest = new CustomeRequest(ctx, request);

            CustomeResponse customeResponse = new CustomeResponse(ctx, request);

            String url = request.uri();

            if(servletMap.containsKey(url)){
                servletMap.get(url).service(customeRequest,customeResponse);
            }else{
                customeResponse.write("404");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

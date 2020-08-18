package nettytomcat.custome;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * 自定义request类
 */
public class CustomeRequest {

    private ChannelHandlerContext channelHandlerContext;

    private HttpRequest request;

    public CustomeRequest(ChannelHandlerContext channelHandlerContext, HttpRequest request){
        this.channelHandlerContext = channelHandlerContext;
        this.request = request;
    }

    public String getMethod() {
        return request.getMethod().name();
    }
}

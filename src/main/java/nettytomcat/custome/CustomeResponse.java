package nettytomcat.custome;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;

/**
 * 自定义response对象
 */
public class CustomeResponse {

    private ChannelHandlerContext channelHandlerContext;

    private HttpRequest request;

    private static final String code = "UTF-8";

    public CustomeResponse(ChannelHandlerContext channelHandlerContext, HttpRequest request){
        this.request = request;

        this.channelHandlerContext = channelHandlerContext;
    }

    public void write(String outMsg) {

        try{
            if(outMsg == null || outMsg.isEmpty()){
                return ;
            }
            //设置HTTP请求及请求头信息
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(outMsg.getBytes(code)));
            response.headers().set("Content-Type", "text/html");
            channelHandlerContext.write(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            channelHandlerContext.flush();
            channelHandlerContext.close();
        }
    }
}

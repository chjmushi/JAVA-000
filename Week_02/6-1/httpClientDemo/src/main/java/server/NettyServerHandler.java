package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;

/**
 * created by xiayiyang on 2020/10/26
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送的数据
     *
     * @param ctx 上下文对象, 含有通道channel，管道pipeline
     * @param msg 就是客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            System.out.println("服务器读取线程 " + Thread.currentThread().getName());
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String uri = fullHttpRequest.getUri();
            System.out.println("客户端请求uri:"+uri);
            mockHandler(fullHttpRequest,ctx);
        } catch (Exception e) {
            System.out.println("channelRead exception");
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    /**
     * 数据读取完毕处理方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    /**
     * 处理异常, 一般是需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private void mockHandler(FullHttpRequest request,ChannelHandlerContext context) {
        FullHttpResponse response = null;
        String returnValue="success";
        try {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer(returnValue.getBytes("UTF-8")));
            response.headers().set("Content-Type","application/json");
            response.headers().set("Contenr-Length",response.content().readableBytes());
        } catch (Exception e) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.NO_CONTENT);
            System.out.println("mockHandler exception");
            e.printStackTrace();
        } finally {
            context.write(response).addListener(ChannelFutureListener.CLOSE);
//            if (null != request) {
//                if (!HttpUtil.isKeepAlive(request)) {
//                    System.out.println("mockHandler close");
//                } else {
//                    System.out.println("mockHandler keepAlive");
//                    response.headers().set(CONNECTION,KEEP_ALIVE);
//                    context.write(response);
//                }
//            }
        }
    }
}

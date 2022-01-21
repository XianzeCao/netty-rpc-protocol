package com.zaccao.rpc.handler;

import com.zaccao.rpc.core.RequestHolder;
import com.zaccao.rpc.core.RpcFuture;
import com.zaccao.rpc.core.RpcProtocol;
import com.zaccao.rpc.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("receive Rpc Server Result");
        long requestId=msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> future= RequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getContent()) ; //返回结果
    }
}

package com.zaccao.rpc.protocol;

import com.zaccao.rpc.IRegistryService;
import com.zaccao.rpc.ServiceInfo;
import com.zaccao.rpc.core.RpcProtocol;
import com.zaccao.rpc.core.RpcRequest;
import com.zaccao.rpc.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
    /*private String serviceAddress;
    private int servicePort;*/

    public NettyClient() {
//        log.info("begin init Netty Client,{},{}",serviceAddress,servicePort);
        bootstrap=new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

       /* this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;*/
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol, IRegistryService registryService) throws Exception {
        ServiceInfo serviceInfo=registryService.discovery(protocol.getContent().getClassName());
        final ChannelFuture future=bootstrap.connect(serviceInfo.getServiceAddress(),serviceInfo.getServicePort()).sync();
        future.addListener(listener->{
            if(future.isSuccess()){
                log.info("connect rpc server {} success.",serviceInfo.getServiceAddress());
            }else{
                log.error("connect rpc server {} failed. ",serviceInfo.getServiceAddress());
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("begin transfer data");
        future.channel().writeAndFlush(protocol);
    }
}

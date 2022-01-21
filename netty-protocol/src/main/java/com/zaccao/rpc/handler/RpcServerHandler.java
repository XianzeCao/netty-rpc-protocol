package com.zaccao.rpc.handler;

import com.zaccao.rpc.constants.ReqType;
import com.zaccao.rpc.core.Header;
import com.zaccao.rpc.core.RpcProtocol;
import com.zaccao.rpc.core.RpcRequest;
import com.zaccao.rpc.core.RpcResponse;
import com.zaccao.rpc.spring.SpringBeanManager;
import com.zaccao.rpc.spring.service.Mediator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        RpcProtocol<RpcResponse> resProtocol=new RpcProtocol();
        Header header=msg.getHeader();
        header.setReqType(ReqType.RESPONSE.code());
        Object result=Mediator.getInstance().processor(msg.getContent());
//        Object result=invoke(msg.getContent());
        resProtocol.setHeader(header);
        RpcResponse response=new RpcResponse();
        response.setData(result);
        response.setMsg("success");
        resProtocol.setContent(response);

        ctx.writeAndFlush(resProtocol);
    }

    @Deprecated
    private Object invoke(RpcRequest request){
        try {
            Class<?> clazz=Class.forName(request.getClassName());
            Object bean= SpringBeanManager.getBean(clazz);
            Method method=clazz.getDeclaredMethod(request.getMethodName(),request.getParameterTypes());
            return method.invoke(bean,request.getParams());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

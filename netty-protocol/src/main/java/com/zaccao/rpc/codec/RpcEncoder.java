package com.zaccao.rpc.codec;

import com.zaccao.rpc.core.Header;
import com.zaccao.rpc.core.RpcProtocol;
import com.zaccao.rpc.serial.ISerializer;
import com.zaccao.rpc.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("============begin RpcEncoder=========");
        Header header=msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getReqType());
        out.writeLong(header.getRequestId());
        ISerializer serializer=SerializerManager.getSerializer(header.getSerialType());
        byte[] data=serializer.serialize(msg.getContent());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}

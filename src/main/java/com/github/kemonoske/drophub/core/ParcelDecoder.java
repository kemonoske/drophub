package com.github.kemonoske.drophub.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ParcelDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.isReadable()) {
            int fooLen = in.readInt();
            byte[] foo = new byte[fooLen];
            in.readBytes(foo);
            int barLen = in.readInt();
            byte[] bar = new byte[barLen];
            in.readBytes(bar);
            out.add(new Parcel(new String(foo), new String(bar)));
        }
    }
}

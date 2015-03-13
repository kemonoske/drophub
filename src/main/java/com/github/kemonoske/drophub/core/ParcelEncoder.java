package com.github.kemonoske.drophub.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ParcelEncoder extends MessageToByteEncoder<Parcel> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Parcel msg, final ByteBuf out) {
        byte[] foo = msg.getFoo().getBytes();
        out.writeInt(foo.length);
        out.writeBytes(foo);
        byte[] bar = msg.getBar().getBytes();
        out.writeInt(bar.length);
        out.writeBytes(bar);
    }
}
package com.github.kemonoske.drophub.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HubClient {

    private EventLoopGroup group;
    private Channel channel;
    private ChannelFuture lastWriteFuture = null;

    public HubClient(String host, int port) {
        this.group = new NioEventLoopGroup();
        try {//FIXME: Should probably happen on each send() call
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HubClientInitializer());
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        } //Who gives a fuck anyway
    }

    public void send(Parcel msg) {
        lastWriteFuture = channel.writeAndFlush(msg);
    }

    //TODO: This method looks like shit
    public void kill() {
        // Wait until all messages are flushed before closing the channel.
        if (lastWriteFuture != null) {
            try {
                lastWriteFuture.sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        group.shutdownGracefully();
    }
}

package com.example.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class EchoClient {
    private static final Logger LOG = LoggerFactory.getLogger(EchoClient.class);
    private final SocketChannel client;

    public EchoClient(SocketChannel client) {
        this.client = client;
    }

    public static EchoClient createClient(String host, int port) throws IOException {
        LOG.info("New client for {} {}", host, port);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        return new EchoClient(socketChannel);
    }

    public void stop() throws IOException {
        LOG.info("Client close");
        client.close();
    }

    public String sendMessage(String msg) {
        LOG.info("Send message '{}'", msg);
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            String response = new String(buffer.array()).trim();
            LOG.info("Response '{}'", response);
            buffer.clear();
            return response;
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return null;

    }
}

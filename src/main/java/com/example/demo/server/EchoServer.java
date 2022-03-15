package com.example.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);

    private static final String POISON_PILL = "POISON_PILL";

    private final Selector selector;
    private final ServerSocketChannel serverSocket;
    private final ByteBuffer buffer;

    private EchoServer( Selector selector, ServerSocketChannel serverSocket, int bufferSize){
        this.selector = selector;
        this.serverSocket = serverSocket;
        this.buffer = ByteBuffer.allocate(bufferSize);
    }

    public static EchoServer createServer(String host, int port) throws IOException {
        LOG.info("New server on {}:{}", host, port);

        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        return new EchoServer(selector, serverSocket, 256);
    }

    @Override
    public void run(){
        LOG.info("Start server");
        boolean running = true;

        while(running) {
            try {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        register(selector, serverSocket);
                    }

                    if (key.isReadable()) {
                        answerWithEcho(buffer, key);
                    }
                    iter.remove();
                }
            }catch (IOException ex){
                LOG.error(ex.getMessage());
                running = false;
            }
        }
    }

    public Thread start(){
        Thread t = new Thread(this);
        t.start();
        return t;
    }

    public void stop(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    private void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            LOG.info("Not accepting client messages anymore");
        }
        else {
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }

    private void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }
}
package ru.ifmo.ctddev.korshikov.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by delf on 5/7/15.
 * UDP server class
 */
public class HelloUDPServer implements HelloServer {


    private Queue<DatagramSocket> sockets;
    private Queue<ExecutorService> services;

    /**
     * start server
     * @param port listening port
     * @param thread number of port
     * @see java.util.concurrent.ExecutorService
     * @see java.util.concurrent.Executors
     * @see java.net.DatagramSocket
     */
    @Override
    public void start(int port, int thread) {
        if(sockets == null){
            sockets = new ConcurrentLinkedQueue<>();
            services = new ConcurrentLinkedQueue<>();
        }
        ExecutorService service = Executors.newFixedThreadPool(thread);
        services.add(service);
        try {
            DatagramSocket receivingSocket = new DatagramSocket(port);
            sockets.add(receivingSocket);

            for (int i = 0; i < thread; ++i) {
                service.submit(() -> {
                    try (DatagramSocket socket = new DatagramSocket()) {
                        DatagramPacket received = new DatagramPacket(new byte[socket.getReceiveBufferSize()], socket.getReceiveBufferSize());
                        while (!Thread.interrupted() && !receivingSocket.isClosed()) {
                            receivingSocket.receive(received);
                            String receivedText = new String(received.getData(), 0, received.getLength());
                            String sending = "Hello, " + receivedText;
                            socket.send(new DatagramPacket(sending.getBytes(), sending.getBytes().length,
                                    received.getAddress(), received.getPort()));
                        }
                    } catch (IOException ignored) {
                    }
                });
            }
        } catch (SocketException e) {

        }

    }

    /**
     * close server
     */
    @Override
    public void close() {
        if (services != null) {
            services.parallelStream().forEach(service -> {
                service.shutdown();
                try {
                    if (!service.awaitTermination(4, TimeUnit.SECONDS)) {
                        //System.err.println("Service did not terminate");
                    }
                    service.shutdownNow();
                } catch (InterruptedException ignored) {
                    service.shutdownNow();
                }
            });
        }
        if (sockets != null) {
            sockets.parallelStream().forEach(DatagramSocket::close);
        }
    }

    /**
     * start server from commandline
     * @param arg args
     *            first - listening port
     *            second - number of thread
     */
    public static void main(String[] arg) {
        if (arg.length != 2) {
            System.out.print("Wrong args");
            return;
        }
        try{
            int port = Integer.parseInt(arg[0]);
            int numberOfThreads = Integer.parseInt(arg[1]);
            new HelloUDPServer().start(port, numberOfThreads);
        } catch (NumberFormatException ignored) {
            System.out.println("Wrong args");
        }
    }
}

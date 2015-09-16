package ru.ifmo.ctddev.korshikov.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by delf on 5/7/15.
 * UDP client class
 */
public class HelloUDPClient implements HelloClient {
    /**
     * run udp client
     *
     * @param args arguments array
     *             first argument - url/ip of server
     *             second argument - port of server
     *             next - prefix of send request
     *             next - count of thread
     *             next - count of requests per thread
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Wrong args");
            return;
        }
        try {
            String url = args[0];
            int destPort = Integer.parseInt(args[1]);
            String prefix = args[2];
            int countOfThreads = Integer.parseInt(args[3]);
            int countOfRequestPerThread = Integer.parseInt(args[4]);
            new HelloUDPClient().start(url, destPort, prefix, countOfThreads, countOfRequestPerThread);
        } catch (NumberFormatException ignored) {
            System.err.println("Wrong args");
        }

    }

    /**
     * start udp server
     *
     * @param url                     url/ip of server
     * @param destPort                port of server
     * @param prefix                  prefix of send request
     * @param thread                  count of thread
     * @param countOfRequestPerThread count of requests per thread
     * @see java.util.concurrent.ExecutorService
     * @see java.util.concurrent.Executors
     * @see java.net.InetAddress
     * @see java.net.DatagramPacket
     */
    public void start(String url, int destPort, String prefix, int thread, int countOfRequestPerThread) {
        if (destPort < 1 || destPort > 0xFFFF) {
            System.err.println("Wrong port number");
            return;
        }
        if (thread < 1 || countOfRequestPerThread < 1) {
            System.err.println("Thread number and request count per thread must be positive");
            return;
        }
        ExecutorService service = Executors.newFixedThreadPool(countOfRequestPerThread);
        try {
            InetAddress address = InetAddress.getByName(url);
            for (int i = 0; i < countOfRequestPerThread; ++i) {
                final int threadNumber = i;
                service.submit(() -> {
                    try (DatagramSocket socket = new DatagramSocket()) {
                        socket.setSoTimeout(400);
                        for (int requestId = 0; requestId < thread; ++requestId) {
                            String sendRequest = prefix + threadNumber + "_" + requestId;
                            DatagramPacket sendingPacket =
                                    new DatagramPacket(sendRequest.getBytes(), sendRequest.getBytes().length, address, destPort);
                            DatagramPacket receivedPacket =
                                    new DatagramPacket(new byte[socket.getReceiveBufferSize()], socket.getReceiveBufferSize());
                            String goodAnswer = "Hello, " + sendRequest;
                            String answer = "";
                            while (!goodAnswer.equals(answer)) {
                                try {
                                    System.out.println("-> " + sendRequest);
                                    socket.send(sendingPacket);
                                    socket.receive(receivedPacket);
                                    answer = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
                                    System.out.println("<- " + answer);
                                } catch (IOException ignored) {
                                }
                            }
                        }
                    } catch (SocketException ignored) {
                    }
                });
            }

            service.shutdownNow();
            if (!service.awaitTermination(60, TimeUnit.SECONDS))
                System.err.println("Service did not terminate");


        } catch (UnknownHostException ignored) {
            System.err.println("Incorrect host: " + url);
        } catch (InterruptedException ignored) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

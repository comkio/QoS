package priorityqueueclassifier;

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Server {

    public static void main(String args[]) throws Exception {

        // Listening port
        int listen_port;

        // Ask user for listening port
        BufferedReader llegir = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("[SERVER] Listen Port?");
        listen_port = Integer.parseInt(llegir.readLine());

        DatagramSocket serverSocket = new DatagramSocket(listen_port);
        byte[] receiveData = new byte[1024];
        long timeemissor, timearrive, delay = 0L;
        int port;

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            timearrive = System.currentTimeMillis();
            String sentence = new String(receivePacket.getData());
            String[] received_lines = sentence.split(" ");
            timeemissor = Long.parseLong(received_lines[1]);

            InetAddress IPAddress = receivePacket.getAddress();
            port = receivePacket.getPort();
            delay = timearrive - timeemissor;

            //Receive packets and calculate all the metrics.
            System.out.println("[SERVER] From: " + IPAddress + ":" + port);
            System.out.println("[SERVER] RECEIVE : " + sentence.trim() + "<>" + delay + "ms.");
            System.out.println("***********************************************");
        }
    }
}

package priorityqueueclassifier;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Classifier {

    public static void main(String args[]) throws Exception {

        // Listening port and destination ports
        int listen_port, send_port1, send_port2, send_port3;

        // Packet priority level 
        Integer priority;

        //Summary of sent packets
        int[] packet_pr = {0, 0};

        // Ask user for listening port and destination ports
        BufferedReader llegir = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("[CLASSIFIER] Listen Port?");
        listen_port = Integer.parseInt(llegir.readLine());
        System.out.println("[CLASSIFIER] Send Port queue 1?");
        send_port1 = Integer.parseInt(llegir.readLine());
        System.out.println("[CLASSIFIER] Send Port queue 2?");
        send_port2 = Integer.parseInt(llegir.readLine());
        System.out.println("[CLASSIFIER] Send Port queue 3?");
        send_port3 = Integer.parseInt(llegir.readLine());

        DatagramSocket ListenSocket = new DatagramSocket(listen_port);
        DatagramSocket SendSocket = new DatagramSocket();

        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] receiveData = new byte[1024];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            ListenSocket.receive(receivePacket);

            //String sentence = new String(receivePacket.getData());
            //String[] received_lines = sentence.split(" ");
            //priority = Integer.parseInt(received_lines[2]);

            DatagramPacket sendPacket = new DatagramPacket(receiveData, receiveData.length, IPAddress, send_port1);
            SendSocket.send(sendPacket);
            
            /*
            if (priority == 1) {
                DatagramPacket sendPacket = new DatagramPacket(receiveData, receiveData.length, IPAddress, send_port1);
                SendSocket.send(sendPacket);
                System.out.println("[CLASSIFIER] Packet priority: HIGH (1)");
                packet_pr[0]++;
            } else {
                DatagramPacket sendPacket = new DatagramPacket(receiveData, receiveData.length, IPAddress, send_port2);
                SendSocket.send(sendPacket);
                System.out.println("[CLASSIFIER] Packet priority: LOW (0)");
                packet_pr[1]++;
            }
            
            System.out.println("[CLASSIFIER] Total sent packets - "
                    + "HIGH: " + packet_pr[0] + " LOW: " + packet_pr[1] + " TOTAL: " + (packet_pr[0] + packet_pr[1]));
            System.out.println("***********************************************");
            */
        }

    }

}

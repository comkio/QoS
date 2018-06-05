package priorityqueueclassifier;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {

    public static void main(String args[]) throws Exception {

        // Packet ID
        int packet_id = 1;
        // Total number of packets to be sent
        int packet_num;
        // Generation rate (lambda)
        double lambda;

        //Send packets here - Similar to your traffic generator or client.
        DatagramSocket clientSocket = new DatagramSocket();
        //destination IP
        InetAddress IPAddress = InetAddress.getByName("localhost");
        //destination port
        int send_port;
        //packet size
        byte[] sendData = new byte[1024];
        // String 
        String sending;
        // Timestamp
        long timestamp;
        //Percentage of High Priority Traffic
        int TC;
        //time variable Poisson traffic
        int a;
        //Percentage of High Priority Traffic
        double Hpr;
        //Priority type
        int priority;
        //Summary of sent packets
        int[] packet_pr = {0, 0};

        // Ask user for number of packets, generation rate, kind of traffic, priority and destination port
        BufferedReader llegir = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("[CLIENT] Number of packets?");
        packet_num = Integer.parseInt(llegir.readLine());
        System.out.println("[CLIENT] Input traffic (packets per second)?");
        lambda = Integer.parseInt(llegir.readLine());   // (packets/s.)
        lambda = lambda / 1000;                         // (packets/ms.)
        System.out.println("[CLIENT] Kind of traffic? (1: Exponential / 2: Deterministic)");
        TC = Integer.parseInt(llegir.readLine());
        System.out.println("[CLIENT] Percentage of High Priority traffic? (%)");
        Hpr = Integer.parseInt(llegir.readLine());
        System.out.println("[CLIENT] Send Port?");
        send_port = Integer.parseInt(llegir.readLine());
        System.out.println("***********************************************");

        while (packet_id < (packet_num + 1)) {

            //current system time in milliseconds
            timestamp = System.currentTimeMillis();
            
            // Computation of current packet priority according to previously set "High Priority" percentage (Hpr)
            if ((Math.random() * 100) < Hpr) {
                priority = 1;
            } else {
                priority = 2;
            }
            
            //information within the String
            sending = packet_id + " " + timestamp + " " + priority + " \n";
            //Encoding String into a sequence of bytes, charset, here system default charset UTP-8
            sendData = sending.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, send_port);
            clientSocket.send(sendPacket);

            System.out.println("[CLIENT] New packet sent at " + System.currentTimeMillis());
            System.out.println("[CLIENT] Sending Packet ID: " + packet_id);

            if (priority == 1) {
                System.out.println("[CLIENT] Packet priority: HIGH (1)");
                packet_pr[0]++;
            } else {
                System.out.println("[CLIENT] Packet priority: LOW (2)");
                packet_pr[1]++;
            }
            System.out.println("[CLIENT] Total sent packets - HIGH: " + packet_pr[0] + " LOW: " + packet_pr[1]);

            //increment packet ids
            packet_id++;

            if (TC == 1) {
                PoissonGen poisson = new PoissonGen();
                a = poisson.compute_Poisson(lambda);
            } else {
                a = (int) (1 / lambda);
            }

            System.out.println("[CLIENT] --> Next packet in " + a + "ms.");

            Thread.sleep(a);
            System.out.println("***********************************************");
        }
        clientSocket.close();

    }

}

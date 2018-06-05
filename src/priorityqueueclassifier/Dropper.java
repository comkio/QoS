package priorityqueueclassifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dropper extends Thread {

    private Integer listen_port;
    private Integer queue_length;
    private final LinkedList<String> queue;

    public Dropper(Integer listen_port, Integer queue_length, LinkedList<String> queue) {
        this.listen_port = listen_port;
        this.queue_length = queue_length;
        this.queue = queue;
    }
//Eliminar el tipus de paquets, el dropper dropeja indistintament del tipus de paquet
    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(listen_port);
            byte[] receiveData = new byte[1024];

            // Number of dropped packets
            int dropped = 0;

            while (true) {

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String sentence = new String(receivePacket.getData());
                System.out.println("[DROPPER] RECEIVED PACKET ID: " + sentence.trim());
            
                // Implement a dropping stratergy. This should ideally be from your last lab session.
                if (queue_length == 0) {
                    queue.add(sentence);
                    System.out.println("Packet added (" + queue.size() + "/∞)");
                } else if (queue.size() < queue_length) {
                    queue.add(sentence);
                    System.out.println("Packet added (" + queue.size() + "/" + queue_length + ")");
                } else {
                    String[] received = sentence.split(" ");
                    long ID_P = Long.parseLong(received[0]);
                    System.out.println("Packet Dropped: " + ID_P);
                    dropped++;
                }
                System.out.println("Number of dropped packets: " + dropped);
                System.out.println("***********************************************");
            }
        } catch (SocketException ex) {
            Logger.getLogger(Dropper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dropper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

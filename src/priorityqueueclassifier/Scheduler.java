package priorityqueueclassifier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scheduler extends Thread {

    private final LinkedList<String> queue1;
    private final LinkedList<String> queue2;
    private final LinkedList<String> queue3;
    Integer sport;
    double mu;
    long time_sleep;

    public Scheduler(LinkedList<String> queue1, LinkedList<String> queue2, LinkedList<String> queue3, Integer send_port, double mu) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
        this.sport = send_port;
        this.mu = mu;
    }

    public void run() {
        try {
            String paquet = "";
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");

            while (true) {

                // Scheduler - similar to your last lab. 
                // Send data based on priority of the packets.
                PoissonGen poisson = new PoissonGen();
                //Thread.sleep(time_sleep);

                //Round Robin for 3 queues
                for(int i = 1; i <= 3; i++){
                    
                    if(i == 1){
                        synchronized (queue1) {
                            paquet = queue1.removeFirst();
                        }
                        byte[] sendData = new byte[1024];
                        sendData = paquet.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, sport);
                        clientSocket.send(sendPacket);
                        System.out.println("[SCHEDULER] packet queue" + i + "sent");
                    }
                    if(i == 2){
                        synchronized (queue2) {
                            paquet = queue2.removeFirst();
                        }
                        byte[] sendData = new byte[1024];
                        sendData = paquet.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, sport);
                        clientSocket.send(sendPacket);
                        System.out.println("[SCHEDULER] packet queue" + i + "sent");
                    } 
                    if(i == 3){
                        synchronized (queue3) {
                            paquet = queue3.removeFirst();
                        }
                        byte[] sendData = new byte[1024];
                        sendData = paquet.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, sport);
                        clientSocket.send(sendPacket); 
                        System.out.println("[SCHEDULER] packet queue" + i + "sent");
                    }   
                    time_sleep = poisson.compute_Poisson(mu);
                    Thread.sleep(time_sleep);
                }
 
            
                /*
                if (!queue1.isEmpty()) {
                    synchronized (queue1) {
                        paquet = queue1.removeFirst();
                    }

                    byte[] sendData = new byte[1024];
                    sendData = paquet.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, sport);
                    clientSocket.send(sendPacket);
                    System.out.println("[SCHEDULER] H Packet sent");
                    System.out.println("[SCHEDULER] H Queue size: " + queue1.size());
                    System.out.println("***********************************************");
                } else if (!queue2.isEmpty()) {
                    synchronized (queue2) {
                        paquet = queue2.removeFirst();
                    }

                    byte[] sendData = new byte[1024];
                    sendData = paquet.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, sport);
                    clientSocket.send(sendPacket);
                    System.out.println("[SCHEDULER] L Packet sent");
                    System.out.println("[SCHEDULER] L Queue size: " + queue2.size());
                    System.out.println("***********************************************");
                }
*/
            }

        } catch (SocketException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

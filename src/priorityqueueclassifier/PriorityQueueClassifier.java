/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package priorityqueueclassifier;

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Toni
 */
public class PriorityQueueClassifier {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {

        // Destination port, listening ports, and queue lengths
        int send_port, listen_portQ1, lengthQ1, listen_portQ2, lengthQ2;
        // Generation rate (mu)
        double mu;

        // Ask user for destination port, listening ports, queue lengths, and generation rate
        BufferedReader llegir = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("[QUEUE] Send Port?");
        send_port = Integer.parseInt(llegir.readLine());
        System.out.println("[QUEUE] Listen Port of Queue 1?");
        listen_portQ1 = Integer.parseInt(llegir.readLine());
        System.out.println("[QUEUE] Length of Queue 1?");
        lengthQ1 = Integer.parseInt(llegir.readLine());
        System.out.println("[QUEUE] Listen Port of Queue 2?");
        listen_portQ2 = Integer.parseInt(llegir.readLine());
        System.out.println("[QUEUE] Length of Queue 2?");
        lengthQ2 = Integer.parseInt(llegir.readLine());
        System.out.println("[QUEUE] Output traffic (packets per second)?");
        mu = Integer.parseInt(llegir.readLine());
        mu = mu / 1000;
        System.out.println("***********************************************");

        final LinkedList<String> queue1 = new LinkedList<>();
        final LinkedList<String> queue2 = new LinkedList<>();

        Scheduler sch = new Scheduler(queue1, queue2, send_port, mu);
        Dropper d1 = new Dropper(listen_portQ1, lengthQ1, queue1, "H");
        Dropper d2 = new Dropper(listen_portQ2, lengthQ2, queue2, "L");

        sch.start();
        d1.start();
        d2.start();
    }

}

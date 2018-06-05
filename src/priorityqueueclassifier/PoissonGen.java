/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package priorityqueueclassifier;

/**
 *
 * @author Toni
 */
public class PoissonGen {

    public int compute_Poisson(double lambda) {
        int a;
        double b, c;

        b = Math.random();
        //System.out.println("b value = " + b + "ms.");
        c = (Math.log(1 - b) / (-lambda));
        //System.out.println("c value = " + c + "ms.");
        a = (int) c;

        return a;
    }
}

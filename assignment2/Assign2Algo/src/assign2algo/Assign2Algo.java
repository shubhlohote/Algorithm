/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign2algo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author shubhangi
 */
public class Assign2Algo {

    /**
     * @param args the command line arguments
     */
     
     
     
    public static void main(String[] args) {
        // TODO code application logic here

	 BinaryTree bt = new BinaryTree();
        bt.insert(10);
        int request =0;
        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.nanoTime());
        System.out.println("Start Time -> "+startTime);
        for(int i=0;i<100;i++)
        {
            Random rand=new Random();
            request=rand.nextInt(250);
            System.out.println("Requested memory:" +request);
            boolean available = bt.search(request);
            
        }
        
        //long stopTime = TimeUnit.MILLISECONDS.toSeconds(System.nanoTime());
        //stopwatch.reset();
       // System.out.println("End Time   -> " + stopTime);
        long stopTime = TimeUnit.MILLISECONDS.toSeconds(System.nanoTime());
        System.out.println("End Time   -> " + stopTime);
        System.out.println("Total time for request processing" + (stopTime-startTime));
        
        
       // int nextReq = 4;
       // available = bt.search(4);
      //  System.out.println(available);
    }
    
}

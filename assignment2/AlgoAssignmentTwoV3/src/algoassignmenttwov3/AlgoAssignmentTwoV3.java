/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoassignmenttwov3;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gaurang Deshpande <deshpande.ga>
 */
public class AlgoAssignmentTwoV3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BinaryTree bt = new BinaryTree();
        bt.insert(5);
        int request =0;
        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.nanoTime());
        //System.out.println("Start Time -> "+startTime);
        int reqID = 1;
        int deallocateReqId = 1;
        
        //for(int i=1;i<=7;i++){
            //if((i%3)==0){
            //    bt.deallocate(deallocateReqId);
            //    deallocateReqId++;
            //}
            Request req = new Request();
            req.setRequestId(reqID);
            
            //Random rand=new Random();
            //request=rand.nextInt(8);
            request = 4;
            req.setRequirementSize(request);
            System.out.println("\nRequest ID - "+reqID+"\nRequested Memory - "+request);
            if(!bt.search(req)){
                System.out.println("Request Failed");
            }
            else{
                for (int j = 0; j < BinaryTree.allocatedMemory.size(); j++) {
                    if (BinaryTree.allocatedMemory.get(j).getProcessIdForRequest() == reqID) {
                        System.out.println("Node id - " + BinaryTree.allocatedMemory.get(j).getNodeId() + ", data - " + BinaryTree.allocatedMemory.get(j).getData());
                    }
                }
            }
            
            reqID++;
            
            //Temp
            req = new Request();
            req.setRequestId(reqID);
            
            //Random rand=new Random();
            //request=rand.nextInt(8);
            request = 10;
            req.setRequirementSize(request);
            
            System.out.println("\nRequest ID - "+reqID+"\nRequested Memory - "+request);
            if(!bt.search(req)){
                System.out.println("Request Failed");
            }
            else{
                for (int j = 0; j < BinaryTree.allocatedMemory.size(); j++) {
                    if (BinaryTree.allocatedMemory.get(j).getProcessIdForRequest() == reqID) {
                        System.out.println("Node id - " + BinaryTree.allocatedMemory.get(j).getNodeId() + ", data - " + BinaryTree.allocatedMemory.get(j).getData());
                    }
                }
            }
            
            reqID++;
            req = new Request();
            req.setRequestId(reqID);
            
            //Random rand=new Random();
            //request=rand.nextInt(8);
            request = 10;
            req.setRequirementSize(request);
            
            System.out.println("\nRequest ID - "+reqID+"\nRequested Memory - "+request);
            if(!bt.search(req)){
                System.out.println("Request Failed");
            }
            else{
                for (int j = 0; j < BinaryTree.allocatedMemory.size(); j++) {
                    if (BinaryTree.allocatedMemory.get(j).getProcessIdForRequest() == reqID) {
                        System.out.println("Node id - " + BinaryTree.allocatedMemory.get(j).getNodeId() + ", data - " + BinaryTree.allocatedMemory.get(j).getData());
                    }
                }
            }
            
            reqID++;
            req = new Request();
            req.setRequestId(reqID);
            
            //Random rand=new Random();
            //request=rand.nextInt(8);
            request = 4;
            req.setRequirementSize(request);
            
            System.out.println("\nRequest ID - "+reqID+"\nRequested Memory - "+request);
            if(!bt.search(req)){
                System.out.println("Request Failed");
            }
            else{
                for (int j = 0; j < BinaryTree.allocatedMemory.size(); j++) {
                    if (BinaryTree.allocatedMemory.get(j).getProcessIdForRequest() == reqID) {
                        System.out.println("Node id - " + BinaryTree.allocatedMemory.get(j).getNodeId() + ", data - " + BinaryTree.allocatedMemory.get(j).getData());
                    }
                }
            }
            //Temp End
            
        //}
        
        long stopTime = TimeUnit.MILLISECONDS.toSeconds(System.nanoTime());
        //System.out.println("End Time   -> " + stopTime);
        System.out.println("Total time for request processing" + (stopTime-startTime));
    }
    
}

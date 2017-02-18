/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoassignmenttwov2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gaurang Deshpande <deshpande.ga>
 */
public class AlgoAssignmentTwoV2 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        BinaryTree bt = new BinaryTree();
        bt.insert(20);
        int request =0;
        
        PrintWriter writer = new PrintWriter(new File("nodeAllocation.csv"));
        StringBuilder builder = new StringBuilder();
        
        PrintWriter writerDeallocation = new PrintWriter(new File("deallocation.csv"));
        StringBuilder builderDeallocation = new StringBuilder();
        
        builder.append("Request ID");
        builder.append(',');
        builder.append("Memory Required");
        builder.append(',');
        builder.append("Node Allocated");
        builder.append(',');
        builder.append("Node Data");
        builder.append(',');
        builder.append("Time Taken");
        builder.append(',');
        builder.append("Failed Requests");
        builder.append('\n');
        
        int failedRequests = 0;
        
        builderDeallocation.append("Deallocated Request ID");
        builderDeallocation.append(',');
        builderDeallocation.append("Deallocated Nodes");
        builderDeallocation.append('\n');
        
        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.nanoTime());
        
        int reqID = 1;
        int deallocateReqId = 1;
        
        for(int i=1;i<=48000;i++){
            if((i%4)==0){
                builderDeallocation.append(deallocateReqId+",");
                builderDeallocation.append('\n');
                bt.deallocate(deallocateReqId, builderDeallocation);
                deallocateReqId++;
            }
            Request req = new Request();
            req.setRequestId(reqID);
            Random rand=new Random();
            int max = 32, min = 1;
            request=rand.nextInt((max-min) + 1)+min;
            
            req.setRequirementSize(request);
            builder.append("\t"+reqID);
            builder.append(",\t\t\t");
            builder.append(request);
            builder.append(",\t\t\t");
            //System.out.println("\nRequest ID - "+reqID+"\nRequested Memory - "+request);
            boolean available = bt.search(req);
            if(!available){
                builder.append("Request Failed");
                builder.append(",\t\t\t");
                builder.append("- - - -");
                builder.append("\n,");
                //System.out.println("Request Failed");
                failedRequests++;
            }
            else{
                for (int j = 0; j < BinaryTree.allocatedMemory.size(); j++) {
                    if (BinaryTree.allocatedMemory.get(j).getProcessIdForRequest() == reqID) {
                        builder.append("\n\t\t\t\t\t\t\t\t"+BinaryTree.allocatedMemory.get(j).getNodeId());
                        builder.append(",\t\t\t");
                        builder.append(BinaryTree.allocatedMemory.get(j).getData());
                        //System.out.println("Node id - "+BinaryTree.allocatedMemory.get(j).getNodeId()+" & Node data - "+BinaryTree.allocatedMemory.get(j).getData());
                    }
                }
            }
            builder.append("\n\n");
            reqID++;
        }
        long stopTime = TimeUnit.MILLISECONDS.toSeconds(System.nanoTime());
        
        builder.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
        builder.append(stopTime-startTime);
        builder.append(",\t\t");
        builder.append(failedRequests);
        builder.append("\n");
        
        writerDeallocation.write(builderDeallocation.toString());
        writerDeallocation.close();
        
        writer.write(builder.toString());
        writer.close();
        //System.out.println("End Time   -> " + stopTime);
        //System.out.println("Total time for request processing" + (stopTime-startTime));
    }
    
}

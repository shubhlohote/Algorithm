/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoassignmenttwov4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author shubhangi
 */
public class BinaryTree {
    private Node root;
    private int requiredMemory;
    public static ArrayList<Node> allocatedMemory;
    
    public BinaryTree(){
        root = null;
        allocatedMemory = new ArrayList<Node>();
    }
    
    //Create Tree data structure
    public void insert(int data){
        root = insert(root, data);
    }
    
    //Function to insert data recursively
    private Node insert(Node node, int data){
        int memory = (int) Math.pow(2, data);
        node = new Node(memory, "available");
        while(data>0){
            if(node.right==null){
                data--;
                node.left = insert(node.left, data);
                node.right = insert(node.right, data);
            }
            else{
                break;
            }
        } 
        return node;
    }     
    
    //Search nodes for required memory size
    public boolean search(Request req){
        int val = req.getRequirementSize();
        requiredMemory = val;
        if(val> root.getData())
        {
            System.out.println("Sufficient memory is not available");
            return false;
        }
        else if(val == 0){
            System.out.println("Request size is 0");
            return false;
        }
        else
        return search(root, req);
    }
    
    //Recursive function
    private boolean search(Node node, Request req){
        int val = requiredMemory;
        if (node.getData() == val){
            boolean result = inorder(node);
            if(result){
                if(makeInOrderBusyDefragment(node, req)){
                    return true;
                }
                return false;
            }
            else{
                return false;
            }
        }
        else if(node.getData() > val && val > node.getData()/2){
            boolean result = inorder(node);
            if(result){
                //requiredMemory = val;
                if(makeInOrderBusyDefragment(node, req)){
                    return true;
                }
                return false;
            }
            return false;
        }
        if (node.getLeft() != null)
            if (search(node.getLeft(), req))
                return true;
        if (node.getRight() != null)
            if (search(node.getRight(), req))
                return true;
        return false;         
    }
    
    //Function for inorder traversal
    public void inorder(){
        inorder(root);
    }

    private boolean inorder(Node node){
        boolean flag = true;
        boolean flagLeft = true,flagRight = true;
        if (node != null){
            if(node.status.equalsIgnoreCase("available")){
                flagLeft = inorder(node.getLeft());
                flagRight = inorder(node.getRight());
            }
            else{
                flag = false;
            }
        }
        else{
            flag = true;
        }
        if(flagLeft==false&&flagRight==false){
            flag = false;
        }
        return flag;
    }
    
    private boolean inorderFullAvailable(Node node){
        boolean flag = true;
        boolean flagLeft = true,flagRight = true;
        if (node != null){
            if(node.status.equalsIgnoreCase("available")){
                flagLeft = inorderFullAvailable(node.getLeft());
                flagRight = inorderFullAvailable(node.getRight());
            }
            else{
                flag = false;
            }
        }
        else{
            flag = true;
        }
        if(flagLeft==false||flagRight==false){
            flag = false;
        }
        return flag;
    }
    
    private void makeInOrderBusy(Node node, Request req){
        if (node != null){
            node.setStatus("busy");
            node.setProcessIdForRequest(req.getRequestId());
            allocatedMemory.add(node);
            makeInOrderBusy(node.getLeft(), req);
            makeInOrderBusy(node.getRight(), req);
        }
    }
    
    /**
     * Deallocation method
     * @param requestID 
     */
    void deallocate(int requestID, StringBuilder builder) throws FileNotFoundException {
        //System.out.println("\nDeallocated request id - "+requestID);
        for(int i=allocatedMemory.size()-1;i>0;i--){
            if(allocatedMemory.get(i).getProcessIdForRequest()==requestID){
                allocatedMemory.remove(i);
            }
        }
        deallocate(root, requestID, builder);
        //System.out.println("");
    }

    private void deallocate(Node node, int requestID, StringBuilder builder) {
        if (node.getProcessIdForRequest() == requestID) {
            node.setStatus("available");
            //System.out.print("Node id - "+node.getNodeId()+" ");
            builder.append("\t\t\t\t\t");
            builder.append(',');
            builder.append(node.getNodeId());
            builder.append('\n');
        }
        if (node.getLeft() != null) {
            deallocate(node.getLeft(), requestID, builder);
        }
        if (node.getRight() != null) {
            deallocate(node.getRight(), requestID, builder);
        }
    }

    private boolean makeInOrderBusyDefragment(Node node, Request req) {
        boolean flag = false;
        if (node != null){
            if(node.getStatus().equalsIgnoreCase("available")) {
                if (requiredMemory > 0) {
                    if (node.getData() > requiredMemory) {
                        flag = makeInOrderBusyDefragment(node.getLeft(), req);
                        flag = makeInOrderBusyDefragment(node.getRight(), req);
                    } else {
                        boolean res = inorderFullAvailable(node);
                        boolean resLeft = false, resRight = false;
                        if(res){
                            makeInOrderBusy(node, req);
                            requiredMemory = requiredMemory - node.getData();
                            if(requiredMemory==0){
                                flag = true;
                            }
                        }
                        else{
                            resLeft = inorderFullAvailable(node.getLeft());
                            resRight = inorderFullAvailable(node.getRight());
                        }
                        if(resLeft){
                            flag = makeInOrderBusyDefragment(node.getLeft(), req);
                        }
                        if(resRight){
                            flag = makeInOrderBusyDefragment(node.getRight(), req);
                        }
                    }
                }
                else{
                    flag = true;
                }
            }
        }
        return flag;
    }
}

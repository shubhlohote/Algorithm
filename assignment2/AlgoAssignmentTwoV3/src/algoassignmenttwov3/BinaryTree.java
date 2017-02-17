/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoassignmenttwov3;

import java.util.ArrayList;

/**
 *
 * @author shubhangi
 */
public class BinaryTree {
    private Node root;
    private int requiredMemory;
    public static ArrayList<Node> allocatedMemory;
    /* Constructor */
    public BinaryTree(){
        root = null;
        allocatedMemory = new ArrayList<Node>();
    }
    
    /* Function to check if tree is empty */
    public boolean isEmpty(){
        return root == null;
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

    /* Function to count number of nodes */
    public int countNodes(){
        return countNodes(root);
    }

    /* Function to count number of nodes recursively */
    private int countNodes(Node r){
        if (r == null)
            return 0;
        else{
            int l = 1;
            l += countNodes(r.getLeft());
            l += countNodes(r.getRight());
            return l;
        }
    }
    
    /* Function to search for an element */
    public boolean search(Request req){
        int val = req.getRequirementSize();
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
    
    /* Function to search for an element recursively */
    private boolean search(Node r, Request req){
        int val = req.getRequirementSize();
        if (r.getData() == val){
            boolean result = inorder(r);
            if(result){
                makeInOrderBusy(r, req);
                return true;
            }
            else{
                return false;
            }
        }
        else if(r.getData() > val && val > r.getData()/2){
            boolean result = inorder(r);
            if(result){
                requiredMemory = val;
                makeInOrderBusyDefragment(r, req);
                return true;
            }
            return false;
        }
        if (r.getLeft() != null)
            if (search(r.getLeft(), req))
                return true;
        if (r.getRight() != null)
            if (search(r.getRight(), req))
                return true;
        return false;         
    }
    
    /* Function for inorder traversal */
    public void inorder(){
        inorder(root);
    }

    private boolean inorder(Node r){
        boolean flag = true;
        boolean flagLeft = true,flagRight = true;
        if (r != null){
            if(!(r.status.equalsIgnoreCase("busy"))){
                flagLeft = inorder(r.getLeft());
                flagRight = inorder(r.getRight());
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
    
    private void makeInOrderBusy(Node r, Request req){
        if (r != null){
            r.setStatus("busy");
            //System.out.print(" Node id - "+r.getNodeId()+" ");
            r.setProcessIdForRequest(req.getRequestId());
            allocatedMemory.add(r);
            makeInOrderBusy(r.getLeft(), req);
            makeInOrderBusy(r.getRight(), req);
        }
    }
    
    /* Function for preorder traversal */
    public void preorder(){
        preorder(root);
    }
    
    private void preorder(Node r){
        if (r != null){
            System.out.print(r.getData() +" ");
            preorder(r.getLeft());             
            preorder(r.getRight());
        }
    }
    
    /* Function for postorder traversal */
    //public void postorder(){
    //    postorder(root);
    //}
    
    /*private boolean postorder(Node r, int value){
        if (r != null) {
            if (r.getLeft() != null) {
                if (r.getLeft().getData() <= value) {
                    if (r.getLeft().getStatus().equalsIgnoreCase("available")) {
                        postorder(r.getLeft(), value);
                        postorder(r.getRight(), value);
                    } else {
                        allocatedMemory.add(r.getRight());
                    }
                }
                
            }
            
        }
        return false;
    }*/     

    /**
     * Deallocation method
     * @param requestID 
     */
    void deallocate(int requestID) {
        System.out.println("\nDeallocated request id - "+requestID);
        for(int i=0;i<allocatedMemory.size();i++){
            if(allocatedMemory.get(i).getProcessIdForRequest()==requestID){
                allocatedMemory.remove(i);
                break;
            }
        }
        deallocate(root, requestID);
    }

    private void deallocate(Node node, int requestID) {
        if (node.getProcessIdForRequest() == requestID) {
            node.setStatus("available");
            System.out.print(" Node id - "+node.getNodeId()+" ");
        }
        if (node.getLeft() != null) {
            deallocate(node.getLeft(), requestID);
        }
        if (node.getRight() != null) {
            deallocate(node.getRight(), requestID);
        }
    }

    private void makeInOrderBusyDefragment(Node r, Request req) {
        if (r != null){
            if(r.getStatus().equalsIgnoreCase("available")) {
                if (requiredMemory != 0) {
                    if (r.getData() > requiredMemory) {
                        makeInOrderBusyDefragment(r.getLeft(), req);
                        makeInOrderBusyDefragment(r.getRight(), req);
                    } else {
                        makeInOrderBusy(r, req);
                        requiredMemory = requiredMemory - r.getData();
                    }
                }
            }
        }
    }
}

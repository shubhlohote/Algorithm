/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign2algo;

import java.util.ArrayList;

/**
 *
 * @author shubhangi
 */
public class BinaryTree {
    private Node root;
    private static ArrayList allocatedMemory;
    /* Constructor */
    public BinaryTree(){
        root = null;
        allocatedMemory = new ArrayList<Node>();
    }
    
    /* Function to check if tree is empty */
    public boolean isEmpty(){
        return root == null;
    }
    
    /* Functions to insert data */
    public void insert(int data){
        root = insert(root, data);
        System.out.println(root.data);
    }
    
    /* Function to insert data recursively */
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
    public boolean search(int val){
        if(val> root.getData())
        {
            System.out.println("Sufficient memory is not available !!");
            return false;
        }
        else
        return search(root, val);
        
    }
    
    /* Function to search for an element recursively */
    private boolean search(Node r, int val){
        
        if (r.getData() == val || (r.getData() > val && val > r.getData()/2)){
            boolean result = inorder(r);
            if(result){
                makeInOrderBusy(r);
                allocatedMemory.add(r);
                System.out.println("Allocated memory is -> "+r.data);
                return true;
            }
            else{
                return false;
            }
        }
        if (r.getLeft() != null)
            if (search(r.getLeft(), val))
                return true;
        if (r.getRight() != null)
            if (search(r.getRight(), val))
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
    
    private void makeInOrderBusy(Node r){
        if (r != null){
            r.setStatus("busy");
            makeInOrderBusy(r.getLeft());
            makeInOrderBusy(r.getRight());
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
    public void postorder(){
        postorder(root);
    }
    
    private void postorder(Node r){
        if (r != null){
            postorder(r.getLeft());             
            postorder(r.getRight());
            System.out.print(r.getData() +" ");
        }
    }     
}

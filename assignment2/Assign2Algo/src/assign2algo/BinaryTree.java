
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign2algo;

/**
 *
 * @author shubhangi
 */
public class BinaryTree {
    private Node root;
 
    /* Constructor */
    public BinaryTree(){
        root = null;
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
        node = new Node(memory);
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
        return search(root, val);
    }
    
    /* Function to search for an element recursively */
    private boolean search(Node r, int val){
        if (r.getData() == val)
            return true;
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

    private void inorder(Node r){
        if (r != null){
            inorder(r.getLeft());
            System.out.print(r.getData() +" ");
            inorder(r.getRight());
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
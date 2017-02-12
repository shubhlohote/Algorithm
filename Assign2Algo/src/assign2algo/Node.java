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
public class Node {
    Node left;
    Node right;
     String status;
     int data;
 
     /* Constructor */
     public Node()
     {
         left = null;
         right = null;
         data = 0;
     }
     /* Constructor */
     public Node(int n)
     {
         left = null;
         right = null;
         data = n;
     }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
    
}

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
public class BTNode {
    BTNode left, right;
     String status;
     int data;
 
     /* Constructor */
     public BTNode()
     {
         left = null;
         right = null;
         data = 0;
     }
     /* Constructor */
     public BTNode(int n)
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
   
    public BTNode getLeft() {
        return left;
    }

    public void setLeft(BTNode left) {
        this.left = left;
    }

    public BTNode getRight() {
        return right;
    }

    public void setRight(BTNode right) {
        this.right = right;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
    
}

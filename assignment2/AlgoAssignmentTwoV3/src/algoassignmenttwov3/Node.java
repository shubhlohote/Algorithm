/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoassignmenttwov3;

/**
 *
 * @author shubhangi
 */
public class Node {
    Node left;
    Node right;
    String status;
    int data;
    int memoryAvailableInChildren;
    int processIdForRequest;
    int nodeId;
    static int count=0;
 
    /* Constructor */
    public Node(){
        left = null;
        right = null;
        data = 0;
    }
    
    /* Constructor */
    public Node(int n){
        left = null;
        right = null;
        data = n;
    }

    /* Constructor */
    public Node(int n, String status){
        left = null;
        right = null;
        data = n;
        this.status = status;
        count++;
        this.nodeId = count;
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

    public int getMemoryAvailableInChildren() {
        return memoryAvailableInChildren;
    }

    public void setMemoryAvailableInChildren(int memoryAvailableInChildren) {
        this.memoryAvailableInChildren = memoryAvailableInChildren;
    }

    public long getProcessIdForRequest() {
        return processIdForRequest;
    }
    public void setProcessIdForRequest(int processIdForRequest) {
        this.processIdForRequest = processIdForRequest;
    }

    public long getNodeId() {
        return nodeId;
    }
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
}
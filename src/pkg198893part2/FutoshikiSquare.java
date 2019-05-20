/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg198893part2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author cy214
 * Square class for the grid
 */


public class FutoshikiSquare implements Serializable {
    boolean editable;
    ArrayList<Integer> pencilmarked;
    int data;
    int answer; //can use to check it

    public FutoshikiSquare(boolean editable,int data) {
        this.editable = editable;
        pencilmarked = new ArrayList<Integer>();
        this.data = data;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getpencilMarked() {
        return pencilmarked.toString()+" ";
    }

    public void addpencilMarked(int num){
        pencilmarked.add(num);
    }
    
    public void deleteMarked(Integer num){
        pencilmarked.remove(num); //remove element or index?
        
    }
    
    public void clearMarked(){
        pencilmarked.removeAll(pencilmarked);
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
    
    public void setpencilMarkedList(ArrayList<Integer> set){
        pencilmarked = set;
    }
    public ArrayList<Integer> getpencilMarkedList(){
        return pencilmarked;
    }
    
    
    
    
}

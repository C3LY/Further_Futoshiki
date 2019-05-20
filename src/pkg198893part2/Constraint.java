/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg198893part2;

import java.io.Serializable;

/**
 *
 * @author cy214
 * Constraints for the puzzle, requires the squares involved in the constraint and if it is horizontal
 */
public abstract class Constraint implements Serializable{
    private FutoshikiSquare square1Data;
    private FutoshikiSquare square2Data;
    private String constraintData;  
    private boolean Horizontal;
    
     /**
     * Constructor for constraint
     * @param Futoshiki 1st square involved in constraint
     * @param Futoshiki 2nd square involved in constraint
     * @param boolean true if horizontal(row constraints) false if vertical(coloumn constraints)
     * 
     *
     */
        public Constraint(FutoshikiSquare square1Data, FutoshikiSquare square2Data,  boolean Horizontal) {
        this.square1Data = square1Data;
        this.square2Data = square2Data;
        this.Horizontal = Horizontal;
    }
        
         /**
     * isLegal  - if the constraint and squares involved is legal

     * @param boolean true if its is legal
     * 
     */
        public abstract boolean isLegal();
        
                public abstract boolean isSatisfied();


    public void setSquare1Data(FutoshikiSquare square1Data) {
        this.square1Data = square1Data;
    }

    public void setSquare2Data(FutoshikiSquare square2Data) {
        this.square2Data = square2Data;
    }

    public int getSquare1Data() {
        return square1Data.getData();
    }

    public int getSquare2Data() {
        return square2Data.getData();
    }
        
  


    public String getConstraintData() {
        return constraintData;
    }

    public void setConstraintData(String constraintData) {
        this.constraintData = constraintData;
    }

    public boolean isHorizontal() {
        return Horizontal;
    }

    public void setHorizontal(boolean Horizontal) {
        this.Horizontal = Horizontal;
    }

}
   























    
//    public Constraint(FutoshikiSquare square1Data, FutoshikiSquare square2Data, String constraintData) {
//        this.square1Data = square1Data;
//        this.square2Data = square2Data;
//        this.constraintData = constraintData;
//        setConstraintData(constraintData);
//        if(constraintData.equals("<")){
//            LessThan=true;
//            Horizontal=true;  
//            empty=false;
//        }
//        else if(constraintData.equals(">")){
//            LessThan=false;
//            Horizontal=true;
//            empty=false;
//        }
//        else if(constraintData.equals("^")){
//            LessThan=true;
//            Horizontal=false;
//            empty=false;
//        }
//        else if(constraintData.equals("v")){
//            LessThan=false;
//            Horizontal=false;
//            empty=false;
//        }
//        else{
//            LessThan=false;
//            Horizontal=false;
//            empty=true;
//            
//        }
//    }
//
//    public boolean isLessThan() {
//        if (constraintData.equals("<") || constraintData.equals("^") || LessThan) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public String correctSymbol(){
//         if (!(square1Data.getData() == 0 || square2Data.getData() == 0 || constraintData == " "||empty)) {
//            if (square1Data.getData() < square2Data.getData() && Horizontal ) {
//                return "<";
//            } else if (square1Data.getData() < square2Data.getData() && !Horizontal) {
//                return "^";
//            } else if (square1Data.getData() > square2Data.getData() && Horizontal ) {
//                return ">";
//            } else if (square1Data.getData() > square2Data.getData() && !Horizontal ) {
//                return "v";
//            }
//            else{
//                return " ";
//            }
//        }
//        else {
//                return " ";
//            }
//    }
//
//    public boolean validNumbers() { //stop if smaller than 1
//        if (!(square1Data.getData() == 0 || square2Data.getData() == 0 || constraintData == " "||empty)) {
//            if ((constraintData.equals(">")) && (square1Data.getData() <= square2Data.getData())) {
//                return false;
//            } else if ((constraintData.equals("<")) && square1Data.getData() >= square2Data.getData()) {
//                return false;
//            } else if ((constraintData.equals("v")) && (square1Data.getData() <= square2Data.getData())) {
//                return false;
//            } else if ((constraintData.equals("^")) && (square1Data.getData() >= square2Data.getData())) {
//                return false;
//            }
//            else{
//                return true;
//            }
//        }
//        else {
//                return true;
//            }
//    }
//
//    public int getSquare1Data() {
//        return square1Data.getData();
//    }
//
//    public void setSquare1Data(int square1Data) {
//        this.square1Data.setData(square1Data);
//    }
//
//    public int getSquare2Data() {
//        return square2Data.getData();
//    }
//
//    public void setSquare2Data(int square2Data) {
//        this.square2Data.setData(square2Data);
//    }
//
//    public String getConstraintData() {
//        return constraintData;
//    }
//
//    public void setConstraintData(String constraintData) {
//        this.constraintData = constraintData;
//        switch (constraintData) {
//            case "<":
//                LessThan=true;
//                Horizontal=true;
//                empty=false;
//                break;
//            case ">":
//                LessThan=false;
//                Horizontal=true;
//                empty=false;
//                break;
//            case "^":
//                LessThan=true;
//                Horizontal=false;
//                empty=false;
//                break;
//            case "v":
//                LessThan=false;
//                Horizontal=false;
//                empty=false;
//                break;
//            default:
//                LessThan=false;
//                Horizontal=false;
//                empty=true;
//                break;
//        }
//    }
//    
//    //method for if one sqaure if empty
//
//    public boolean isEmpty() {
//        return empty;
//    }
//   


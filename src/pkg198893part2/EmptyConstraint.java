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
 */
public class EmptyConstraint extends Constraint implements Serializable {

    public EmptyConstraint(FutoshikiSquare square1Data, FutoshikiSquare square2Data, boolean Horizontal) {
        super(square1Data, square2Data, Horizontal);
    }


    @Override
    public String getConstraintData(){
        return " ";
    }

    @Override
    public boolean isLegal() {
        return true;
    }

    @Override
    public boolean isSatisfied() {
                return true;
    }
    
    
    
}

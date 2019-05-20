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
public class LessThan extends Constraint implements Serializable {

    public LessThan(FutoshikiSquare square1Data, FutoshikiSquare square2Data, boolean Horizontal) {
        super(square1Data, square2Data, Horizontal);
        if(Horizontal){
            super.setConstraintData("<");
        }
        else{
            super.setConstraintData("^");
        }
    }

      @Override
    public boolean isLegal(){
        return !(super.getSquare2Data()==1||(super.getSquare1Data() >= super.getSquare2Data() && (super.getSquare1Data()!=0 && super.getSquare2Data()!=0)));
    }

    @Override
    public boolean isSatisfied(){
        return super.getSquare1Data() < super.getSquare2Data();
    }
    
    
    
}

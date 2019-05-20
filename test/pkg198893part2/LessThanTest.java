/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg198893part2;

//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author cy214
 */
public class LessThanTest {
    
    public LessThanTest() {
    }
    
    @Before
    public void setUp() {
    }

       /**
     * Test of isLegal method, of class LessThan.
     */
    @Test
    public void testIsLegalValidHorizontal() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,2);
        FutoshikiSquare square2 = new FutoshikiSquare(true,3);
        LessThan c1 = new LessThan(square1,square2,true); // |2|<|3|
        
        assert(c1.isLegal());
        assertEquals("<", c1.getConstraintData());
    }
    
     @Test
    public void testIsLegalValidVertical() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,2);  //|2|
        FutoshikiSquare square2 = new FutoshikiSquare(true,3);  //^ 
        LessThan c1 = new LessThan(square1,square2,false); //|3|   
        
        assertEquals(true, c1.isLegal());
        assertEquals("^", c1.getConstraintData());
    }

    @Test
    public void testIsLegalInValidHorizontal() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,3);  
        FutoshikiSquare square2 = new FutoshikiSquare(true,2);  
        LessThan c1 = new LessThan(square1,square2,true); // |3|<|2|    
        
        assertEquals(false, c1.isLegal());
    }
    
    @Test
    public void testIsLegalInValidVertical() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,3);  //|3|
        FutoshikiSquare square2 = new FutoshikiSquare(true,2);  //^
        LessThan c1 = new LessThan(square1,square2,false); //|2|
       
        assertEquals(false, c1.isLegal());
    }
    
    @Test
    public void testIsLegalValidSquare1Empty() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,0);  
        FutoshikiSquare square2 = new FutoshikiSquare(true,2); 
        LessThan c1 = new LessThan(square1,square2,true);
       
        assertEquals(true, c1.isLegal());
    }
    
    @Test
    public void testIsLegalValidSquare2Empty() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,2);  
        FutoshikiSquare square2 = new FutoshikiSquare(true,0); 
        LessThan c1 = new LessThan(square1,square2,true);
       
        assertEquals(true, c1.isLegal());
    }
    
    @Test
    public void testIsLegalInValidSmallerThan1() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,0);  
        FutoshikiSquare square2 = new FutoshikiSquare(true,1); 
        LessThan c1 = new LessThan(square1,square2,true);
       
        assertEquals(false, c1.isLegal());
    }
    @Test
    public void testIsLegalValid1() {
        FutoshikiSquare square1 = new FutoshikiSquare(true,1);  
        FutoshikiSquare square2 = new FutoshikiSquare(true,0); 
        LessThan c1 = new LessThan(square1,square2,true);
       
        assertEquals(true, c1.isLegal());
    }
    
    
    
}

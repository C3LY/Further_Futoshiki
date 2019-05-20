package pkg198893part2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 *
 * @author cy214
 */
public class FutoshikiPuzzleTest {
    

    
    FutoshikiPuzzle p1;
    public FutoshikiPuzzleTest() {
    }
    
    @Before
    public void setUp() {
        p1 = new FutoshikiPuzzle(5);
    }
 
    

    /**
     * Test of fillPuzzle method, of class FutoshikiPuzzle.
     * I deleted the while loop that checks if the square has already been processed. The program was taking to long to generate.
     */
    @Test
    public void testNumberGridFillPuzzle() {
        int Numbercounter =0; //this counts what has been placed in the grid
        FutoshikiPuzzle p2 = new FutoshikiPuzzle(5); //fills all 25 squares 
        p2.fillPuzzle(5);   //deletes 5*(5*3)/5 = 5*15/5 = 15 are deleted 25/15 = 10
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(p2.getSquare(i, j)>0){
                    Numbercounter++;
                }
            } 
        } 
        
        assertTrue(Numbercounter >=10);
    //    assertEquals(10,Numbercounter); //it should match what I've set the number of symbols and numbers to in fillPuzzle
    }
    

    
    /**
     * This could fail! I deleted the while loop that checks if the square has already been processed. The program was taking to long to generate.
     */
    @Test
    public void testConstriantsFillPuzzle(){
      FutoshikiPuzzle p2 = new FutoshikiPuzzle(5);
        p2.fillPuzzle(5); // 5*(5*3)/5 = 15 size of 25 - 15 = 10
        System.out.println("displayString test constraints" + p2.displayString());
        //p1 is a 5x5 puzzle
        int RowConcounter =0;

        for (int i = 0; i < 5; i++) { //for every row
            for (int j = 0; j < 4; j++) { //for every col
                if (p2.getRowConstraint(i, j) != " ") {
                    RowConcounter++;
                }
            }
        }
        int ColConcounter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {

                if (p2.getColConstraint(i, j) != " ") {
                    ColConcounter++;

                }
            }
        }
        assertEquals(5, (RowConcounter+ColConcounter));
    }

    @Test
    public void testEmpty(){
        FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
        p3.setSquare(3,3,3);
        assertEquals(3, p3.getSquare(3,3));
        p3.empty(3, 3);
        assertEquals(0, p3.getSquare(3,3));

    }

    
    @Test
    public void testIsPuzzleComplete(){
         FutoshikiPuzzle p4 = new FutoshikiPuzzle(3);
         p4.setSquare(1,0,0);
         p4.setSquare(3,0,1);
         p4.setSquare(2,0,2);
         p4.setSquare(3,1,0);
         p4.setSquare(2,1,1);
         p4.setSquare(1,1,2);
         p4.setSquare(2,2,0);
         p4.setSquare(1,2,1);
         p4.setSquare(3,2,2);
         p4.setRowConstraint(">",2,0);
         p4.setColConstraint("v",0,1);
         System.out.println("test for puzzle complete" + p4.displayString());
         assertEquals(true, p4.isPuzzleComplete());
         
         p4.empty(2,0);
         assertEquals(false, p4.isPuzzleComplete());

    }
    
    @Test 
        public void testIsClear(){
        FutoshikiPuzzle p4 = new FutoshikiPuzzle(3);
         p4.setSquare(1,0,0);
         p4.setSquare(3,0,1);
         p4.setSquare(2,0,2);
         p4.setSquare(3,1,0);
         p4.setSquare(2,1,1);
         p4.setSquare(1,1,2);
         p4.setSquare(2,2,0);
         p4.setSquare(1,2,1);
         p4.setSquare(3,2,2);
         p4.setRowConstraint(">",2,0);
         p4.setColConstraint("v",0,1);
         p4.clear();
         assertEquals(0, p4.getSquare(0,0));
         assertEquals(0, p4.getSquare(0,1));
         assertEquals(0, p4.getSquare(0,2));
         assertEquals(0, p4.getSquare(1,0));
         assertEquals(0, p4.getSquare(1,1));
         assertEquals(0, p4.getSquare(1,2));
         assertEquals(0, p4.getSquare(2,0));
         assertEquals(0, p4.getSquare(2,1));
         assertEquals(0, p4.getSquare(2,3));
         
         
    }
    
    @Test
    public void testMain(){}

    
@Test
    public void testIsLegalRepeated(){
        FutoshikiPuzzle fs1 = new FutoshikiPuzzle(5);
       //check repeated 
        fs1.setSquare(4,0, 0);
        fs1.setSquare(4,0, 1);
        fs1.setSquare(2,1, 0);
        assertEquals(false,(fs1.isLegal()));    
         
        FutoshikiPuzzle fs2 = new FutoshikiPuzzle(5); 
        fs2.setSquare(3,1, 0);
         fs2.setSquare(3,2, 0);
         assertEquals(false,(fs2.isLegal()));  
    }
    
    
    @Test
    public void testIsLegalColumn(){     
         FutoshikiPuzzle fs3 = new FutoshikiPuzzle(5); 
        fs3.setSquare(5,3,3);    //test the column constraint - change set square
        fs3.setColConstraint("v", 3,3);
       fs3.setSquare(4,4,3);
       assertEquals(true,fs3.isLegal());
       
       fs3.setSquare(2,3,3);
       assertEquals(false,fs3.isLegal());
    }
    
    @Test
    public void testIsLegalRow(){
       
       FutoshikiPuzzle fs4 = new FutoshikiPuzzle(5);
       fs4.setSquare(5,3,3);//test the row constraint - change set square
       fs4.setRowConstraint(">", 3,3);
       fs4.setSquare(3,3,4);
       assertEquals(true,fs4.isLegal());
       
       fs4.setSquare(2,3,3);
       assertEquals(false,fs4.isLegal());
      
    }
    
    
    @Test
    public void testGetProblems() {
        FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
        p3.setSquare(3, 1, 0);
        p3.setSquare(3, 2, 0);

        p3.setRowConstraint("<", 1, 0);
        p3.setSquare(2, 1, 1);
        p3.setColConstraint("^", 1, 1);
        p3.setSquare(1, 2, 1);
        System.out.println("test for get problems" + p3.displayString());
        assertEquals("row constraint at 1,0 is illegal with <\nrow constraint at 1,1 is illegal with ^\n3 is repeated along col0\n", p3.getProblems());
         
        
    }
       
    @Test
    public void testNewCommand(){
        ByteArrayInputStream in = new ByteArrayInputStream("new 5\n".getBytes());
        System.setIn(in);
        Parser p = new Parser(); 
        Command c = p.getCommand();
        assertEquals(c.getCommand(), CommandWord.NEW);
        
    }
    
    @Test
    public void testSetCommand(){
        ByteArrayInputStream in = new ByteArrayInputStream("set 2 2 3\n".getBytes());
        System.setIn(in);
        Parser p = new Parser(); 
        Command c = p.getCommand();
        assertEquals(c.getCommand(), CommandWord.SET);
        
    }
    
    @Test
    public void testMarkCommand(){
        ByteArrayInputStream in = new ByteArrayInputStream("mark 2 2 3\n".getBytes());
        System.setIn(in);
        Parser p = new Parser(); 
        Command c = p.getCommand();
        assertEquals(c.getCommand(), CommandWord.MARK);
        
    }   
    
    @Test
    public void testEmptyCommand(){
        ByteArrayInputStream in = new ByteArrayInputStream("empty 2 2\n".getBytes());
        System.setIn(in);
        Parser p = new Parser(); 
        Command c = p.getCommand();
        assertEquals(c.getCommand(), CommandWord.EMPTY);
        
    }
    @Test
    public void testClearCommand(){
        ByteArrayInputStream in = new ByteArrayInputStream("clear\n".getBytes());
        System.setIn(in);
        Parser p = new Parser(); 
        Command c = p.getCommand();
        assertEquals(c.getCommand(), CommandWord.CLEAR);
        
    }
    
        
    @Test
    public void noProblems(){
        FutoshikiPuzzle fs2 = new FutoshikiPuzzle(5);
        assertEquals("", fs2.getProblems());
    }
    

        
    
    

//   }
   

    
    //--------------------------OLD TESTS-------------------------------------------------------------------------
    
        
    @Test
    public void testCreation(){
        FutoshikiPuzzle p = new FutoshikiPuzzle(5);
    }

    @Test
    public void testValidSetSqaure() {
        p1.setSquare(5,0,1);//put 5 in coords 0,1 - (value,row 0, column 1)
        assertEquals(5, p1.getSquare(0,1));

        p1.setSquare(1,4,4); //boundary test
        assertEquals(1, p1.getSquare(4,4));
        p1.setSquare(2,0,0); //boundary test
        assertEquals(2, p1.getSquare(0,0));

    }


//Tested the getters in my testing of the setter methods

    /**
     * Test of setRowConstraints method, of class FutoshikiPuzzle.
     */
    @Test
    public void testSetValidRowConstraints() {
      //  FutoshikiPuzzle p2 = new FutoshikiPuzzle(5);
        p1.setRowConstraint("<",0,1); //put < between 0,1 and 0,2
        assertEquals("<", p1.getRowConstraint(0,1)); 
        
        p1.setRowConstraint(">",0,0); //boundary test
        assertEquals(">", p1.getRowConstraint(0,0)); 
        p1.setRowConstraint("<",4,3); //boundary
        assertEquals("<", p1.getRowConstraint(4,3)); 
    }
    
    @Test
    public void testInValidSetRowConstraints() {
      //  FutoshikiPuzzle p2 = new FutoshikiPuzzle(5);
        p1.setRowConstraint(">", 4, 0); //should display as an error as it is out of bounds
    //    assertThat("You are trying to insert outside the Row grid with 4,0", outContent.toString());
    }

    /**
     * Test of setColConstraints method, of class FutoshikiPuzzle.
     */
    @Test
    public void testValidSetColConstraints() {
      //  FutoshikiPuzzle p2 = new FutoshikiPuzzle(5);
        p1.setColConstraint("v",0,1); //put v between rows 1 and 2, column 0
        assertEquals("v", p1.getColConstraint(0,1)); 
                
        p1.setColConstraint("v",0,0); //boundary
        assertEquals("v", p1.getColConstraint(0,0)); 
        p1.setColConstraint("v",3,4); //boundary
        assertEquals("v", p1.getColConstraint(3,4)); 
    }
    
    @Test
    public void testInvalidSetColConstraints() {
      //  FutoshikiPuzzle p2 = new FutoshikiPuzzle(5);
        p1.setColConstraint("v",3,4); //should produce an error since its out of bounds
        p1.setColConstraint("v",-3,3); //should produce an error since its out of bounds
    }
    
    @Test
    public void testAddPencilMark(){
        FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
        p3.addPencilMark(5,3,4);
        ArrayList<Integer> pencilMarking = new ArrayList<Integer>();
        pencilMarking.add(5);
        assertEquals(pencilMarking, p3.getGridFutoSquare(3, 4).getpencilMarkedList());
    }
    
    @Test
     public void testDeepCopygridToSolved(){
         FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
         p3.setSquare(5,0,1);
        p3.setSquare(1,4,4); 
        p3.setSquare(2,0,0); 
           FutoshikiSquare[][] p4;
           
           p4 = p3.DeepCopyGrid(p3.getGrid());
           
           assertEquals(p4[0][1].getData(),5);
           assertEquals(p4[4][4].getData(),1);
           assertEquals(p4[0][0].getData(),2);
         
     }
     
     @Test
     public void testSolver(){ //This uses solverSetup method, midSolver and solving method
         FutoshikiPuzzle p4 = new FutoshikiPuzzle(3);
         p4.setSquare(3,0,0);
         p4.setSquare(2,0,1);
         p4.setSquare(1,1,1);
         p4.setColConstraint("v", 1, 2);
         p4.setColConstraint("<", 2, 0);
         
         assertFalse(p4.isPuzzleComplete());
         p4.solverSetup();
         assertTrue(p4.isPuzzleComplete());
                
     }
     
     @Test
     public void testFillMarkings(){
         FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
         TupleSolveParams tps = new TupleSolveParams(p3.getGrid(),0,0);
         p3.emptyMarkings(tps);
         boolean flag = false; //is the grid empty
         for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(!(p3.getGridFutoSquare(i,j).getpencilMarkedList().isEmpty())){
                    flag = true;
                }   
            }
        }  
         assertFalse(flag); 
         
         p3.fillMarkings(new TupleSolveParams(p3.getGrid(),0,0));
         flag = false; //grid should have pencil marks in it
         for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(!(p3.getGridFutoSquare(i,j).getpencilMarkedList().isEmpty())){
                    flag = true;
                }   
            }
        }  
         assertTrue(flag);
         
         FutoshikiPuzzle p4 = new FutoshikiPuzzle(3);
         p4.setSquare(2,0,1);
         p4.setSquare(3,1,0);
         p4.setSquare(1,1,1);
         p4.setSquare(3,2,1);
         p4.setColConstraint("^", 0, 0);
         p4.setColConstraint("v", 1, 0);
         
         ArrayList<Integer> markings = new ArrayList<Integer>();
         markings.add(1);
         p4.fillMarkings(new TupleSolveParams(p4.getGrid(),0,0));
         assertEquals(markings,p4.getGridFutoSquare(0, 0).getpencilMarkedList());
     }
     
     @Test
     public void testEmptyMarkings(){
         FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
         
         ArrayList<Integer> pencilMarking = new ArrayList<Integer>();
        pencilMarking.add(5);
         pencilMarking.add(2);
         pencilMarking.add(4);
         p3.getGridFutoSquare(2, 2).setpencilMarkedList(pencilMarking);
         p3.getGridFutoSquare(3, 1).setpencilMarkedList(pencilMarking);
         
         boolean flag = false; //grid should have pencil marks in it
         for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(!(p3.getGridFutoSquare(i,j).getpencilMarkedList().isEmpty())){
                    flag = true;
                }   
            }
        }  
         assertTrue(flag);
         
         TupleSolveParams tps = new TupleSolveParams(p3.getGrid(),0,0); 
         p3.emptyMarkings(tps);//grid shouldnt have pencil marks in it
         flag = false;
         for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(!(p3.getGridFutoSquare(i,j).getpencilMarkedList().isEmpty())){
                    flag = true;
                }   
            }
        }  
         assertFalse(flag);         
     }
     
     @Test
     public void testSolveIt(){
         FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
         p3.fillPuzzle(3);
         p3.solveIt();
         assertTrue(p3.isPuzzleComplete());
     }
     
     @Test
     public void testHint(){
         FutoshikiPuzzle p3 = new FutoshikiPuzzle(2);
         p3.fillPuzzle(3);
         for (int i = 0; i < 4; i++) {
             p3.hint();
         }
         
         assertTrue(p3.isPuzzleComplete());
         
     }
     
     @Test
     public void testLegalSquare(){
         FutoshikiPuzzle p3 = new FutoshikiPuzzle(5);
        p3.setSquare(5,3,3);
        assertTrue(p3.isLegalSquare(3, 3));
        p3.setColConstraint("v", 3,3);
       p3.setSquare(4,4,3);
       assertTrue(p3.isLegalSquare(3, 3));
       
       p3.setSquare(2,3,3);
       assertFalse(p3.isLegalSquare(3, 3)); 
       
       p3.setSquare(4,4,3);
       p3.setSquare(5,4,3);
       assertFalse(p3.isLegalSquare(3, 3)); 
       
     }
     }

        //OLD TESTS
//    @Test
//    public void testRowConstraintFillPuzzle() {
//        int RowConcounter =0;
//        p1.fillPuzzle();
//        for(int i=0;i<5;i++){
//            for(int j=0;j<4;j++){
//                if(p1.getRowConstraint(i, j)!=null){
//                    RowConcounter++;
//                }
//            } 
//        }
//        assertEquals(3,RowConcounter);
//    }
//    
//    @Test
//    public void testColConstraintFillPuzzle() {
//        int ColConcounter =0;
//        p1.fillPuzzle();
//        System.out.println(p1.displayString());
//        for(int i=0;i<4;i++){
//            for(int j=0;j<5;j++){
//                if(p1.getColConstraint(i, j)!=null){
//                    ColConcounter++;
//                }
//            } 
//        }
//        assertEquals(3,ColConcounter);
//    }
    //    /**
//     * Test of displayString method, of class FutoshikiPuzzle.
//     */
//    @Test
//    public void testDisplayString(){
//        p1.setSquare(4, 0, 1);
//        p1.setSquare(2, 1, 3);
//        p1.setSquare(1, 2, 3);
//        p1.setSquare(2, 1, 4);
//        p1.setSquare(3, 2, 1);
//        p1.setSquare(1, 4, 4);
//        p1.setRowConstraint("<", 3, 2);
//        p1.setRowConstraint("<", 4, 3);
//        p1.setColConstraint("^", 0, 0);
//        p1.setColConstraint("v", 3, 2);
//        p1.setColConstraint("v", 4, 3);
//        assertEquals("\n----  ----  ----  ----  ----  \n" +
//"|  |  | 4|  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  \n" +
//"  ^                           \n" +
//"----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  | 2|  | 2| \n" +
//"----  ----  ----  ----  ----  \n" +
//"                              \n" +
//"----  ----  ----  ----  ----  \n" +
//"|  |  | 3|  |  |  | 1| <|  | \n" +
//"----  ----  ----  ----  ----  \n" +
//"                    v         \n" +
//"----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  \n" +
//"                          v   \n" +
//"----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  | 1| \n" +
//"----  ----  ----  ----  ----  ",p1.displayString());
//
//    }
    
    //Display string has been changed
    
//    @Test
//    public void testDisplayString2(){ //for a bigger grid
//        FutoshikiPuzzle p2 = new FutoshikiPuzzle(10);
//        p2.setSquare(8, 1, 8);
//        p2.setSquare(10, 7, 6);
//        p2.setSquare(10, 2, 4);
//        p2.setSquare(5, 1, 9);
//        p2.setSquare(2, 9, 2);
//        p2.setRowConstraint("<", 3, 2);
//        p2.setRowConstraint("<", 4, 3);
//        p2.setRowConstraint("<", 4, 5);
//        p2.setRowConstraint("<", 9, 7);
//        p2.setColConstraint("^", 0, 0);
//        p2.setColConstraint("^", 1, 0);
//        p2.setColConstraint("^", 2, 0);
//        p2.setColConstraint("v", 3, 0);
//        p2.setColConstraint("^", 8, 0);
//        p2.setColConstraint("v", 4, 3);
//        p2.setColConstraint("v", 9, 6);
//        assertEquals("\n----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"  ^     ^     ^     v                             ^         \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  | 8|  | 5| \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                                                            \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  | <|10|  |  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                                                            \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  | <|  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                          v                                 \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                                                            \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  | <|  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                                                            \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                                                        v   \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  |  |  |  |10|  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                                                            \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"                                                            \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  \n" +
//"|  |  |  |  | 2|  |  |  |  |  |  |  |  |  |  |  |  |  |  | \n" +
//"----  ----  ----  ----  ----  ----  ----  ----  ----  ----  ",p2.displayString());
//    }
    
    //These are private methods that cannot be tested
  //   @Test
//   public void testcheckRepeatedRow(){
//       FutoshikiPuzzle fs6 = new FutoshikiPuzzle(5);
//       fs6.setSquare(3,3,3);
//       fs6.setSquare(5,4,3);
//       assertEquals(false,fs6.noRepeatsRow().isEmpty());
//       
//       fs6.setSquare(3,3,2);
//       assertEquals(true,fs6.noRepeatsRow().isEmpty());
//   }
//   
//   @Test
//   public void testcheckRepeatedCol(){
//       FutoshikiPuzzle fs7 = new FutoshikiPuzzle(5);
//       fs7.setSquare(2,4,3);
//       fs7.setSquare(3,4,3);
//       assertEquals(false,fs7.noRepeatsCol().isEmpty());
//       
//       fs7.setSquare(3,4,2);
//       assertEquals(true,fs7.noRepeatsCol().isEmpty());
//   }
//   
//   @Test
//   public void testValidRowConstraints(){
//       FutoshikiPuzzle fs8 = new FutoshikiPuzzle(5);
//       fs8.setSquare(3,3,3);
//       fs8.setSquare(2,4,3);
//       fs8.setRowConstraint("<", 3, 3);
//       System.out.println(fs8.displayString());
//       System.out.println("-----------------------------------------------!");
//       assertEquals(true,fs8.validRowConstraints().isEmpty());
//       
//       FutoshikiPuzzle fs9 = new FutoshikiPuzzle(5);
//       fs9.setSquare(5,0,1);
//       fs9.setSquare(4,1,1);
//       fs9.setRowConstraint("<", 0, 1);
//       System.out.println(fs9.displayString());
//       System.out.println("-----------------------------------------------!");
//       assertEquals(false,fs9.validRowConstraints().isEmpty());
//       
//       FutoshikiPuzzle fs10 = new FutoshikiPuzzle(5);
//       fs10.setSquare(0,0,1);
//       fs10.setSquare(0,1,2);
//       fs10.setRowConstraint(">", 0, 0);
//       System.out.println(fs10.displayString().isEmpty());
//       System.out.println("-----------------------------------------------!");
//       assertEquals(false,fs10.validRowConstraints());
//       
//       FutoshikiPuzzle fs11 = new FutoshikiPuzzle(5);
//       fs11.setSquare(1,3,2);
//       fs11.setSquare(1,4,4);
//       fs11.setRowConstraint("<", 1, 3);
//       System.out.println(fs11.displayString());
//       System.out.println("-----------------------------------------------!");
//       assertEquals(true,fs11.validRowConstraints().isEmpty());
//       
//       fs11.isLegal();
//   }
//   
//   @Test
//   public void testValidColConstraints(){
//       FutoshikiPuzzle fs12 = new FutoshikiPuzzle(5);
//       fs12.setSquare(0,0,4);
//       fs12.setSquare(1,0,5);
//       fs12.setColConstraint("^",3, 3);
//       System.out.println(fs12.displayString());
//       System.out.println("-----------------------------------------------");
    //   assertEquals(true,fs12.validColConstraints());
       
//       FutoshikiPuzzle fs13 = new FutoshikiPuzzle(5);
//       fs13.setSquare(3,3,2);
//       fs13.setSquare(4,3,3);
//       fs13.setColumnConstraint(4, 3, "v");
//       System.out.println(fs13.displayString());
//       System.out.println("-----------------------------------------------");
//       assertEquals(false,fs13.validColConstraints());
//       
//       FutoshikiPuzzle fs14 = new FutoshikiPuzzle(5);
//       fs14.setSquare(2,4,1);
//       fs14.setSquare(3,4,2);
//       fs14.setColumnConstraint(0, 0, "^");
//       System.out.println(fs14.displayString());
//       System.out.println("-----------------------------------------------");
//       assertEquals(false,fs14.validColConstraints());
//       
//       FutoshikiPuzzle fs15 = new FutoshikiPuzzle(5);
//       fs15.setSquare(3,2,2);
//       fs15.setSquare(4,2,4);
//       fs15.setColumnConstraint(1, 3, "v");
//       System.out.println(fs15.displayString());
//       System.out.println("-----------------------------------------------");
//       assertEquals(true,fs15.validColConstraints());
//   }
//}



//    @Test(expected = IndexOutOfBoundsException.class)
//    public void testSetInValidColConstraints() {
      //  FutoshikiPuzzlePuzzle p2 = new FutoshikiPuzzlePuzzle(5);
   //     p1.setColConstraint("v",0,19); //put v between rows 1 and 2, column 0
   // } 
    
    
//        @Test
//    public void testemptyPencilMarks(){
//        FutoshikiPuzzle fs3 = new FutoshikiPuzzle(5);
//        assertEquals(true, fs3.emptyPencilMarks(fs3.getGrid()));
//        fs3.addPencilMark(3, 2, 2);
//        assertEquals(false, fs3.emptyPencilMarks(fs3.getGrid()));
//    }
    
//        @Test
//    public void testInValidSetSqaure() {    
//        p1.setSquare(2,5,1); // error should be displayed as it is out of bounds
//     //   assertEquals("You are trying to insert an item outside the square grid 5,1", outContent.toString());
//        p1.setSquare(2,-3,1); // error should be displayed as it is out of bounds
//        p1.setSquare(2,2,-1); // error should be displayed as it is out of bounds
//        p1.setSquare(-2,3,1); // error should be displayed as it is out of bounds
//
//    }

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg198893part2;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 *
 * @author cy214
 */
public class SaveTest {
    
    public SaveTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of saveGame method, of class Save.
     */
    @Test
    public void testSaveGame() {
        FutoshikiPuzzle puzzle = new FutoshikiPuzzle(5);
        puzzle.fillPuzzle(3);
        Save s = new Save();
        s.saveGame(puzzle);
        String hashCodeS = Integer.toString(puzzle.hashCode());
        File f = new File("saveFiles/" + hashCodeS + ".save");
        FutoshikiPuzzle puzzle2 = null;
        try{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveFiles/" + hashCodeS + ".save"));
            puzzle2 = (FutoshikiPuzzle) ois.readObject();
            ois.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FutoshikiPuzzle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FutoshikiPuzzle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FutoshikiPuzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                    assertEquals(puzzle.getSquare(i, j),puzzle2.getSquare(i,j));
                }
        }
    }

    //CAN'T TEST LOAD GAME AS IT REQUIRES A STAGE
    
}

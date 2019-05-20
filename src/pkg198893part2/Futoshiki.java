/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg198893part2;



/**
 *
 * @author cy214
 */
public class Futoshiki {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Parser p = new Parser();
        Command c = null;
        System.out.print("Commands (row,column value):");
        System.out.println("\n NEW - creates a new puzzle");
        System.out.println("SET - adds a number to the square");
        System.out.println("CLEAR - clear the board");
        System.out.println("MARK - add a pencil mark");
        System.out.println("EMPTY - empty a square ");                                             
        System.out.print("Enter a command>");
     //   p.getCommand();
        FutoshikiPuzzle p1=null;
        while ((c = p.getCommand()) != null && c.getCommand() != CommandWord.QUIT) {
            System.out.println(c);

            
            switch (c.getCommand()) {
            case NEW: //this creates a new puzzle
                    p1 = new FutoshikiPuzzle(c.getValue());
                    p1.fillPuzzle(2);
                break;
            case SET: //add a number to a square
                    if(p1 !=null){
                        p1.setSquare(c.getValue(),c.getRow(),c.getColumn());
                    }
                break;
            case CLEAR: //clear the whole board
                    if(p1 !=null){
                        p1.clear();
                    }
                break;
            case MARK: //set a pencil mark
                    if(p1 !=null){
                        p1.addPencilMark(c.getValue(),c.getRow(),c.getColumn());
                    }
                break;
            case EMPTY: //empty a square
                if(p1 !=null){
                        p1.empty(c.getRow(),c.getColumn());
                    }
            default:
                break;
        }
            System.out.println(p1.displayString());
                        System.out.print(">");
            
        
            if (p1!= null && p1.isPuzzleComplete()) {
                System.out.println("Puzzle is complete");
                c.setCommand(CommandWord.QUIT);
            
        }
        
    }
    }
    
}

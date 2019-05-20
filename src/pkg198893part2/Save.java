package pkg198893part2;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Save {

    public Save() {
    }

    public void saveGame(FutoshikiPuzzle puzzle){
//        FileChooser fileChooser = new FileChooser();
//        File f = fileChooser.showOpenDialog(stage);
        try{
            File f = new File("saveFiles/" + puzzle.hashCode() + ".save");
        //    File f = new File("src\saveFiles\" +  + puzzle.hashCode() + \".save\");
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
            os.writeObject(puzzle);
            os.close();
        }
        catch(IOException ex){
            Logger.getLogger(FutoGUI.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    public FutoshikiPuzzle loadGame(Stage stage) {
        FutoshikiPuzzle puzzle = null;

        try {
            File f = chooseFile(stage);
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(new FileInputStream(f));//new FileInputStream("saveFiles/1584657794.save")
            puzzle = (FutoshikiPuzzle) ois.readObject();
            ois.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FutoshikiPuzzle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FutoshikiPuzzle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FutoshikiPuzzle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return puzzle;
    }

    private File chooseFile(Stage stage){
        FileChooser fc = new FileChooser();
        return fc.showOpenDialog(stage);
    }

}

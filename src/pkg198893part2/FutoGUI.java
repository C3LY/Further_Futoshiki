/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg198893part2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author cy214
 */
public class FutoGUI extends Application implements Serializable {
    Stage primaryStage;
    int gridSize = 0;
    int difficulty;
    FutoshikiPuzzle p1;
    Button[][] buttonGrid;
    BorderPane bp_mainGame;
    ToggleButton tb_markingMode = new ToggleButton("marking mode");
    TextField row = new TextField("0");
    TextField col = new TextField("0");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Futoshiki");
        mainMenu();
        this.primaryStage.show();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private Scene mainMenu(){
        VBox vbox_mainButtons = new VBox();
        Button btn_new = new Button("New");
        Button btn_load = new Button("Load");
        Button btn_quit = new Button("Quit");
        
        btn_new.setStyle("-fx-font: 30 Helvetica; -fx-base: #61eddf");
        btn_new.setPadding(new Insets(10));
        btn_new.setPrefWidth(200);
        btn_load.setStyle("-fx-font: 30 Helvetica; -fx-base: #61eddf");
        btn_load.setPadding(new Insets(10));
        btn_load.setPrefWidth(200);
        btn_quit.setStyle("-fx-font: 30 Helvetica; -fx-base: #61eddf");
        btn_quit.setPadding(new Insets(10));
        btn_quit.setPrefWidth(200);
        vbox_mainButtons.setSpacing(10);
        vbox_mainButtons.setAlignment(Pos.CENTER);
     //   vbox_mainButtons.setStyle("-fx-background-color:#008577");
        BackgroundImage bgimg= new BackgroundImage(new Image("file:src\\pkg198893part2\\puppy.jpg",800,600,false,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        vbox_mainButtons.setBackground(new Background(bgimg));
        
        vbox_mainButtons.setPrefHeight(120);
        vbox_mainButtons.setPrefWidth(160);
        
        vbox_mainButtons.getChildren().addAll(btn_new,btn_load,btn_quit);
        
        btn_new.setOnAction(e -> {
            newGame();
        });
        
        btn_load.setOnAction(e -> {
            Save s = new Save();
            p1 = s.loadGame(primaryStage);
            mainGame();
        });
        
        btn_quit.setOnAction(e -> {
            System.exit(0);
        });
        
        Scene scene = new Scene(vbox_mainButtons, 600, 600);

        primaryStage.setScene(scene);
        
        return scene;
    }

    /**
     * The main interface with the grid and the newGame bar
     * @return scene
     */
    private Scene mainGame() {
        bp_mainGame = new BorderPane();
        bp_mainGame.setStyle("-fx-background-color:#008577");

        //----------------------Menu--------------------------------------------------
        Menu m = new Menu("File");
        MenuBar menubar = new MenuBar();
        menubar.getMenus().add(m);
        MenuItem newB = new MenuItem("New");
        MenuItem saveB = new MenuItem("Save");
        MenuItem loadB = new MenuItem("Load");


        m.getItems().add(saveB);
        m.getItems().add(loadB);
        m.getItems().add(newB);
        bp_mainGame.setTop(menubar);

        newB.setOnAction(e -> {
            finish();
        });

        saveB.setOnAction(e ->{
                Save s = new Save();
                s.saveGame(p1);
        });

        loadB.setOnAction(e -> {
            Save s = new Save();
            p1 = s.loadGame(primaryStage);
            mainGame();
                });
        
//----------------------------------------------------------------------------

        bp_mainGame.setCenter(displayStringGUI(gridSize)); //the grid
       


//-------Problems with the grid is shown on the right
        TextArea tf_problems = new TextArea();
        tf_problems.setPrefHeight(600);
        tf_problems.setPrefWidth(400);
        tf_problems.setWrapText(true);
        tf_problems.setDisable(true);
        tf_problems.setStyle("-fx-opacity: 1.0; -fx-font: 20 arial; -fx-color: #140f02; -fx-base: #37c4b9");
        bp_mainGame.setRight(tf_problems);
        //  bp_mainGame.setBottom(solverB());


        HBox bottom = new HBox();

        row.setDisable(true);
        col.setDisable(true);
        bottom.getChildren().addAll(solverB(), fillMarkingsB(), tb_markingMode, hint(), stepSolverB(), row, col); //bottom bar extra features
        bp_mainGame.setBottom(bottom);

        tb_markingMode.setOnAction(e -> {
            mainGame();
        });


        Scene scene = new Scene(bp_mainGame);
        primaryStage.setScene(scene);
        return scene;
    }

    /**
     * Displaying the grid
     * @param size - size of the board
     * @return GridPane
     */
    public GridPane displayStringGUI(int size) { //place the board
        int x = 0;
        int y = 0;
        buttonGrid = new Button[size][size];
        GridPane gridGUI = new GridPane();
        gridGUI.setHgap(10);
        gridGUI.setVgap(10);
        gridGUI.setPadding(new Insets(30));

        for (int r = 0; r < size; r++) {
            x = 0;
            for (int c = 0; c < size; c++) {
                Button btn_square = addSquare(p1.getSquare(r, c), r, c);
                buttonGrid[r][c] = btn_square;
                if (!(p1.getGridFutoSquare(r, c).isEditable())) {
                    btn_square.setDisable(true);
                    if (Integer.parseInt(btn_square.getText()) >= 10) {
                        btn_square.setStyle("-fx-opacity: 1.0; -fx-font: 14 arial; -fx-base: #e0ebeb");
                    } else {
                        btn_square.setStyle("-fx-opacity: 1.0; -fx-font: 22 arial; -fx-base: #e0ebeb");
                    }
                }
                gridGUI.add(addSquareS(btn_square, r, c), x, y);
                if (c < size - 1) {
                    gridGUI.add(addConstraint(p1.getRowConstraint(r, c)), x + 1, y);
                }
                if (r < size - 1) {
                    gridGUI.add(addConstraint(p1.getColConstraint(r, c)), x, y + 1);
                }
                x = x + 2;
            }
            y = y + 2;
        }
        return gridGUI;
    }

    /**
     * solve button - solves the grid
     * @return Button
     */
    public Button solverB() {
        Button btn_solver = new Button("solver");
        btn_solver.setOnAction(e -> {
            p1.solveIt();
            mainGame();
        });
        return btn_solver;
    }

    /**
     * hint button - solves one of the squares
     * @return Button
     */
    public Button hint() {
        Button btn_hint = new Button("hint");
        btn_hint.setOnAction(e -> {
            p1.hint();
            mainGame();
        });
        return btn_hint;
    }

    /**
     * shows how depth first search is used to solve the grid
     * @return Button
     */
    private Button stepSolverB() {
        Button btn_stepSolver = new Button("Step solver");
        btn_stepSolver.setOnAction(e -> {
            if (row.getText().equals(("0")) && col.getText().equals("0")) {
                p1.emptyMarkings(new TupleSolveParams(p1.getGrid(), 0, 0)); //refresh the markings before solving
                p1.fillMarkings(new TupleSolveParams(p1.getGrid(), 0, 0));
            }
            if (!p1.isPuzzleComplete()) {
                TupleSolveParams params = p1.solving(new TupleSolveParams(p1.getGrid(), Integer.parseInt(row.getText()), Integer.parseInt(col.getText())));
                p1.setGrid(params.grid);
                col.setText(Integer.toString(params.colC));
                row.setText(Integer.toString(params.rowC));
                mainGame();
            }
            else{
                finish();
            }

        });
        return btn_stepSolver;
    }

    /**
     * marks in  all the possible numbers in each squares
     * @return Button
     */
    public Button fillMarkingsB() {
        Button btn_marking = new Button("fill markings for me");
        btn_marking.setOnAction(e -> {
            p1.fillMarkings(new TupleSolveParams(p1.getGrid(), 0, 0));
            mainGame();
        });
        return btn_marking;
    }


    /**
     * Starting newGame to create puzzle
     * @return Scene
     */
    private Scene newGame() {
        BorderPane bp_menu = new BorderPane();
        bp_menu.setStyle("-fx-background-color:#008577");
        VBox VBox_Difficulty = new VBox();

        RadioButton rbtn_easy = new RadioButton("Easy");
        RadioButton rbtn_medium = new RadioButton("Medium");
        RadioButton rbtn_hard = new RadioButton("Hard");
        rbtn_easy.setUserData("Easy");
        rbtn_medium.setUserData("Medium");
        rbtn_hard.setUserData("Hard");
        rbtn_easy.setStyle("-fx-font: 15 Helvetica; -fx-color: #ebf7f6; -fx-base: #61eddf");
        rbtn_medium.setStyle("-fx-font: 15 Helvetica; -fx-color: #ebf7f6; -fx-base: #61eddf");
        rbtn_hard.setStyle("-fx-font: 15 Helvetica; -fx-color: #ebf7f6; -fx-base: #61eddf");

        BackgroundImage bgimg= new BackgroundImage(new Image("file:src\\pkg198893part2\\teacup-puppy.jpg",400,300,false,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        VBox_Difficulty.setBackground(new Background(bgimg));
           VBox_Difficulty.setPrefWidth(160);
           VBox_Difficulty.setPrefHeight(120);
        rbtn_easy.setSelected(true);

        final ToggleGroup group_Difficulty = new ToggleGroup();
        rbtn_easy.setToggleGroup(group_Difficulty);
        rbtn_medium.setToggleGroup(group_Difficulty);
        rbtn_hard.setToggleGroup(group_Difficulty);

        //if (group.getSelectedToggle() != null) {

        Spinner<Integer> spin_gridSize = new Spinner(2, 30, 5);

        Button btn_ok = new Button("OK");
        btn_ok.setStyle("-fx-font: 10 Helvetica; -fx-base: #61eddf");
        btn_ok.setPrefWidth(40);

        VBox_Difficulty.getChildren().addAll(rbtn_easy, rbtn_medium, rbtn_hard, spin_gridSize, btn_ok);
        VBox_Difficulty.setSpacing(15);
        VBox_Difficulty.setAlignment(Pos.CENTER_LEFT);
        VBox_Difficulty.setPadding(new Insets(10));

        bp_menu.setPadding(new Insets(40));
        bp_menu.setCenter(VBox_Difficulty);


        Scene scene = new Scene(bp_menu, 480 , 360);

        primaryStage.setScene(scene);

        btn_ok.setOnAction(e -> {
            gridSize = spin_gridSize.getValue();
            difficulty = difficultyTranslation(group_Difficulty.getSelectedToggle().getUserData().toString());
            p1 = new FutoshikiPuzzle(gridSize);
            p1.fillPuzzle(difficulty);
            mainGame();
        });

        row.setText("0");
        col.setText("0");


        return scene;

    }

    /**
     * changes the selection from the newGame into numbers
     * @param difficultyName
     * @return int
     */
    private int difficultyTranslation(String difficultyName) {
        int div = 0;
        if (difficultyName.equals("Easy")) {
            div = 3;
        } else if (difficultyName.equals("Medium")) {
            div = 6;
        } else if (difficultyName.equals("Hard")) {
            div = 10;
        }
        return div;
    }

    /**
     * Square for grid
     * @param initialNum
     * @param x
     * @param y
     * @return
     */
    private Button addSquare(int initialNum, int x, int y) {
        Button btn_square;
        if (initialNum == 0) {
            btn_square = new Button(" ");
        } else {
            btn_square = new Button(Integer.toString(initialNum));
        }
        btn_square.setPrefWidth(40);
        btn_square.setPrefHeight(20);

        if (initialNum >= 10) {
            btn_square.setStyle("-fx-opacity: 0.7;-fx-font: 10 arial; -fx-background-color: linear-gradient(from 10px 10px to 100px 100px, #fdd8c9, #98fffc);");
        } else {
            btn_square.setStyle("-fx-opacity: 0.7;-fx-font: 22 arial; -fx-background-color: linear-gradient(from 10px 10px to 100px 100px, #fdd8c9, #98fffc);");
        }


        btn_square.setOnAction(e -> {

            if (btn_square.getText().equals(" ")) {
                btn_square.setText("1");
                p1.setSquare(Integer.parseInt(btn_square.getText()), x, y);
                //   problemsTable();
                System.out.println(p1.displayString());
            } else {
                String text = Integer.toString((Integer.parseInt(btn_square.getText()) + 1) % (gridSize + 1));
//                p1.setSquare(Integer.parseInt(text), gridSize, gridSize);
                btn_square.setText(text);
                p1.setSquare(Integer.parseInt(btn_square.getText()), x, y);
                if (btn_square.getText().equals("0")) {
                    btn_square.setText(" ");
                }
            }
            if (!(p1.isLegalSquare(x, y))) {
                if (initialNum >= 10) {
                    btn_square.setStyle("-fx-opacity: 0.7; -fx-font: 10 arial; -fx-base: #ed192d");
                } else {
                    btn_square.setStyle("-fx-opacity: 0.7; -fx-font: 22 arial; -fx-base: #ed192d");
                }


            } else {
                if (initialNum >= 10) {
                    btn_square.setStyle("-fx-opacity: 0.7;-fx-font: 10 arial; -fx-background-color: linear-gradient(from 10px 10px to 100px 100px, #fdd8c9, #98fffc);");
                } else {
                    btn_square.setStyle("-fx-opacity: 0.7;-fx-font: 22 arial; -fx-background-color: linear-gradient(from 10px 10px to 100px 100px, #fdd8c9, #98fffc);");
                }

            }
            fillErrors();
            System.out.println(p1.displayString());
            if (p1.isPuzzleComplete()) {
                finish();
            }
        });
        return btn_square;

    }

    /**
     * marking Square for each square in the grid
     * @param x
     * @param y
     * @return TextArea
     */
    private TextArea markingGrid(int x, int y) {
        TextArea squareMarks = new TextArea();
        squareMarks.setPrefWidth(40);
        squareMarks.setPrefHeight(20);
        squareMarks.setWrapText(true);
        squareMarks.setStyle("-fx-font-size: 80%; -fx-text-overrun: clip");
        String text = "";
        ArrayList<Integer> marksList = p1.getGridFutoSquare(x, y).getpencilMarkedList();
        for (int i = 0; i < marksList.size(); i++) {
            text += marksList.get(i);
        }
        squareMarks.setText(text);
        //squareMarks.setText(p1.getGridFutoSquare(x,y).getpencilMarked());
        return squareMarks;
    }


    private StackPane addSquareS(Button squareB, int x, int y) { 
        StackPane square = new StackPane();

        if (tb_markingMode.isSelected()) {
            square.getChildren().add(squareB); //can i use toFront() instead?
            TextArea marking = markingGrid(x, y);
            if (p1.getGridFutoSquare(x, y).isEditable()) {
                marking.setStyle("-fx-opacity: 0.3; -fx-font-size: 60%; -fx-text-overrun: clip");
            } else {
                marking.setStyle("-fx-opacity: 0");
            }
            square.getChildren().add(marking);
        } else {
            square.getChildren().add(markingGrid(x, y));
            square.getChildren().add(squareB);
        }

        square.setPrefWidth(40);
        square.setPrefHeight(20);
        return square;
    }

    /**
     * Dialog box with finishing message
     */
    private void finish() {
        Alert finishDialog = new Alert(AlertType.NONE);

        Image image;
        if (p1.isPuzzleComplete()) {
            finishDialog.setTitle("Congrats");
            finishDialog.setHeaderText("You're finished!!! ");
            image = new Image("file:src\\pkg198893part2\\dog.jpg", 50, 50, false, false);

        } else {
            finishDialog.setTitle("Finish Up?");
            finishDialog.setHeaderText("Awwwww, you're leaving... ");
            image = new Image("file:src\\pkg198893part2\\sad.jpg", 50, 50, false, false);
        }
        
        finishDialog.setContentText("What would you like to do?");

        ButtonType btn_new = new ButtonType("Main Menu");
        ButtonType btn_close = new ButtonType("Close game");

        finishDialog.getButtonTypes().setAll(btn_new, btn_close);

        ImageView imageView = new ImageView(image);
        finishDialog.setGraphic(imageView);

        Optional<ButtonType> result = finishDialog.showAndWait();


        if (result.get() == btn_new) {
            mainMenu();
        } else if (result.get() == btn_close) {
            System.exit(0);
        }
    }
    

    /**
     * adding constraints to be printed in the grid
     * @param constraint
     * @return Label
     */
    private Label addConstraint(String constraint) {
        Label lbl_cons = new Label(constraint);
        //   lbl_cons.setPrefWidth(10);
        //  lbl_cons.setPrefHeight(5);
        //    lbl_cons.setPadding(new Insets(10,2,2,2));
        lbl_cons.setStyle("-fx-font: 22 arial;");
        lbl_cons.setPadding(new Insets(0, 15, 0, 15));
        return lbl_cons;
    }

    /**
     * fill the errors in the right error box on the screen
     */
    private void fillErrors() {
        Node bpr = bp_mainGame.getRight();

        ((TextArea) bpr).setText(p1.getProblems());
    }
}

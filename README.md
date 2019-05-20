#Displaying the Data
displayStringGUI is the method that displays the grid. Every square in the puzzle is converted to a button. The button can be clicked to change the number. The data of the square is stored as the button text. Each of these buttons are put in a 2d array which mimics the grid. My constraints are converted to labels. All the labels and buttons are stored and diplayed using a GridPane.
The GUI is updated by calling the mainGame method which calls displayStringGUI and the other elements in my interface when something is changed. 


#Editing the data
When a button square is created, it has methods that updates the data in the puzzle when the button is clicked. These methods are in addSquare. When a button square is clicked, the program checks if the number added is legal by using isLegalSquare. If it is not legal, then it changes the colour of the button. 

#Optional Features
* Puppy as the background in the main menu - take an image, create a BackGround image object, set as the background
* Save Function - I've got a save class, my save function takes in a FutoshikiPuzzle, it is saved using an ObjectOutputStream which takes in Java Objects. 
* Load Function - I used a file chooser which allows the user to pick the file from file explorer. The puzzle is read and returned. Saved to a puzzle variable in FutoGUI
* Show solve grid button - A completed puzzle is created when the game is started. This puzzle is saved. To stop it from referencing the current puzzle I had to do a deep copy to the other FutoshikiPuzzle variable. When solve button is pressed, the grid is set to the solvedpuzzle. All tiles become uneditable.
* Pencil markings in squares - I use a stackpane to hold the button square and a textArea. The textArea is ued to hold the pencil markings. A button allows the textarea to go on top of the stackpane which allows the user to make markings. When the button is selected again the button square goes to the top.
* fill all squares with possible numbers in pencil marks - I store pencil markings in the FutoshikiSquare. I use this for my DFS, to store or the possible numbers for a square. It goes through each square in the grid and put a number in, if it is legal, then a pencil mark of the number is added. 
* show a hint - it chooses a random coordinate, it uses the solved puzzle to find what number should be in that square.
* Step by step solver (showing DFS), also showing coordinates of the square the solver is looking at. This calls my solving method and updates the game GUI. The markings are shown (which holds the possible numbers for each square).
* show description of all the errors in the grid - i have a method called getProblems from Part 2. This is called to a textbox. The game is refreshed whenever a button square is pressed, this textbox gets updated too.
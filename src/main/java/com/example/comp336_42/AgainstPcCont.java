package com.example.comp336_42;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

public class AgainstPcCont {
    static int counter=0;
    static char player = 'x';
    static char[] arr;
    static int[][] winCases = {{0, 1, 2},/*row1*/{3, 4, 5},/*row2*/{6, 7, 8},/*row3*/{0, 3, 6},/*column1*/{1, 4, 7},/*column2*/{2, 5, 8},/*column3*/{0, 4, 8},/*diagonal1*/{2, 4, 6}/*diagonal2*/};
    public static Button[] buttons = new Button[9];
    public Button button00;
    public Button button01;
    public Button button02;
    public Button button10;
    public Button button11;
    public Button button12;
    public Button button20;
    public Button button21;
    public Button button22;


    @FXML
    private RadioButton xSelected = new RadioButton();
    ;

    @FXML
    private RadioButton oSelected = new RadioButton();
    ;

    @FXML
    private ToggleGroup choiceToggleGroup;


    @FXML
    private Button btnEasy = new Button();


    @FXML
    private Button btnMedium = new Button();

    @FXML
    private Button btnAdvanced = new Button();


    @FXML
    public void initialize() {
//        btnEasy=new Button();
//        btnMedium=new Button();
//        btnAdvanced=new Button();
        arr = new char[9];


        btnEasy.setDisable(true);
        btnMedium.setDisable(true);
        btnAdvanced.setDisable(true);
//
//        xSelected=new RadioButton();
//        oSelected=new RadioButton();

        buttons[0] = button00;
        buttons[1] = button01;
        buttons[2] = button02;
        buttons[3] = button10;
        buttons[4] = button11;
        buttons[5] = button12;
        buttons[6] = button20;
        buttons[7] = button21;
        buttons[8] = button22;


        choiceToggleGroup = new ToggleGroup();
        xSelected.setToggleGroup(choiceToggleGroup);
        oSelected.setToggleGroup(choiceToggleGroup);

        // Add a change listener to the ToggleGroup
        choiceToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if (choiceToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) choiceToggleGroup.getSelectedToggle();
                    if ("Play As X".equals(selected.getText())) {
                        player = 'x';
                    } else if ("Play As O".equals(selected.getText())) {
                        player = 'o';
                    }
                    btnEasy.setDisable(false);
                    btnMedium.setDisable(false);
                    btnAdvanced.setDisable(false);

                    btnEasy.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            String choice = showChoosePlayerDialog();

                            if (choice.equals("X")) {//the normal case
                                OpenGameEasy("X");
                            } else if (choice.equals("O")) {//we will do something
                                OpenGameEasy("O");
                            }

                        }
                    });

                    btnMedium.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            String choice = showChoosePlayerDialog();

                            if (choice.equals("X")) {//the normal case
                                OpenGameEasy("X");
                            } else if (choice.equals("O")) {//we will do something
                                OpenGameEasy("O");
                            }


                        }
                    });

                    btnAdvanced.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            String choice = showChoosePlayerDialog();

                            if (choice.equals("X")) {//the normal case
                                OpenGameExpert("X");
                            } else if (choice.equals("O")) {//we will do something
                                OpenGameExpert("O");
                            }
//                            OpenGameExpert("O");


//                            OpenGameExpert();
                        }
                    });

                }
            }
        });
    }

    public void OpenGameExpert(String X_O) {

        String plyr = player + "";
        if (plyr.equalsIgnoreCase(X_O)) {//user is starting
            System.out.println("user is starting");

            try {
                Main.ShowBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            if (player == 'o') {
//                int r = (int) (Math.random() * 9);
//                buttons[r].setText("X");
//                buttons[r].setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
//
////                int r = (int) (Math.random() * 9);
////                buttons[r].setText("O");
////                buttons[r].setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
//
//            }
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText("");
            }

            for (int i = 0; i < buttons.length; i++) {

                Button temp = buttons[i];

                temp.setOnAction(event -> {
                    if (temp.getText().equals("")) {
                        if (Won(buttons) == 'n') {
                            temp.setStyle("-fx-text-fill:  #f8d320; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
                            temp.setText(player == 'x' ? "X" : "O");
                            if (Won(buttons) != 'n') {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setContentText("Player " + Won(buttons) + " Wins!");
                                alert.showAndWait();
                            } else {
                                if (!boardIsFull()) {
                                    ComputerPlay.MODE = "Expert";
                                    int l = ComputerPlay.MakeMove(getBoard());
                                    System.out.println(l);
                                    buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
                                    buttons[l].setStyle("-fx-text-fill: White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
                                    if (Won(buttons) != 'n') {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setContentText("Player " + Won(buttons) + " Wins!");
                                        alert.showAndWait();
                                    }
                                }
                            }
                        }
                    }
                });
            }


        } else if (!plyr.equalsIgnoreCase(X_O)) {//PC is starting
            System.out.println("PC is starting");
            try {
                Main.ShowBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText("");
            }

            char pcSymbol;  // Declare the variable 'player' of type char
            if (player == 'x') {
                pcSymbol = 'O';
            } else {
                pcSymbol = 'X';
            }

            ComputerPlay.MODE = "Expert";
            int firstMove = ComputerPlay.MakeMove(getBoard());
            System.out.println(firstMove);
            buttons[firstMove].setText((player == 'x' ? 'O' : 'X') + "");
            buttons[firstMove].setStyle("-fx-text-fill: White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");


//            ComputerPlay.MODE = "Expert";
//            int l = ComputerPlay.MakeMove(getBoard());
//            System.out.println(l);
//            buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
//            buttons[l].setStyle("-fx-text-fill: White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
//            int firstL = randomMove;
            //                                    buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
//            System.out.println("second"+ (player == 'x' ? 'O' : 'X'));

//            int randomMove = (int) (Math.random() * 9);
//            System.out.println(randomMove);// l is the number of the block that pc has chosen
//            System.out.println("pc isssssssssssss:" + pcSymbol);
//            buttons[randomMove].setText(pcSymbol + "");
//            buttons[randomMove].setStyle("-fx-text-fill: White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");


//            if (player == 'o') {
//                int r = (int) (Math.random() * 8);
//                buttons[r].setText("X");
////                buttons[r].setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: blue;-fx-border-width: 4;-fx-border-color:  #f8d320");
//                buttons[r].setStyle("-fx-text-fill:  #f8d320; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
//
//            }

            for (int i = 0; i < buttons.length; i++) {

                Button temp = buttons[i];

                temp.setOnAction(event -> {
                    if (temp.getText().equals("")) {
                        if (Won(buttons) == 'n') {
                            temp.setStyle("-fx-text-fill:  #f8d320; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
                            temp.setText(player == 'x' ? "X" : "O");
                            if (Won(buttons) != 'n') {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setContentText("Player " + Won(buttons) + " Wins!");
                                alert.showAndWait();
                            } else {
                                if (!boardIsFull()) {
                                    ComputerPlay.MODE = "Expert";
                                    int l = ComputerPlay.MakeMove(getBoard());
                                    System.out.println(l);
                                    buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
                                    buttons[l].setStyle("-fx-text-fill: White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");

                                    if (Won(buttons) != 'n') {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setContentText("Player " + Won(buttons) + " Wins!");
                                        alert.showAndWait();
                                    }
                                }
                            }
                        }
                    }
                });
            }


        }


    }


    public void OpenGameEasy(String X_O) {
        if (X_O.equals("X")) {//X Starts
            System.out.println("X Starts");
            try {
                Main.ShowBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }

            char pcSymbol;
            if (player == 'x') {
                pcSymbol = 'O';
            } else {
                pcSymbol = 'X';
            }
            //two cases
            //player    = x --> player  is the one who starts
            //pc        = x --> pc      is the one who starts

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText("");
            }
            if (player == 'o') {//if pc is starting
                int r = (int) (Math.random() * 9);
                buttons[r].setText("X");
                buttons[r].setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
            }
            for (int i = 0; i < buttons.length; i++) {
                Button temp = buttons[i];
                temp.setOnAction(event -> {
                    if (temp.getText().equals("")) {//if the clicked button is not already chosen
                        if (Won(buttons) == 'n') {//and nobody has won yet

                            temp.setStyle("-fx-text-fill:  #f8d320; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
                            temp.setText(player == 'x' ? "X" : "O"); //assign the clicked button with X or O (depends on what's character is the player)

                            /////////////////////////////////////////////////////////Main Block
                            if (Won(buttons) != 'n') {//somebody won
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setContentText("Player " + Won(buttons) + " Wins!");
                                alert.showAndWait();
                            } else {


                                ///////////////////////////////////////////////
                                if (!boardIsFull()) {
                                    System.out.println("....");

                                    int randomMove = (int) (Math.random() * 9);

                                    while (getBoard()[randomMove] != '.') {
                                        randomMove = (int) (Math.random() * 9);
                                    }
//                                    ComputerPlay.MODE = "Expert";
//                                    int l = ComputerPlay.MakeMove(getBoard());
                                    int l = randomMove;
                                    System.out.println(l);// l is the number of the block that pc has chosen

//                                    buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
                                    buttons[l].setText(pcSymbol + "");
                                    buttons[l].setStyle("-fx-text-fill: White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");

                                    if (Won(buttons) != 'n') {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setContentText("Player " + Won(buttons) + " Wins!");
                                        alert.showAndWait();
                                    }
                                }
                                ///////////////////////////////////////////////
                            }
                            /////////////////////////////////////////////////////////Main Block

                        }
                    }
                });
            }
        } else if (X_O.equals("O")) {
            System.out.println("O Starts");

            try {
                Main.ShowBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }

            char pcSymbol;
            if (player == 'x') {
                pcSymbol = 'O';
            } else {
                pcSymbol = 'X';
            }

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText("");
            }
            //two cases
            //player    = o --> player  is the one who starts
            //pc        = o --> pc      is the one who starts

            if (player == 'o') {//if player is starting

            } else if (player == 'x') {//if pc is starting
                int r = (int) (Math.random() * 9);
                buttons[r].setText("O");
                buttons[r].setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
            }

            for (int i = 0; i < buttons.length; i++) {
                Button temp = buttons[i];
                temp.setOnAction(event -> {
                    if (temp.getText().equals("")) {//if the clicked button is not already chosen
                        if (Won(buttons) == 'n') {//and nobody has won yet

                            temp.setStyle("-fx-text-fill:  #f8d320; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
                            temp.setText(player == 'x' ? "X" : "O"); //assign the clicked button with X or O (depends on what's character is the player)

                            /////////////////////////////////////////////////////////Main Block
                            if (Won(buttons) != 'n') {//somebody won
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setContentText("Player " + Won(buttons) + " Wins!");
                                alert.showAndWait();
                            } else {


                                ///////////////////////////////////////////////
                                if (!boardIsFull()) {
                                    System.out.println("....");

                                    int randomMove = (int) (Math.random() * 9);

                                    while (getBoard()[randomMove] != '.') {
                                        randomMove = (int) (Math.random() * 9);
                                    }
//                                    ComputerPlay.MODE = "Expert";
//                                    int l = ComputerPlay.MakeMove(getBoard());
                                    int l = randomMove;
                                    System.out.println(l);// l is the number of the block that pc has chosen

//                                    buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
                                    buttons[l].setText(pcSymbol + "");
                                    buttons[l].setStyle("-fx-text-fill: White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");

                                    if (Won(buttons) != 'n') {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setContentText("Player " + Won(buttons) + " Wins!");
                                        alert.showAndWait();
                                    }
                                }
                                ///////////////////////////////////////////////
                            }
                            /////////////////////////////////////////////////////////Main Block

                        }
                    }
                });
            }

        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    private void makeComputerMove(GridPane grid) {
//        aiGame.Move bestMove;
//        do {
//            bestMove = findBestMove(board, AiSymbol); // Assuming 'board' is your game board
//            if (bestMove.row != -1 && bestMove.column != -1) {
//                // Assuming 'gridPane' is your GridPane where you have buttons
//                Button button = (Button) gridPane.getChildren().get(bestMove.row * SIZE + bestMove.column);
//                if (button.getText().isEmpty()) {
//                    button.setText(String.valueOf(AiSymbol));
//                    board[bestMove.row][bestMove.column] = AiSymbol; // Update the game board
//                }
//
//                checkWin(bestMove.row, bestMove.column, grid, AiSymbol);
//            } else {
//                System.out.println("No valid moves left.");
//            }
//        } while (bestMove.row != -1 && bestMove.column != -1);
//    }
//
//    private aiGame.Move findBestMove(char[][] board, char symbol) {
//        int bestScore = (symbol == AiSymbol) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        aiGame.Move bestMove = new aiGame.Move();
//        bestMove.row = -1;
//        bestMove.column = -1;
//
//        // Create a list of available moves
//        ArrayList<aiGame.Move> availableMoves = new ArrayList<>();
//
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                if (board[i][j] == '\u0000') {
//                    availableMoves.add(new aiGame.Move(i, j));
//                }
//            }
//        }
//
//        // Shuffle the available moves randomly
//        Collections.shuffle(availableMoves);
//
//        // Try each available move and select the best one
//        for (aiGame.Move move : availableMoves) {
//            int i = move.row;
//            int j = move.column;
//
//            board[i][j] = symbol;
//            int score = minimax(board, 0, false);
//            board[i][j] = '\u0000';
//
//            if ((symbol == AiSymbol && score > bestScore) || (symbol == playerSymbol && score < bestScore)) {
//                bestScore = score;
//                bestMove.row = i;
//                bestMove.column = j;
//            }
//        }
//
//        return bestMove;
//    }
//
//    private void checkWin(int row, int col, GridPane grid, char symbol) {
//
//        if (checkLine(0, col, 1, 0, symbol) || checkLine(row, 0, 0, 1, symbol)
//                || (row == col && checkLine(0, 0, 1, 1, symbol))
//                || (row + col == 2 && checkLine(0, 2, 1, -1, symbol))) {
//            gameOver = true;
//            winningSymbol = symbol; // Store the winning symbol
//
//            showWinnerAlert();
//            currentRound++;
////			System.out.println("\n Round: " + currentRound + "\n");
//            resetGameBoard(grid);
//            return;
//        } else if (isBoardFull()) {
//            gameOver = true;
//            showDrawAlert();
//            currentRound++;
//            resetGameBoard(grid);
//            return;
//        }
//
////        if (currentRound <= totalRounds) {
//////			System.out.println("\n Round: " + currentRound + "\n");
////
////            currentRoundLabel.setText("Round: " + currentRound + " / " + totalRounds);
////        } else {// rounds done
//////			System.out.println("rounds done");
////            pane1.getChildren().removeAll(infoBox, gridPane);
////            VBox resBox = new VBox();
////            String winner;
////
////            Label result = new Label();
////
////            if (Xscore > Oscore) {
////                if (ch11.isSelected()) {
//////				winner="X";
////                    winner = ch11.getText();
////                    result.setText("PLAYER " + winner + " WON!");
////
////                } else if (ch22.isSelected()) {
////                    winner = ch11.getText();
////                    result.setText("PLAYER " + winner + " WON!");
////
////                }
////            } else if (Oscore > Xscore) {
//////				winner = "O";
////                if (ch11.isSelected()) {
////                    winner = ch22.getText();
////                    result.setText("PLAYER " + winner + " WON!");
////
////                } else if (ch22.isSelected()) {
////                    winner = ch11.getText();
////                    result.setText("PLAYER " + winner + " WON!");
////
////                }
////
////            } else {
////                result.setText("It's A Draw!");
////
////            }
////            /// ---------------------------------------
////            result.setTextFill(Color.GREEN);
////            if (ch11.isSelected()) {
////                Xplayer = ch11.getText();
////                Oplayer = ch22.getText();
////
////            } else if (ch22.isSelected()) {
////                Xplayer = ch22.getText();
////                Oplayer = ch11.getText();
////
////            }
////            Label score = new Label(Xplayer + " score : " + Xscore + "\n" + Oplayer + " score : " + Oscore);
////            score.setTextFill(Color.DARKSLATEBLUE);
////
////            result.setFont(fontBtn);
////            score.setFont(fontBtn);
////
////            Button rePlay = new Button("Play Again");
////            rePlay.setStyle(
////                    "-fx-background-color: white; -fx-background-radius: 20;-fx-text-fill:#BB688D; -fx-border-color:#BB688D ; -fx-border-width: 2; -fx-border-radius: 13; -fx-padding: 5; -fx-background-insets: 0;");
////
////            rePlay.setOnAction(c -> {
////                Xscore = 0;
////                Oscore = 0;
////                currentRound = 1;
////                totalRounds = 0;
////                Image image = new Image("C:\\Users\\User\\Desktop\\bc.PNG");
////                ImageView imageView = new ImageView(image);
////            });
////
////            resBox.getChildren().addAll(result, score, rePlay);
////            resBox.setAlignment(Pos.CENTER);
////            resBox.setSpacing(20);
////            pane1.setCenter(resBox);
////
////        }
//    }
//    private boolean isBoardFull() {
//        for (int row = 0; row < SIZE; row++) {
//            for (int col = 0; col < SIZE; col++) {
//                if (board[row][col] == '\u0000') {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//    private boolean checkLine(int row, int col, int rowInc, int colInc, char symbol) {
//        for (int i = 0; i < 3; i++) {
//            if (board[row][col] != symbol) {
//                return false;
//            }
//            row += rowInc;
//            col += colInc;
//        }
//        return true;
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean boardIsFull() {//was GameNotFinished

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText().equals("X") || buttons[i].getText().equals("O")) {
                return false;
            }

        }
        return true;
    }


    private char Won(Button[] buttons) {
        //checking if somebody won                              {'x' or 'o'}
        //or the game is over with nobody winning (tie(Draw))   {'t'}
        //or the game is not over yet (null(no winner yet))     {'n'}


            /*
            |  0,0  |  0,1  |  0,2  |
            |-------|-------|-------|
            |  1,0  |  1,1  |  1,2  |
            |-------|-------|-------|
            |  2,0  |  2,1  |  2,2  |
            */

        for (int j = 0; j < winCases.length; j++) {
            int a = winCases[j][0];
            int b = winCases[j][1];
            int c = winCases[j][2];
            if (buttons[a].getText().equals("") || buttons[b].getText().equals("") || buttons[c].getText().equals(""))
                continue;
            if (!buttons[a].getText().equals("") && buttons[a].getText().charAt(0) == buttons[b].getText().charAt(0) && buttons[a].getText().charAt(0) == buttons[c].getText().charAt(0)) {
                return buttons[a].getText().charAt(0);
            }
        }
        //n(still no winner) or x or o
        char result;
        if (boardIsFull()) {
            result = 't';//tie    (Draw)
        } else {
            result = 'n';//null    (no winner yet)
        }
        return result;
    }


    private char[] getBoard() {
        counter++;
//        char[] arr = new char[9];
//        arr = new char[9];
        System.out.println("method called for the #"+counter+" time");
        for (int i = 0; i < arr.length; i++) {
            if (buttons[i].getText().equals("")) {
                arr[i] = '.';
            } else {
                arr[i] = buttons[i].getText().toLowerCase(Locale.ROOT).charAt(0);
            }
            System.out.println(arr[i]);
        }

        return arr;
    }


    public String showChoosePlayerDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Player");
        alert.setHeaderText("Who should start?");
        alert.setContentText("Choose your option.");
        ButtonType buttonTypeX = null;
        ButtonType buttonTypeO = null;
        if (player == 'x') {
            buttonTypeX = new ButtonType("X (You)");
            buttonTypeO = new ButtonType("O");
        } else if (player == 'o') {
            buttonTypeX = new ButtonType("X");
            buttonTypeO = new ButtonType("O (You)");
        }


        alert.getButtonTypes().setAll(buttonTypeX, buttonTypeO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonTypeX) {
                return "X";
            } else if (result.get() == buttonTypeO) {
                return "O";
            }
        }

        return null; // or a default value
    }
}

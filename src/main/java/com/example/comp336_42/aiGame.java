package com.example.comp336_42;

import com.sun.javafx.menu.MenuItemBase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Collections;

public class aiGame extends BorderPane {
    private static final int SIZE = 3; // Size of the Tic Tac Toe board
    private final char[][] board = new char[SIZE][SIZE];
    private char currentPlayer = 'X'; // Player 'X' starts
    private final GridPane gridPane = new GridPane();
    int row, col;

    Font fontBtn = Font.font("Basketball Old Face", FontWeight.BOLD, 19); // Font family, style (bold), size
    String name1;
    RadioButton ch22, ch11;

    String Xplayer = "";
    String Oplayer = "";
    VBox infoBox;
    private char winningSymbol = '\u0000';
    static char playerSymbol = ' ';
    static char AiSymbol = ' ';
    boolean aiFirst = false;
    boolean playerFirst = true;
    private boolean gameOver = false;

    private final Label currentRoundLabel = new Label();
    private Label xScoreLabel;
    private Label oScoreLabel;
    private int totalRounds = 0;
    private int currentRound = 1;
    String currentPlayerName = "";

    static int Xscore = 0;
    static int Oscore = 0;
    BorderPane pane1 = new BorderPane();

    // ----------------------------------------------------
    public aiGame() {
        MenuItemBase advanced = null;
        advanced.setOnAction(a -> {

            Button save = new Button("Save");
            Button start = new Button("Start Game");

            // -------------------------------------------------
            save.setOnAction(c -> {

                java.awt.Label note = null;
                java.awt.Label soundstart = null;
                java.awt.Label name = null;
                if (!name.getText().isEmpty() && !soundstart.getText().isEmpty()) {
                    name1 = name.getText();

                    Label q1 = new Label("Who Will Start First ?  ");
                    RadioButton ch1 = new RadioButton(name1);
                    RadioButton ch2 = new RadioButton("Computer");

                    Label q2 = new Label("Who Will Play With X  ?");
                    ch11 = new RadioButton(name1);
                    ch22 = new RadioButton("Computer");

                    ch1.setOnAction(event -> {
                        if (ch1.isSelected()) {
                            ch2.setSelected(false); // Unselect ch2 if ch1 is selected
                            playerFirst = true;
                            aiFirst = false;
                            boolean roundsFilled = !soundstart.getText().isEmpty();
                            boolean checkboxesSelected = (ch11.isSelected() || ch22.isSelected());
                            start.setDisable(!roundsFilled || !checkboxesSelected);
                        } else {
                            start.setDisable(true); // Disable Start button if ch1 is deselected
                        }
                    });

                    ch2.setOnAction(event -> {
                        if (ch2.isSelected()) {
                            ch1.setSelected(false); // Unselect ch1 if ch2 is selected
                            playerFirst = false;
                            aiFirst = true;
                            boolean roundsFilled = !soundstart.getText().isEmpty();
                            boolean checkboxesSelected = (ch11.isSelected() || ch22.isSelected());
                            start.setDisable(!roundsFilled || !checkboxesSelected);
                        } else {
                            start.setDisable(true); // Disable Start button if ch2 is deselected
                        }
                    });

                    // ---------------------------------------------------
                } else { // txt empty
                    note.setText("Please Fill All Data!");
                }
            });// save end
            // ---------------------------------------------------------------------
            start.setOnAction(s -> {

                pane1.setCenter(gridPane);
                gridPane.setAlignment(Pos.CENTER);

                if (ch11.isSelected()) {
                    Xplayer = ch11.getText();
                    Oplayer = ch22.getText();
                    System.out.println(Xplayer + "  xplayer");

                } else if (ch22.isSelected()) {
                    Xplayer = ch22.getText();
                    Oplayer = ch11.getText();

                }

                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        Button button = createButton(row, col, gridPane);
                        gridPane.add(button, col, row);
                    }
                }

                // ---------------------------------------
                if (aiFirst) {
                    makeComputerMove(gridPane);

                } else if (playerFirst) {
                    // Player makes the first move
                    Button button = createButton(row, col, gridPane);
                    gridPane.add(button, col, row);
                }
                // ---------------------------show results----------------------------------

                currentRoundLabel.setText("Round: " + currentRound + "/" + totalRounds);

                xScoreLabel = new Label("X player [" + Xplayer + "] Score: 0");
                oScoreLabel = new Label("O player [" + Oplayer + "] Score: 0");

                infoBox = new VBox(currentRoundLabel, xScoreLabel, oScoreLabel);
                infoBox.setAlignment(Pos.CENTER);
                pane1.setTop(infoBox);
            }); // start ends

        }); // level ends

    }// constructor ends

    private Button createButton(int row, int col, GridPane grid) {
        Button button = new Button();
        button.setMinSize(100, 100);
        button.setStyle("-fx-background-color: #D6D6F0;-fx-text-fill:navy; -fx-border-color:#BB688D ;");

        button.setOnAction(event -> {
            if (board[row][col] == '\u0000') { // Check if the position is empty
                board[row][col] = playerSymbol; // player turn
                button.setText(String.valueOf(playerSymbol));
//				System.out.println("player symbol: " + playerSymbol);

                // Check for a win or draw
                checkWin(row, col, grid, playerSymbol);

                makeComputerMove(gridPane);
            }
        });

        return button;

    }

    // --------------------------------------------------------------------------------------------

    private void checkWin(int row, int col, GridPane grid, char symbol) {

        if (checkLine(0, col, 1, 0, symbol) || checkLine(row, 0, 0, 1, symbol)
                || (row == col && checkLine(0, 0, 1, 1, symbol))
                || (row + col == 2 && checkLine(0, 2, 1, -1, symbol))) {
            gameOver = true;
            winningSymbol = symbol; // Store the winning symbol

            showWinnerAlert();
            currentRound++;
//			System.out.println("\n Round: " + currentRound + "\n");
            resetGameBoard(grid);
            return;
        } else if (isBoardFull()) {
            gameOver = true;
            showDrawAlert();
            currentRound++;
            resetGameBoard(grid);
            return;
        }

        if (currentRound <= totalRounds) {
//			System.out.println("\n Round: " + currentRound + "\n");

            currentRoundLabel.setText("Round: " + currentRound + " / " + totalRounds);
        } else {// rounds done
//			System.out.println("rounds done");
            pane1.getChildren().removeAll(infoBox, gridPane);
            VBox resBox = new VBox();
            String winner;

            Label result = new Label();

            if (Xscore > Oscore) {
                if (ch11.isSelected()) {
//				winner="X";
                    winner = ch11.getText();
                    result.setText("PLAYER " + winner + " WON!");

                } else if (ch22.isSelected()) {
                    winner = ch11.getText();
                    result.setText("PLAYER " + winner + " WON!");

                }
            } else if (Oscore > Xscore) {
//				winner = "O";
                if (ch11.isSelected()) {
                    winner = ch22.getText();
                    result.setText("PLAYER " + winner + " WON!");

                } else if (ch22.isSelected()) {
                    winner = ch11.getText();
                    result.setText("PLAYER " + winner + " WON!");

                }

            } else {
                result.setText("It's A Draw!");

            }
            /// ---------------------------------------
            result.setTextFill(Color.GREEN);
            if (ch11.isSelected()) {
                Xplayer = ch11.getText();
                Oplayer = ch22.getText();

            } else if (ch22.isSelected()) {
                Xplayer = ch22.getText();
                Oplayer = ch11.getText();

            }
            Label score = new Label(Xplayer + " score : " + Xscore + "\n" + Oplayer + " score : " + Oscore);
            score.setTextFill(Color.DARKSLATEBLUE);

            result.setFont(fontBtn);
            score.setFont(fontBtn);

            Button rePlay = new Button("Play Again");
            rePlay.setStyle(
                    "-fx-background-color: white; -fx-background-radius: 20;-fx-text-fill:#BB688D; -fx-border-color:#BB688D ; -fx-border-width: 2; -fx-border-radius: 13; -fx-padding: 5; -fx-background-insets: 0;");

            rePlay.setOnAction(c -> {
                Xscore = 0;
                Oscore = 0;
                currentRound = 1;
                totalRounds = 0;
                Image image = new Image("C:\\Users\\User\\Desktop\\bc.PNG");
                ImageView imageView = new ImageView(image);
            });

            resBox.getChildren().addAll(result, score, rePlay);
            resBox.setAlignment(Pos.CENTER);
            resBox.setSpacing(20);
            pane1.setCenter(resBox);

        }
    }

    private boolean checkLine(int row, int col, int rowInc, int colInc, char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[row][col] != symbol) {
                return false;
            }
            row += rowInc;
            col += colInc;
        }
        return true;
    }
    /// -------------------------------------------------------------

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }
    /// -------------------------------------------------------------

    private void showWinnerAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Player " + winningSymbol + " wins!");

        if (winningSymbol == 'X') {

            Xscore++;

        } else if (winningSymbol == 'O') {
            Oscore++;
        }

//-----------------------------------------------
        if (ch11.isSelected()) {
            Xplayer = ch11.getText();
            Oplayer = ch22.getText();

        } else if (ch22.isSelected()) {
            Xplayer = ch22.getText();
            Oplayer = ch11.getText();

        }

        xScoreLabel.setText("X player [" + Xplayer + "] Score: " + Xscore);
        oScoreLabel.setText("O player [" + Oplayer + "] Score: " + Oscore);

        alert.showAndWait();

    }
    // ----------------------------------------------------------------------------

    private void showDrawAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("It's a draw!");
        alert.showAndWait();
    }
    /// -------------------------------------------------------------

    // Reset the game board for the next round
    private void resetGameBoard(GridPane grid) {
        gameOver = false; // Reset gameOver to false

        // Iterate through the GridPane and clear the text on each Button
        for (Node node : grid.getChildren()) {
            if (node instanceof Button button) {
                button.setText(""); // Clear Xs and Os
            }
        }

        // Clear the game board (2D char array)
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '\u0000'; // Clear the game board
            }
        }
    }

    //---------------------------------------------------------------------------
    private int minimax(char[][] board, int depth, boolean isMaximizing) {

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == '\u0000') {
                        board[i][j] = AiSymbol;
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = '\u0000';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == '\u0000') {
                        board[i][j] = playerSymbol;
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = '\u0000';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    private Move findBestMove(char[][] board, char symbol) {
        int bestScore = (symbol == AiSymbol) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.column = -1;

        // Create a list of available moves
        ArrayList<Move> availableMoves = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '\u0000') {
                    availableMoves.add(new Move(i, j));
                }
            }
        }

        // Shuffle the available moves randomly
        Collections.shuffle(availableMoves);

        // Try each available move and select the best one
        for (Move move : availableMoves) {
            int i = move.row;
            int j = move.column;

            board[i][j] = symbol;
            int score = minimax(board, 0, false);
            board[i][j] = '\u0000';

            if ((symbol == AiSymbol && score > bestScore) || (symbol == playerSymbol && score < bestScore)) {
                bestScore = score;
                bestMove.row = i;
                bestMove.column = j;
            }
        }

        return bestMove;
    }

    private void makeComputerMove(GridPane grid) {
        Move bestMove;
        do {
            bestMove = findBestMove(board, AiSymbol); // Assuming 'board' is your game board
            if (bestMove.row != -1 && bestMove.column != -1) {
                // Assuming 'gridPane' is your GridPane where you have buttons
                Button button = (Button) gridPane.getChildren().get(bestMove.row * SIZE + bestMove.column);
                if (button.getText().isEmpty()) {
                    button.setText(String.valueOf(AiSymbol));
                    board[bestMove.row][bestMove.column] = AiSymbol; // Update the game board
                }

                checkWin(bestMove.row, bestMove.column, grid, AiSymbol);
            } else {
                System.out.println("No valid moves left.");
            }
        } while (bestMove.row != -1 && bestMove.column != -1);
    }

    static class Move {
        int row;
        int column;

        public Move(int row, int column) {
            this.row = row;
            this.column = column;

        }

        public Move() {
            super();
        }


    }

}
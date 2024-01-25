package com.example.comp336_42;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static com.example.comp336_42.MiniMaxAlgo.bestMoves;

public class AgainstPcCont {


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

    static int numOf_rounds = 0;
    static int round = 0;

    static int xScore;
    static int oScore;

    static TextField txtFieldRoundsCounter;
    @FXML
    static TextArea txtAreaRoundsCounter;



///////////////////////////////////////////////////////////

    @FXML
    private RadioButton xSelected = new RadioButton();

    @FXML
    private RadioButton oSelected = new RadioButton();

    @FXML
    private ToggleGroup userChoiceToggleGroup;

///////////////////////////////////////////////////////////

    @FXML
    private Button btnEasy = new Button();

    @FXML
    private Button btnMedium = new Button();

    @FXML
    private Button btnAdvanced = new Button();

    ///////////////////////////////////////////////////////////////////////////////////////////////
    static char user = 'x';
    static char pc = 'o';
    static char[] arrCells;

    @FXML
    public void initialize() {

        arrCells = new char[9];
        for (int i = 0; i < arrCells.length; i++) {
            arrCells[i] = '.';
        }
        txtAreaRoundsCounter=new TextArea("1/5");
        btnEasy.setDisable(true);
        btnMedium.setDisable(true);
        btnAdvanced.setDisable(true);

        buttons[0] = button00;
        buttons[1] = button01;
        buttons[2] = button02;

        buttons[3] = button10;
        buttons[4] = button11;
        buttons[5] = button12;

        buttons[6] = button20;
        buttons[7] = button21;
        buttons[8] = button22;

        userChoiceToggleGroup = new ToggleGroup();
        xSelected.setToggleGroup(userChoiceToggleGroup);
        oSelected.setToggleGroup(userChoiceToggleGroup);

        userChoiceToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if (userChoiceToggleGroup.getSelectedToggle() != null) {

                    RadioButton selected = (RadioButton) userChoiceToggleGroup.getSelectedToggle();
                    if ("Play As X".equals(selected.getText())) {
                        user = 'x';
                        pc = 'o';
                    } else if ("Play As O".equals(selected.getText())) {
                        user = 'o';
                        pc = 'x';
                    }
                    btnEasy.setDisable(false);
                    btnMedium.setDisable(false);
                    btnAdvanced.setDisable(false);
                    ////////////////////////////////////////////////////////////////////////////////
                    btnEasy.setOnAction(event -> {

                        ////////////////////////////////
                        try {
                            Main.ShowBoard();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ////////////////////////////////

                        String choice = chooseWhoStart_popup();
                        askNumberOfRounds();
                        if (choice.equals("X")) {//x is starting
                            randomMode('x');
                        } else if (choice.equals("O")) {//o is starting
                            randomMode('o');
                        }
                    });

                    ////////////////////////////////////////////////////////////////////////////////
                    btnAdvanced.setOnAction(event -> {
                        try {
                            Main.ShowBoard();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        String choice = chooseWhoStart_popup();
                        askNumberOfRounds();
                        if (choice.equals("X")) {//x is starting
                            advancedMode('x');
                        } else if (choice.equals("O")) {//o is starting
                            advancedMode('o');
                        }

                    });

                    ////////////////////////////////////////////////////////////////////////////////
                }
            }
        });
    }

    public void randomMode(char X_O) {

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            arrCells[i] = '.';
        }

        if (X_O == 'x') {//X Starts
            System.out.println("X Starts");

            if (pc == 'x') {//if pc is starting
                int r = (int) (Math.random() * 9);
                buttons[r].setText("X");
                buttons[r].setStyle("-fx-font-size: 45;");
                arrCells[r] = 'x';

                for (int i = 0; i < buttons.length; i++) {
                    int currI = i;
                    Button currBtn = buttons[i];
                    currBtn.setOnAction(event -> {
                        if (currBtn.getText().equals("")) {//if the clicked button is not already chosen

                            if (checkStatus() == 'n') {//and nobody has won yet
                                System.out.println(checkStatus());
                                currBtn.setStyle("-fx-font-size: 45;");
                                currBtn.setText(user == 'x' ? "X" : "O"); //assign the clicked button with X or O (depends on what's character is the player)
                                arrCells[currI] = (user == 'x' ? 'x' : 'o');
                                /////////////////////////////////////////////////////////Main Block
                                ////////////////////////////////////////////////
                                char status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}


                                if (status != 'n') {
                                    try {
                                        popup(status);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else {//status= 'n'   --> game is still going
                                    if (!boardIsFull()) {
                                        System.out.println("....");

                                        int randomMove = (int) (Math.random() * 9);

                                        while (arrCells[randomMove] != '.') {
                                            randomMove = (int) (Math.random() * 9);
                                        }
                                        int l = randomMove;
                                        System.out.println(l);// l is the number of the block that pc has chosen

                                        buttons[l].setText(Character.toUpperCase(pc) + "");
                                        buttons[l].setStyle("-fx-font-size: 45;");
                                        arrCells[l] = (user == 'x' ? 'o' : 'x');
                                        status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}
                                        if (status != 'n') {
                                            try {
                                                popup(status);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                    ///////////////////////////////////////////////
                                }
                                /////////////////////////////////////////////////////////Main Block

                            }
                        }
                    });
                }

            } else if (user == 'x') {//so the player wants to start
                for (int i = 0; i < buttons.length; i++) {

                    int currI = i;
                    Button currBtn = buttons[i];
                    currBtn.setOnAction(event -> {
                        if (currBtn.getText().equals("")) {//if the clicked button is not already chosen


                            if (checkStatus() == 'n') {//and nobody has won yet
                                currBtn.setStyle("-fx-font-size: 45;");
                                currBtn.setText(user == 'x' ? "X" : "O"); //assign the clicked button with X or O (depends on what's character is the player)
                                arrCells[currI] = (user == 'x' ? 'x' : 'o');
                                /////////////////////////////////////////////////////////Main Block

                                char status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}


                                if (status != 'n') {
                                    try {
                                        popup(status);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else {//status= 'n'   --> game is still going
                                    if (!boardIsFull()) {
                                        System.out.println("....");

                                        int randomMove = (int) (Math.random() * 9);

                                        while (arrCells[randomMove] != '.') {
                                            randomMove = (int) (Math.random() * 9);
                                        }
//                                    ComputerPlay.MODE = "Expert";
//                                    int l = ComputerPlay.MakeMove(getBoard());
                                        int l = randomMove;
                                        System.out.println(l);// l is the number of the block that pc has chosen

//                                    buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
                                        buttons[l].setText(Character.toUpperCase(pc) + "");
                                        buttons[l].setStyle("-fx-font-size: 45;");
                                        arrCells[l] = (user == 'x' ? 'o' : 'x');
                                        status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}
                                        if (status != 'n') {
                                            try {
                                                popup(status);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
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

        } else if (X_O == 'o') {
            System.out.println("O Starts");

            //two cases
            //player    = o --> player  is the one who starts
            //pc        = o --> pc      is the one who starts

            if (user == 'o') {//if player is starting

            } else if (user == 'x') {//if pc is starting
                int r = (int) (Math.random() * 9);
                buttons[r].setText("O");
                buttons[r].setStyle("-fx-font-size: 45;");
                arrCells[r] = 'o';
            }

            for (int i = 0; i < buttons.length; i++) {
                int currI = i;
                Button currBtn = buttons[i];
                currBtn.setOnAction(event -> {
                    if (currBtn.getText().equals("")) {//if the clicked button is not already chosen
                        if (checkStatus() == 'n') {//and nobody has won yet

                            currBtn.setStyle("-fx-font-size: 45;");
                            currBtn.setText(user == 'x' ? "X" : "O"); //assign the clicked button with X or O (depends on what's character is the player)
                            arrCells[currI] = (user == 'x' ? 'x' : 'o');
                            /////////////////////////////////////////////////////////Main Block
                            ////////////////////////////////////////////////
                            char status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}


                            if (status != 'n') {
                                try {
                                    popup(status);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {//status= 'n'   --> game is still going
                                if (!boardIsFull()) {
                                    System.out.println("....");

                                    int randomMove = (int) (Math.random() * 9);

                                    while (arrCells[randomMove] != '.') {
                                        randomMove = (int) (Math.random() * 9);
                                    }
//                                    ComputerPlay.MODE = "Expert";
//                                    int l = ComputerPlay.MakeMove(getBoard());
                                    int l = randomMove;
                                    System.out.println(l);// l is the number of the block that pc has chosen

//                                    buttons[l].setText((player == 'x' ? 'O' : 'X') + "");
                                    buttons[l].setText(Character.toUpperCase(pc) + "");
                                    buttons[l].setStyle("-fx-font-size: 45;");
                                    arrCells[l] = (user == 'x' ? 'o' : 'x');

                                    status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}
                                    if (status != 'n') {
                                        try {
                                            popup(status);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
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


    public void advancedMode(char X_O) {
        //case  1   (X_O=user)-->  user is starting
        //case  2   (X_O=pc)    -->  pc is starting

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            arrCells[i] = '.';
        }

        if (X_O == user) {//user is starting
            System.out.println("user is starting");

            for (int i = 0; i < buttons.length; i++) {
//                System.out.println("reached there");
                Button currBtn = buttons[i];
                int currI = i;
                currBtn.setOnAction(event -> {

                    if (currBtn.getText().equals("")) {

                        if (checkStatus() == 'n') {

                            currBtn.setStyle("-fx-font-size: 45;");
                            currBtn.setText(user == 'x' ? "X" : "O");
                            arrCells[currI] = (user == 'x' ? 'x' : 'o');

                            ////////////////////////////////////////////////
                            char status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}


                            if (status != 'n') {
                                try {
                                    popup(status);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {//status= 'n'   --> game is still going
                                if (!boardIsFull()) {
                                    int l = MiniMaxAlgo.makeAMove(arrCells);
                                    System.out.println(l);
                                    buttons[l].setText((user == 'x' ? 'O' : 'X') + "");
                                    buttons[l].setStyle("-fx-font-size: 45;");
                                    arrCells[l] = (user == 'x' ? 'o' : 'x');
                                    status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}
                                    if (status != 'n') {
                                        try {
                                            popup(status);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }


        } else if (X_O == pc) {//PC is starting
            System.out.println("PC is starting");

            int firstMove = MiniMaxAlgo.makeAMove(arrCells);
            showBestMoves();
            buttons[firstMove].setText(Character.toUpperCase(pc) + "");
            buttons[firstMove].setStyle("-fx-font-size: 45;");
            arrCells[firstMove] = pc;

            //noinspection DuplicatedCode
            for (int i = 0; i < buttons.length; i++) {

                Button currBtn = buttons[i];
                int currI = i;
                currBtn.setOnAction(event -> {

                    if (currBtn.getText().equals("")) {
                        if (checkStatus() == 'n') {
                            ////////////////////////////////////////////////
                            currBtn.setStyle("-fx-font-size: 45;");
                            currBtn.setText(Character.toUpperCase(user) + "");
                            arrCells[currI] = user;
                            ////////////////////////////////////////////////
                            char status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}


                            if (status != 'n') {
                                try {
                                    popup(status);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {//status= 'n'   --> game is still going
                                if (!boardIsFull()) {
                                    int l = MiniMaxAlgo.makeAMove(arrCells);
                                    showBestMoves();
                                    System.out.println(l);
                                    buttons[l].setText(Character.toUpperCase(pc) + "");
                                    buttons[l].setStyle("-fx-font-size: 45;");
                                    arrCells[l] = pc;

                                    status = checkStatus();//Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}
                                    if (status != 'n') {
                                        try {
                                            popup(status);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                            ////////////////////////////////////////////////
                        }
                    }
                });
            }


        }


    }

    private void showBestMoves() {
        for (int i = 0; i < buttons.length; i++) {
            if(buttons[i].getText().equals("")){
                buttons[i].setText(bestMoves[i]+"");
                buttons[i].setStyle("-fx-font-size: 45;");

            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("do you want to continue?");
        ButtonType btnContinue =new ButtonType("Continue...");

        alert.getButtonTypes().setAll(btnContinue);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == btnContinue) {
                for (int i = 0; i < buttons.length; i++) {
                    if(buttons[i].getText().equals("X")||buttons[i].getText().equals("O")){
                        continue;
                    }else {
                        buttons[i].setText("");
                    }
                }
            }

        }
    }

    private void popup(char status) throws IOException {

            if (status == 't') {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("The Round Is Over!");
                alert.setContentText("Draw !!");


                ButtonType btnNextRound = new ButtonType("Start Next Round!");
                ButtonType btnEnd = new ButtonType("End Game :( And Return To Home Screen");
                alert.getButtonTypes().setAll(btnNextRound, btnEnd);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == btnNextRound){
                        round++;
                        txtAreaRoundsCounter.setText(round + "/" + numOf_rounds);
                        System.out.println("btnNextRound selected");
                    } else {//btnEnd
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                        Parent root = loader.load();
                        Main.primaryStage = new Stage();
                        Main.primaryStage.setScene(new Scene(root));
                        Main.primaryStage.show();
//                        System.out.println("btnEnd selected");
                    }
                }
                alert.showAndWait();
            } else {//somebody won the round!
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                                    alert.setContentText("Player " + status + " Wins!");


                if(status=='x'){
                    xScore++;
                }else if (status=='o'){
                    oScore++;
                }
                String result=endOfRoundCheck();//round++;

                if(result.equals("end")){
                    char op='X';
                    if(status=='x'){
                        op='O';
                    }
                    alert.setContentText("Player " + Character.toUpperCase(status)  + " is the winner of this round!\n " +
                            "And it's not possible for " + op  + " player to win.");
                    ButtonType btnEnd = new ButtonType("End Game :( And Return To Home Screen");

                    alert.getButtonTypes().setAll(btnEnd);
                    alert.showAndWait();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                    Parent root = loader.load();
                    Main.primaryStage.close();
                    Main.primaryStage = new Stage();
                    Main.primaryStage.setScene(new Scene(root));
                    Main.primaryStage.show();


                } else if (result.equals("cont")) {
                    alert.setContentText("Player " + Character.toUpperCase(status)  + " is the winner of this round!");

                    ButtonType btnNextRound = new ButtonType("Start Next Round!");
                    ButtonType btnEnd = new ButtonType("End Game :( And Return To Home Screen");
                    alert.getButtonTypes().setAll(btnNextRound, btnEnd);

                    Optional<ButtonType> result2 = alert.showAndWait();
                    if (result2.isPresent()) {
                        if (result2.get() == btnNextRound){
                            round++;
                            txtAreaRoundsCounter.setText(round + "/" + numOf_rounds);
                            System.out.println("btnNextRound selected");
                            Main.ShowBoard();
                        } else {//btnEnd
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                            Parent root = loader.load();
                            Main.primaryStage.close();
                            Main.primaryStage = new Stage();
                            Main.primaryStage.setScene(new Scene(root));
                            Main.primaryStage.show();
//                        System.out.println("btnEnd selected");
                        }
                    }
                    alert.showAndWait();

                }
//                txtAreaRoundsCounter.setText(round + "/" + numOf_rounds);
//                alert.showAndWait();
            }
    }

    public String endOfRoundCheck() {
        round++;
        int roundsRemaining = numOf_rounds - round;
        int scoreDifference = Math.abs(xScore - oScore);

        if (scoreDifference > roundsRemaining) {
            // One player has an insurmountable lead
            System.out.println("Game over. It's not possible for the trailing player to win.");
            return "end";
        } else {
            // The game continues
            System.out.println("Next round. The game is still open.");
            return "cont";

            // Implement logic for proceeding to the next round
        }
    }
    private boolean boardIsFull() {//was GameNotFinished
        boolean elementDetected;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText().equals("")) {
                return false;
            }
        }
        return true;
    }


    private char checkStatus() {
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
            if (arrCells[a] == '.' || arrCells[b] == '.' || arrCells[c] == '.') {
                continue;
            }
            if (arrCells[a] != '.' && arrCells[a] == arrCells[b]
                    && arrCells[a] == arrCells[c]) {
                return arrCells[a];//winner
            }
        }

        //Four cases: {n(still no winner)} or {t(tie(Draw))} or {x} or {o}
        char result;
        if (boardIsFull()) {
            result = 't';//tie    (Draw)
        } else {
            result = 'n';//null    (no winner yet)
        }
        return result;
    }

    public String chooseWhoStart_popup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Player");
        alert.setHeaderText("Who should start?");
//        alert.setContentText("Choose your option.");
        ButtonType buttonTypeX = null;
        ButtonType buttonTypeO = null;
        if (user == 'x') {
            buttonTypeX = new ButtonType("X (You)");
            buttonTypeO = new ButtonType("O");
        } else if (user == 'o') {
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

        return null;
    }
    private void askNumberOfRounds() {
        TextInputDialog dialog = new TextInputDialog("5"); // Default value
        dialog.setTitle("Game Settings");
        dialog.setHeaderText("Number of Rounds");
        dialog.setContentText("Please enter the number of rounds you want to play:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(numberOfRounds -> {
            try {
                int rounds = Integer.parseInt(numberOfRounds);
                numOf_rounds=rounds;
                txtAreaRoundsCounter.setText(round + "/"+numOf_rounds);

                System.out.println("Number of Rounds: " + numOf_rounds);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        });
    }

    public void againstFrind() {
        try {
            Main.ShowBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String choice = chooseWhoStart_popup();
        askNumberOfRounds();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
        }
        for (int i = 0; i < buttons.length; i++) {

            Button temp = buttons[i];
            temp.setOnAction(event -> {
                if (temp.getText().equals("")) {

                    if (user == 'x' && checkStatus() == 'n') {
                        temp.setStyle("-fx-font-size: 45;");
                        temp.setText("X");
                        if (checkStatus() != 'n') {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Player " + checkStatus() + " Wins!");
                            alert.showAndWait();
                        }
                        user = 'o';
                    } else if (user == 'o' && checkStatus() == 'n') {
                        temp.setStyle("-fx-font-size: 45;");
                        temp.setText("O");
                        if (checkStatus() != 'n') {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Player " + checkStatus() + " Wins!");
                            alert.showAndWait();
                        }
                        user = 'x';
                    }
                }
            });

        }
    }

}

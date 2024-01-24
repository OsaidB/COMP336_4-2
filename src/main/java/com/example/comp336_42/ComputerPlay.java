package com.example.comp336_42;

import java.util.Arrays;

import static com.example.comp336_42.AgainstPcCont.player;

public class ComputerPlay {
    public static String MODE = "Easy";
    //    public static char pc = 'o';
    public static char pc = 'o';


    public static int MakeMove(char[] board) {
        System.out.println("GET BOARDDDDDDDDDDDDDDD");
        System.out.println("GET BOARDDDDDDDDDDDDDDD"+ Arrays.toString(board));

        if (MODE.equals("Easy") || MODE.equals("Medium")) {
            int randomMove = (int) (Math.random() * 8);

            while (board[randomMove] != '.') {
                randomMove = (int) (Math.random() * 8);
            }

            return randomMove;
        } else if (MODE.equals("Expert")) {

            if (player == 'x') {
                pc = 'o';
            } else if (player == 'o') {
                pc = 'x';
            }
            int move = getBestMove(board);
            System.out.println("GET BOARDDDDDDDDDDDDDDD");
            System.out.println("GET BOARDDDDDDDDDDDDDDD"+ Arrays.toString(board));

            return move;
        }

        return -130;

    }


    private static int evaluateScore(char[] board) {

        for (int j = 0; j < AgainstPcCont.winCases.length; j++) {
            /*
            |  0,0  |  0,1  |  0,2  |
            |-------|-------|-------|
            |  1,0  |  1,1  |  1,2  |
            |-------|-------|-------|
            |  2,0  |  2,1  |  2,2  |
            */
            int a = AgainstPcCont.winCases[j][0];
            int b = AgainstPcCont.winCases[j][1];
            int c = AgainstPcCont.winCases[j][2];
//            if (board[a] != '.' && board[a] == board[b] && board[a] == board[c]) {
//
//                if (board[a] == cturn)
//                    return 10;
//                else
//                    return -10;
//            }
            if (board[a] == pc
                    && board[b] == pc
                    && board[c] == pc) {

                return 10;

            } else
                return -10;

        }

        return 0;
    }

    private static int getBestMove(char[] board) {
        int bestScore = -999;
        int bestMove = -1;

//        if(player==)


        for (int i = 0; i < board.length; i++) {//getting all possible moves { 6, 7, 9}
            if (board[i] == '.') {// is the spot available?

                board[i] = pc;
//                int score = MiniMax(board, (pc == 'x' ? 'o' : 'x'), 0);

                int score = miniMax(board, player, 0);
                board[i] = '.';
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private static int miniMax(char[] board, char turn, int depth) {
        //turn = not pc (as a starting point)
//        int score = evaluateScore(board);
//
//        if (score == 10) {
//            return score - depth;
//        }
//        if (score == -10) {
//            return score + depth;
//        }
//        if (GameEnds(board)) {
//            return 0;
//        }

//        int best = (turn == pc) ? -1000 : 1000;

//////////////////////////////////////////
        int score = 404;//just a random number to initialize variable
        char status = checkStatus(board);
        if (status != 'n') {
            if (status == pc) {//same as pc --> 1
                score = 1;
            } else if (status == player) {//not pc --> -1
                score = -1;
            } else if (status == 't') {//t means tie (draw)   --> 0
                score = 0;
            }
            return score;
        }
//////////////////////////////////////////

        if (turn == pc) {//maximizing (pc's turn)
            int bestScore = -999;

            for (int i = 0; i < board.length; i++) {//getting all possible moves { 6, 7, 9}
                if (board[i] == '.') {// is the spot available?

                    board[i] = turn;//pc
                    int currScore = miniMax(board, player, depth + 1);//calculating the score of (player's turn)
                    board[i] = '.';
//                    if ((turn == pc && currScore > best) || (turn != pc && currScore < best)) {
                    if (currScore > bestScore) {
                        bestScore = currScore;
                    }

                }

            }
            return bestScore;
        }else {//turn == player     ((player's turn))   //want to find the moves that leads to the lowest score for player
            int lowestScore = 999;

            for (int i = 0; i < board.length; i++) {//getting all possible moves { 6, 7, 9}
                if (board[i] == '.') {// is the spot available?

                    board[i] = turn;//pc
                    int currScore = miniMax(board, pc, depth + 1);//calculating the score of (player's turn)
                    board[i] = '.';
//                    if ((turn == pc && currScore > best) || (turn != pc && currScore < best)) {
                    if (currScore < lowestScore) {
                        lowestScore = currScore;
                    }

                }

            }
            return lowestScore;
        }

//        for (int i = 0; i < board.length; i++) {
//            if (board[i] == '.') {//all possible moves { 6, 7, 9}
//
//                board[i] = turn;
//                int current = miniMax(board, (turn == 'x' ? 'o' : 'x'), depth + 1);
//                if ((turn == pc && current > best) || (turn != pc && current < best)) {
//                    best = current;
//                }
//                board[i] = '.';
//            }
//        }
//        return best;
    }

    private static boolean GameEnds(char[] board) {

        for (int i = 0; i < board.length; i++) {
            if (board[i] == '.')
                return false;
        }

        return true;
    }

    private static char checkStatus(char[] board) {
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

        for (int j = 0; j < AgainstPcCont.winCases.length; j++) {
            int a = AgainstPcCont.winCases[j][0];
            int b = AgainstPcCont.winCases[j][1];
            int c = AgainstPcCont.winCases[j][2];
            if (board[a] == '.' || board[b] == '.' || board[c] == '.') {
                continue;
            }
            if (board[a] != '.' && board[a] == board[b]
                    && board[a] == board[c]) {
                return board[a];//winner
            }
        }
        //n(still no winner) or x or o
        char result;
        if (boardIsFull(board)) {
            result = 't';//tie    (Draw)
        } else {
            result = 'n';//null    (no winner yet)
        }
        return result;
    }

    private static boolean boardIsFull(char[] board) {//was GameNotFinished

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'x' || board[i] == 'o') {
                return false;
            }

        }
        return true;
    }

    public static void main(String[] args) {
//        char[] board = {'x', '.', '.', '.', '.', '.', '.', '.', '.'};
//        System.out.println(getBestMove(board));
    }
}

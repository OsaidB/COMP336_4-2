package com.example.comp336_42;

import static com.example.comp336_42.AgainstPcCont.user;
import static com.example.comp336_42.AgainstPcCont.pc;

public class MiniMaxAlgo {
    static char[] board;
    static int[] bestMoves;

    public static int makeAMove(char[] board) {
        MiniMaxAlgo.board = board;
        bestMoves = new int[9];
        return getBestMove();
    }

    private static int getBestMove() {
        int bestScore = -999;
        int bestMove = -1;

        for (int i = 0; i < board.length; i++) {//getting all possible moves { 6, 7, 9}

            if (board[i] == '.') {// is the spot available?
                board[i] = pc;

                int score = miniMax(user);
                board[i] = '.';
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                    bestMoves[i]=bestMove;
                }
            }
        }
        System.out.println("(getBestMove)   -->     BestMove=" + bestMove);
        return bestMove;
    }

    private static int miniMax(char turn) {
        //////////////////////////////////////////
        int score = 404;//just a random number to initialize variable

        char status = checkStatus();

        if (status != 'n') { //if status = 'n' --> then game is not over yet
            if (status == pc) {//same as pc --> 1
                score = 1;
            } else if (status == user) {//not pc --> -1
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
                    int currScore = miniMax(user);//calculating the score of (player's turn)
                    board[i] = '.';
                    if (currScore > bestScore) {
                        bestScore = currScore;
                    }

                }

            }
            return bestScore;
        } else {//turn == player     ((player's turn))   //want to find the moves that leads to the lowest score for player

            int lowestScore = 999;

            for (int i = 0; i < board.length; i++) {//getting all possible moves { 6, 7, 9}
                if (board[i] == '.') {// is the spot available?

                    board[i] = turn;//pc
                    int currScore = miniMax(pc);//calculating the score of (player's turn)
                    board[i] = '.';
                    if (currScore < lowestScore) {
                        lowestScore = currScore;
                    }

                }

            }
            return lowestScore;
        }
    }

    private static char checkStatus() {
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
        //three cases: {n(still no winner)} or {x} or {o}
        char result;
        if (boardIsFull()) {
            result = 't';//tie    (Draw)
        } else {
            result = 'n';//null    (no winner yet)
        }
        return result;
    }

    private static boolean boardIsFull() {//was GameNotFinished
        for (char c : board) {
            if (c == '.') { //whenever we find one '.' element
                // --> then the board is not full
                return false;
            }
        }
        return true;
    }

}

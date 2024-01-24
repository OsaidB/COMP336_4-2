import java.util.Arrays;
public class ComputerPlay {
	public static String MODE = "Easy";
	public static char cturn = 'o';

	public static int MakeMove(char[] board) {
		if (MODE.equals("Easy") || MODE.equals("Medium")) {
			int randomMove = (int) (Math.random() * 8);
			while (board[randomMove] != '.') {
				randomMove = (int) (Math.random() * 8);
			}
			return randomMove;
		} else if (MODE.equals("Expert")) {
			int move = getBestMove(board);
			return move;
		}
		return -1;
	}



	private static int evaluateScore(char[] board) {
		int[][] Cases = {
			{0, 1, 2},
			{3, 4, 5},
			{6, 7, 8},
			{0, 3, 6},
			{1, 4, 7},
			{2, 5, 8},
			{0, 4, 8},
			{2, 4, 6}
		};

		for (int j = 0; j < Cases.length; j++) {
			int a = Cases[j][0];
			int b = Cases[j][1];
			int c = Cases[j][2];
			if (board[a] != '.' && board[a] == board[b] && board[a] == board[c]) {
				if (board[a] == cturn)
					return 10;
				else
					return -10;
			}
		}

		return 0;
	}


	private static int MiniMax(char board[], char turn, int depth) {
		int score = evaluateScore(board);
		if (score == 10)
			return score - depth;
		if (score == -10)
			return score+depth;
		if (GameEnds(board)) return 0;

		int best = (turn == cturn) ? -1000 : 1000;

		for (int i = 0; i < board.length; i++) {
			if (board[i] == '.') {
				board[i] = turn;
				int current = MiniMax(board, (turn == 'x' ? 'o' : 'x'), depth + 1);
				if ((turn == cturn && current > best) || (turn != cturn && current < best)) {
					best = current;
				}
				board[i] = '.';

			}
		}
		return best;
	}

	private static boolean GameEnds(char[] board) {

		for (int i = 0; i < board.length; i++) {
			if (board[i] == '.')
				return false;
		}

		return true;
	}

	private static int getBestMove(char board[]) {
		int max = -10001;
		int maxi = -1;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == '.') {

				board[i] = cturn;

				int thismax = MiniMax(board, (cturn == 'x' ? 'o' : 'x'), 0);
				if (thismax > max) {
					max = thismax;
					maxi = i;
				}
				board[i] = '.';
			}
		}
		return maxi;
	}

	public static void main(String[] args) {
		char[] board = {'x','.','.','.','.','.','.','.','.'};
		System.out.println(getBestMove(board));
	}
}

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	static char player = 'x';
	static char player1 = 'x';
	static char player2 = 'o';
	public static Button[] buttons = new Button[9];
	public Button playwithc;
	public Button button00;
	public Button button01;
	public Button button02;
	public Button button10;
	public Button button11;
	public Button button12;
	public Button button20;
	public Button button21;
	public Button button22;
	public Label identf;
	public Label idref;
	public TextField play1;
	public TextArea play2;
	public Button startb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttons[0] = button00;
		buttons[1] = button01;
		buttons[2] = button02;
		buttons[3] = button10;
		buttons[4] = button11;
		buttons[5] = button12;
		buttons[6] = button20;
		buttons[7] = button21;
		buttons[8] = button22;

	}

	public void LevelsScene() {
		try {
			Main.SwitchToLevelsScene();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void OpenGameEasy() {
		try {
			Main.ShowBoard();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setText("");
		}
		if (player=='o') {
			int r = (int) (Math.random()*8);
			buttons[r].setText("X");
			buttons[r].setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");

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
							if (GameNotFinished()) {
								System.out.println("....");
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

	private char Won(Button[] buttons) {
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
			if (buttons[a].getText().equals("") || buttons[b].getText().equals("") || buttons[c].getText().equals(""))
				continue;
			if (!buttons[a].getText().equals("") && buttons[a].getText().charAt(0) == buttons[b].getText().charAt(0) && buttons[a].getText().charAt(0) == buttons[c].getText().charAt(0)) {
				return buttons[a].getText().charAt(0);
			}
		}
		return 'n';
	}

	private char[] getBoard() {
		char[] arr = new char[9];
		for (int i = 0; i < arr.length; i++) {
			if (buttons[i].getText().equals(""))
				arr[i] = '.';
			else
				arr[i] = buttons[i].getText().toLowerCase(Locale.ROOT).charAt(0);

			System.out.println(arr[i]);
		}

		return arr;
	}

	private boolean GameNotFinished() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getText().equals(""))
				return true;
		}
		return false;
	}

	public void OpenGameExpert() {

		try {
			Main.ShowBoard();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (player=='o') {
			int r = (int) (Math.random()*8);
			buttons[r].setText("X");
			buttons[r].setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");

		}
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setText("");
		}
		for (int i = 0; i < buttons.length; i++) {

			Button temp = buttons[i];
			temp.setOnAction(event -> {
				if (temp.getText().equals("")) {
					if ( Won(buttons) == 'n') {
						temp.setStyle("-fx-text-fill:  #f8d320; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
						temp.setText(player == 'x' ? "X": "O");
						if (Won(buttons) != 'n') {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setContentText("Player " + Won(buttons) + " Wins!");
							alert.showAndWait();
						} else {
							if (GameNotFinished()) {
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

	public void OpenMultiplayerScene() {
		try {
			Main.ShowMulti();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void OnStartMulti() {
		try {
			Main.ShowBoard();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setText("");
		}
		for (int i = 0; i < buttons.length; i++) {

			Button temp = buttons[i];
			temp.setOnAction(event -> {
				if (temp.getText().equals("")) {

					if (player == 'x' && Won(buttons) == 'n') {
						temp.setStyle("-fx-text-fill:  #f8d320; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
						temp.setText("X");
						if (Won(buttons) != 'n') {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setContentText("Player " + Won(buttons) + " Wins!");
							alert.showAndWait();
						}
						player = 'o';
					} else if (player == 'o' && Won(buttons) == 'n') {
						temp.setStyle("-fx-text-fill:  White; -fx-font-family: 'Bell MT'; -fx-font-size: 45; -fx-background-color: Transparent;-fx-border-width: 4;-fx-border-color:  #f8d320");
						temp.setText("O");
						if (Won(buttons) != 'n') {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setContentText("Player " + Won(buttons) + " Wins!");
							alert.showAndWait();
						}
						player = 'x';
					}
				}
			});

		}
	}
	public void WhoStartX() {
		player='x';
		LevelsScene();
	}
	public void WhoStartO() {
		player = 'o';
		LevelsScene();
	}
	public void WhoStart() {
		try {
			Main.SwitchToWhoStart();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}



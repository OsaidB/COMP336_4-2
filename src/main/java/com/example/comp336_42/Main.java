import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
public static Stage GameStage = new Stage();
	public static void main(String[] args) {
		Application.launch(args);
	}

	public static void ShowBoard() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("boardGamePlay.fxml"));
		Parent root = loader.load();
		GameStage.setScene(new Scene(root));
	}

	public static void ShowMulti() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("Names.fxml"));
		Parent root = loader.load();
		GameStage.setScene(new Scene(root));
	}

	public static void SwitchToWhoStart() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("WhoStart.fxml"));
		Parent root = loader.load();
		GameStage.setScene(new Scene(root));
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = loader.load();
		GameStage.setScene(new Scene(root));
		GameStage.show();
	}

	public static void SwitchToLevelsScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("Levels.fxml"));
		Parent root = loader.load();
		GameStage.setScene(new Scene(root));
	}
}

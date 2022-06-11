package application;

import java.io.IOException;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import router.MainRouter;
import vo.Member;

public class Main extends Application {

	public static Member loginMember = null;
	public static SceneLoader sceneLoader;
	public static Connector conn;

	public Main() {
		conn = new Connector();
		conn.connect();
		sceneLoader = new SceneLoader();
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
			BorderPane main = (BorderPane) loader.load();
			Scene scene = new Scene(main);
			primaryStage.setScene(scene);

			MainController mainCon = loader.getController();
			mainCon.setStage(primaryStage);
			MainRouter.stage = primaryStage;

			primaryStage.setOnCloseRequest((ev) -> {
				Connector.isRun = false;
				if (Connector.isConnected) {
					try {
						Connector.server.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package application;

import java.io.IOException;
import java.util.ArrayList;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import router.MainRouter;
import vo.Member;

public class Main extends Application {

	public static Member loginMember = null;
	public static ArrayList<Object> alertList = null;

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
			try {
				System.setProperty("prism.lcdtext", "false");

				Font.loadFont(Class.forName("application.Main").getResource("../css/resources/fonts/SLEIGothicTTF.ttf")
						.toString(), 16);
				String fontFamily = Font.loadFont(Class.forName("application.Main")
						.getResource("../css/resources/fonts/SLEIGothicTTF.ttf").toString(), 16).getFamily();
				System.out.println(fontFamily);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
			BorderPane main = (BorderPane) loader.load();
			Scene scene = new Scene(main);
			primaryStage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());
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

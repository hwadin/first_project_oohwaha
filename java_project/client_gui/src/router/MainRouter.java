package router;

import java.io.IOException;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import network_dto.NetworkData;
import vo.Member;

public class MainRouter {
	NetworkData<?> data;

	public static Stage stage;

	public NetworkData<?> route(NetworkData<?> data) {
		String action = data.getAction();
		Object value = data.getV();

		if (Main.loginMember == null) {
			Member m = null;
			if (value instanceof Member) {
				m = (Member) value;
			}
			switch (action) {
			case "member/login":
				login(m);
				break;
			case "member/join":

				break;
			}
		}
		return null;
	}

	private void login(Member member) {
		Main.loginMember = member;
		try {
			BorderPane userMain = FXMLLoader.load(getClass().getResource("../view/UserMain.fxml"));
			Platform.runLater(() -> {
				stage.setScene(new Scene(userMain));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
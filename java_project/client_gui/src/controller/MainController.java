package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import network_dto.NetworkData;
import router.MainRouter;
import vo.Member;

public class MainController implements Initializable {

	@FXML
	private TextField userid, userpw;
	@FXML
	private Button btnLogin;
	@FXML
	private Hyperlink btnJoin;

	public static final String IP = "127.0.0.1";
	public static final int PORT = 5001;

	static Socket server;

	private static ObjectOutputStream req;
	private static ObjectInputStream res;

	public static Stage stage;

	NetworkData<?> returnData;

	public void setStage(Stage primaryStage) {
		this.stage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction((event) -> {
			String id = userid.getText();
			String pw = userpw.getText();

			System.out.println("id : " + id + ", pw : " + pw);
			Connector.send(new NetworkData<Member>("member/login", new Member(id, pw)));
			Main.alertList = new ArrayList<>();
		});

		btnJoin.setOnAction((event) -> {
			Scene scene = new Scene((AnchorPane) Main.sceneLoader.load(SceneLoader.JOIN_PATH));
			System.out.println(stage);
			MainRouter.stage = stage;
			stage.setScene(scene);

		});

		userpw.setOnKeyReleased(ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				btnLogin.fire();
			}
		});

	}
//
//	public void send(NetworkData<?> data) {
//		try {
//			req = new ObjectOutputStream(new BufferedOutputStream(server.getOutputStream()));
//			req.writeObject(data);
//			req.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return;
//	}
//
//	void receive() {
//		Thread t = new Thread(() -> {
//			while (true) {
//				try {
//					res = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
//					this.returnData = (NetworkData<Member>) res.readObject();
//					MainRouter.route(returnData);
//
//				} catch (IOException e) {
//					System.out.println("서버 연결 오류");
//					try {
//						server.close();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//					return;
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		t.start();
//
//	}
//
//	private void login(Member member) {
//		Main.loginMember = member;
//
//		try {
//			BorderPane userMain = (BorderPane) FXMLLoader.load(getClass().getResource("../view/UserMain.fxml"));
//			Platform.runLater(() -> {
//				System.out.println(stage);
//				stage.setScene(new Scene(userMain));
//			});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

}

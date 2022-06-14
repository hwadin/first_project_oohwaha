package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import network_dto.NetworkData;
import vo.Member;

public class JoinController implements Initializable {

	@FXML
	private AnchorPane Anchor;
	@FXML
	private ToggleGroup group;
	@FXML
	private TextField id, name, age;
	@FXML
	private Button idcheck, back, btnreg;
	@FXML
	private PasswordField password, passwordchk;
	@FXML
	private ComboBox<String> comboPublic1, comboPublic2;
	@FXML
	private BorderPane borderPane;

	boolean isOwner = false;
	public static Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				RadioButton value = (RadioButton) newValue;
				String text = value.getText();
				System.out.println(text);
				if (text.equals("일반회원")) {
					isOwner = false;
				} else {
					isOwner = true;
				}
			}
		}); // END ToggleGroup

		btnreg.setOnAction(e -> {

			// TextField
			String ID = id.getText();
			String pass = password.getText();
			String passchk = passwordchk.getText();
			String namee = name.getText();
			String agee = age.getText();

			// combobox
			String comboArea = comboPublic1.getValue();
			String comboArea2 = comboPublic2.getValue();

			// 아이디 입력 검사
			if (ID.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("아이디를 입력해 주세요.");
				alert.show();
				return;
			}
			// 비밀번호 입력 검사
			if (pass.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("비밀번호를 입력해 주세요.");
				alert.show();
				return;
			} else if (passchk.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("비밀번호 확인란을 입력해 주세요.");
				alert.show();
				return;
			} else if (!pass.equals(passchk)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("비밀번호가 일치하지않습니다.");
				alert.show();
				return;
			}
			// 이름 입력 검사
			if (namee.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("이름을 입력해 주세요.");
				alert.show();
				return;
			}
			// 나이 입력 검사
			if (agee.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("나이를 입력해 주세요.");
				alert.show();
				return;
			}
			// 거주지역 입력 검사
			if (comboPublic2.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("거주지역을 선택해 주세요.");
				alert.show();
				return;
			}

			Connector.send(new NetworkData<Member>("member/join",
					new Member(ID, pass, namee, Integer.parseInt(agee), comboArea + " " + comboArea2, isOwner)));

			Platform.runLater(() -> {
				Scene scene = new Scene((BorderPane) Main.sceneLoader.load(SceneLoader.MAIN_PATH));
				System.out.println(stage);
				stage = MainController.stage;
				stage.setScene(scene);
				stage.show();
			});
		});

		// 아이디 중복 체크
		idcheck.setOnAction(event -> {
			handlePopup();
		});

		// 이전 버튼 실행
		back.setOnAction(e -> {
			Scene scene = new Scene((BorderPane) Main.sceneLoader.load(SceneLoader.MAIN_PATH));
			System.out.println(MainController.stage);
			MainController.stage.setScene(scene);
		});

		comboPublic1.getSelectionModel().selectFirst();

	}// initialize END

	public void idcheck() {

		Connector.send(new NetworkData<Member>("member/find", new Member(id.getText())));

	}

	public void handlePopup() {

		Alert alert = new Alert(AlertType.ERROR);

		if (alert.isShowing()) {
			alert.hide();
		} else if (id.getText() == null || id.getText().equals("")) {

//        	final Window window = idcheck.getScene().getWindow();
			alert.setHeaderText("아이디를 입력해주세요.");
			alert.show();
		} else {
			idcheck();
		}
	}

}

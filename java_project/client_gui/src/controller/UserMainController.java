package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class UserMainController implements Initializable {

	@FXML
	private Label txtTitle, userName;

	@FXML
	private Button btnCalendar;

	@FXML
	private BorderPane borderPane;

	@FXML
	private AnchorPane userMainPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userName.setText(Main.loginMember.getId());

		txtTitle.setCursor(Cursor.HAND);
		txtTitle.setOnMouseClicked(ev -> {
			borderPane.setCenter(userMainPane);
		});

		btnCalendar.setOnAction(event -> {
			AnchorPane monthCal = (AnchorPane) Main.sceneLoader.load(SceneLoader.M_SCHEDULE_PATH);
			borderPane.setCenter(monthCal);
		});
	}

}

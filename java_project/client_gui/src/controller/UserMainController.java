package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import network_dto.NetworkData;
import service.MemberService;
import vo.Member;

public class UserMainController implements Initializable {

	@FXML
	private Label txtTitle, userName;

	@FXML
	private Button btnCalendar, btnFriend, btnSearch;

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

		btnFriend.setOnAction(event -> {
			AnchorPane friendList = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
			borderPane.setCenter(friendList);
			MemberService.setTarget(borderPane);
			Connector.send(new NetworkData<Member>("member/frdList", Main.loginMember));
		});

		btnSearch.setOnAction(ev -> {
			Popup pop = new Popup();
			AnchorPane searchIcon = (AnchorPane) Main.sceneLoader.load(SceneLoader.SEARCH_PATH);
			System.out.println(pop.getContent());
			pop.getContent().add(searchIcon);
			pop.setAutoHide(true);
			pop.show(MainController.stage);
		});

	}

}

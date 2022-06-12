package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import network_dto.NetworkData;
import service.MemberService;
import service.ScheduleService;
import vo.Member;

public class UserMainController implements Initializable {

	@FXML
	private Label txtTitle, userName;

	@FXML

	private Button btnCalendar, btnFriend, btnSearch, btnConfig;

	private TextField txtId;

	@FXML
	private Button btnCalendar, btnFriend, btnSearch;


	@FXML
	private BorderPane borderPane;

	@FXML
	private AnchorPane userMainPane;

	@FXML
	private VBox box1, box2, box3, box4, box5, box6, box7;

	@FXML
	private Label lbl01, lbl02, lbl03, lbl04, lbl05, lbl06, lbl07;

	Calendar cal;
	ArrayList<VBox> boxList;

	private static Node prevPage;

	public static Node getPrevPage() {
		return prevPage;
	}

	public static void setPrevPage(Node node) {
		UserMainController.prevPage = node;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boxList = new ArrayList<>(Arrays.asList(box1, box2, box3, box4, box5, box6, box7));

		cal = Calendar.getInstance();
		Date currentDate = new Date();
		cal.setTime(currentDate);

		drawCal();

		userName.setText(Main.loginMember.getId());

		txtTitle.setCursor(Cursor.HAND);
		txtTitle.setOnMouseClicked(ev -> {
			borderPane.setCenter(userMainPane);
		});

		btnCalendar.setOnAction(event -> {
			AnchorPane monthCal = (AnchorPane) Main.sceneLoader.load(SceneLoader.M_SCHEDULE_PATH);
			ScheduleService.setCalendar(monthCal);
			ScheduleService.setTarget(borderPane);
			borderPane.setCenter(monthCal);
		});

		btnFriend.setOnAction(event -> {
			AnchorPane friendList = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
			borderPane.setCenter(friendList);
			MemberService.setTarget(borderPane);
			Connector.send(new NetworkData<Member>("member/frdList", Main.loginMember));
		});

		btnSearch.setOnAction(ev -> {
			AnchorPane searchIcon = (AnchorPane) Main.sceneLoader.load(SceneLoader.SEARCH_PATH);

			MemberService.setTarget(searchIcon);
			Connector.send(new NetworkData<Member>("member/findId", new Member(txtId.getText())));

		});

		btnConfig.setOnAction(ev -> {
			setPrevPage(borderPane.getCenter());
			AnchorPane updateMem = (AnchorPane) Main.sceneLoader.load(SceneLoader.UPDATE_PATH);
			ScheduleService.setCalendar(updateMem);
			ScheduleService.setTarget(borderPane);
			borderPane.setCenter(updateMem);

		});

	}

	void drawCal() {
		ArrayList<Label> lblList = new ArrayList<>(Arrays.asList(lbl01, lbl02, lbl03, lbl04, lbl05, lbl06, lbl07));

		for (Label l : lblList) {
			l.setText("");
		}

		int dayMonth = cal.get(Calendar.DAY_OF_MONTH); // 이번 달의 오늘
		int days = cal.get(Calendar.DAY_OF_WEEK); // 오늘이 현재 주의 몇째 일(1이 일요일, 7이 토요일)

		int firstDay = dayMonth - days + 1; // 이번 달의 첫째 날
		int lastDay = dayMonth + 7 - days; // 이번 달의 마지막 날

		boxList.get(days - 1).setStyle("-fx-background-color:rgba(255,129,129,0.5);");

		// DB에서 스케쥴 읽어오게 요청 후 받아온 데이터에 기반하여 스케쥴 항목 추가

		ScheduleService.setBoxList(boxList);
		ScheduleService.setTarget(borderPane);
		Connector.send(new NetworkData<Member>("schedule/findWeek", Main.loginMember));
	};

}

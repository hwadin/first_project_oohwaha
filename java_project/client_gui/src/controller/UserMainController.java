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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import network_dto.NetworkData;
import service.MemberService;
import service.ScheduleService;
import vo.FrndList;
import vo.InviteList;
import vo.Member;

public class UserMainController implements Initializable {

	@FXML
	private Label txtTitle, userName, alertCount;

	@FXML
	private Button btnCalendar, btnFriend, btnSearch, btnConfig, btnAlert;

	@FXML
	private TextField txtId;

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
		alertCount.setVisible(false);
		drawCal();
		drawAlert();

		Popup pop = new Popup();
		Hyperlink logout = new Hyperlink();
		logout.setText("로그 아웃");
		pop.getContent().add(logout);
		logout.setPrefHeight(15);
		logout.setPrefWidth(100);
		logout.setAlignment(Pos.CENTER);
		logout.setStyle(
				"-fx-border-color: black; -fx-border-style: solid;-fx-background-color:white;-fx-font-family:'SLEI Gothic TTF', 'Arial Black';");

		logout.setOnAction(ev -> {
			Connector.send(new NetworkData<Member>("member/logout", Main.loginMember));
			Main.loginMember = null;
			Scene scene = new Scene((BorderPane) Main.sceneLoader.load(SceneLoader.MAIN_PATH));
			Stage stage = MainController.stage;
			stage.setScene(scene);
			stage.show();
			pop.hide();
		});

		userName.setText(Main.loginMember.getId());
		userName.setOnMouseClicked(ev -> {
			if (pop.isShowing()) {
				pop.setAutoHide(false);
				pop.hide();
			} else {
				pop.setAutoHide(true);
				final Window window = userName.getScene().getWindow();
				final double x = window.getX() + userName.localToScene(0, 0).getX() + userName.getScene().getX() - 100
						+ userName.getWidth();
				final double y = window.getY() + userName.localToScene(0, 0).getY() + userName.getScene().getY()
						+ userName.getHeight();
				pop.show(MainController.stage, x, y);
			}
		});

		txtTitle.setCursor(Cursor.HAND);
		txtTitle.setOnMouseClicked(ev -> {
			borderPane.setCenter(userMainPane);
			drawCal();
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

		btnAlert.setOnAction(ev -> {
			Popup alertPop = new Popup();
			VBox alertListBox = new VBox();
			alertListBox.setPrefWidth(300);
			alertListBox.setMaxWidth(300);
			alertListBox.setMinWidth(300);

			HBox alertItem = null;
			HBox itemText = null;
			HBox itemBtn = null;
			if (Main.alertList.size() > 0) {
//				alertListBox = new VBox();
				for (Object o : Main.alertList) {
					alertItem = new HBox();
					itemText = new HBox();
					itemBtn = new HBox();

					alertItem.setPadding(new Insets(0, 5, 0, 5));
					alertItem.setAlignment(Pos.CENTER_LEFT);
					alertItem.setMinHeight(40);
					alertItem.setSpacing(5);
					alertItem.setStyle(
							"-fx-background-color:white; -fx-border-style:solid; -fx-border-color:skyblue;-fx-font-family:'SLEI Gothic TTF', 'Arial Black';");
					itemText.setPrefWidth(200);
					itemBtn.setPrefWidth(100);
					alertItem.getChildren().addAll(itemText, itemBtn);
					itemText.setAlignment(Pos.CENTER_LEFT);
					itemBtn.setAlignment(Pos.CENTER_RIGHT);
					itemBtn.setSpacing(5);
					Label alert = new Label();
					Button accept = new Button(), reject = new Button();
					accept.setText("수락");
					reject.setText("거절");
					Button[] btns = { accept, reject };
					if (o instanceof FrndList) {
						FrndList frnd = (FrndList) o;
						alert.setText(frnd.getId() + "(" + frnd.getName() + ")님의 친구 추가");
						// 친구 수락
						accept.setOnAction(ev1 -> {
							Connector.send(new NetworkData<FrndList>("member/frdAccept", frnd));
							alertPop.hide();
						});
						// 친구 거절
						reject.setOnAction(ev2 -> {
							Connector.send(new NetworkData<FrndList>("member/frdReject", frnd));
							alertPop.hide();
						});
					} else if (o instanceof InviteList) {
						FrndList frnd = (FrndList) o;
						alert.setText(frnd.getId() + "(" + frnd.getName() + ")님의 모임 초대");
					}

					for (Button b : btns) {
						b.setStyle(
								"-fx-pref-width:20;-fx-font-family:'SLEI Gothic TTF', 'Arial Black'; -fx-border-radius:10;");
						if (b.equals(accept)) {
							b.setStyle("-fx-background-color:green;-fx-text-fill:white;");
						} else {
							b.setStyle("-fx-background-color:red;-fx-text-fill:white;");
						}
					}
					itemText.getChildren().add(alert);
					itemBtn.getChildren().addAll(accept, reject);
					alertListBox.getChildren().add(alertItem);
				}
			} else {
				itemText = new HBox();
				alertItem = new HBox();
				alertItem.setPadding(new Insets(0, 5, 0, 5));
				alertItem.setAlignment(Pos.CENTER_LEFT);
				alertItem.setMinHeight(40);
				alertItem.setSpacing(5);
				alertItem.setStyle(
						"-fx-background-color:white; -fx-border-style:solid; -fx-border-color:skyblue;-fx-font-family:'SLEI Gothic TTF', 'Arial Black';");
				itemText.setPrefWidth(200);
				itemText.setAlignment(Pos.CENTER_LEFT);
				itemText.getChildren().add(new Label("알림이 없습니다."));
				alertItem.getChildren().add(itemText);
				alertListBox.getChildren().add(alertItem);
			}
			alertPop.getContent().add(alertListBox);
			if (alertPop.isShowing()) {
				alertPop.setAutoHide(false);
				alertPop.hide();
			} else {
				alertPop.setAutoHide(true);
				final Window window = btnAlert.getScene().getWindow();
				final double x = window.getX() + btnAlert.localToScene(0, 0).getX() + btnAlert.getScene().getX()
						+ btnAlert.getWidth() - 300;
				final double y = window.getY() + btnAlert.localToScene(0, 0).getY() + btnAlert.getScene().getY()
						+ btnAlert.getHeight() + 5;
				alertPop.show(MainController.stage, x, y);
			}
		});

	}

	private void drawAlert() {
		alertCount.setPrefWidth(16);
		alertCount.setAlignment(Pos.CENTER);
		alertCount.setStyle(
				"-fx-border-radius:50; -fx-border-color:red; -fx-background-color:red; -fx-text-fill:white; -fx-background-radius:50;");
		Connector.send(new NetworkData<Member>("member/alert", Main.loginMember));
	}

	void drawCal() {

		for (VBox b : boxList) {
			b.getChildren().clear();
		}

		cal = Calendar.getInstance();
		Date currentDate = new Date();
		cal.setTime(currentDate);

		ArrayList<Label> lblList = new ArrayList<>(Arrays.asList(lbl01, lbl02, lbl03, lbl04, lbl05, lbl06, lbl07));

		for (Label l : lblList) {
			l.setText("");
		}

//		int dayMonth = cal.get(Calendar.DAY_OF_MONTH); // 이번 달의 오늘
		int days = cal.get(Calendar.DAY_OF_WEEK); // 오늘이 현재 주의 몇째 일(1이 일요일, 7이 토요일)

//		int firstDay = dayMonth - days + 1; // 이번 달의 첫째 날
//		int lastDay = dayMonth + 7 - days; // 이번 달의 마지막 날

		boxList.get(days - 1).setStyle("-fx-background-color:rgba(255,129,129,0.5);");

		// DB에서 스케쥴 읽어오게 요청 후 받아온 데이터에 기반하여 스케쥴 항목 추가

		ScheduleService.setBoxList(boxList);
		ScheduleService.setTarget(borderPane);
		Connector.send(new NetworkData<Member>("schedule/findWeek", Main.loginMember));
	};

}

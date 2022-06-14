package service;

import java.util.ArrayList;

import application.Main;
import application.SceneLoader;
import controller.MainController;
import controller.UserMainController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;
import network_dto.NetworkData;
import vo.Member;

public class MemberService {

	public static Parent target;

	public static void setTarget(Parent t) {
		target = t;
	}

	public static ArrayList<VBox> boxList;

	public static void setBoxList(ArrayList<VBox> boxList) {
		MemberService.boxList = boxList;
	}

	@SuppressWarnings("unchecked")
	public void frdList(ArrayList<Member> list) {
		System.out.println(list);
		AnchorPane frdListPage = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
		TableView<Member> frdList = (TableView<Member>) frdListPage.lookup("#tableView");
		TableColumn<Member, ?> tableColumn = new TableColumn<>();
		tableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumn.setMaxWidth(Double.MAX_VALUE);
		tableColumn.setStyle("-fx-pref-width:335;");
		frdList.getColumns().add(tableColumn);
		frdList.setItems(FXCollections.observableArrayList(list));
		frdList.getSelectionModel().selectFirst();

		Platform.runLater(() -> {
			if (target instanceof BorderPane) {
				((BorderPane) target).setCenter(frdListPage);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void findId(ArrayList<Member> list) {
		AnchorPane findIdPage = (AnchorPane) target;
		System.out.println(target);
		ListView<String> findId = (ListView<String>) findIdPage.getChildren().get(0);
		for (Member m : list) {
			Platform.runLater(() -> {
				System.out.println(Thread.currentThread());
				System.out.println(m.getId());
				findId.getItems().add(m.getId());
				findId.getSelectionModel().selectFirst();
			});
		}
		System.out.println(findId.hashCode());
		System.out.println("findId : " + findId);
		System.out.println("member" + findId.getItems());
		Popup pop = new Popup();
		pop.getContent().add(findIdPage);

		pop.setAutoHide(true);
//		pop.setX(1085);
//		pop.setY(210);
		final Window window = MainController.stage.getScene().getWindow();
		final double x = window.getX() + 622;
		final double y = window.getY() + 72;

		Platform.runLater(() -> {
			pop.show(MainController.stage, x, y);
		});
	}

//	public void mbList(ArrayList<Member> list) {
//
//		AnchorPane mbListPage = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
//		AnchorPane mbList = (AnchorPane) mbListPage.getChildren().get(0);
//		for (Member m : list) {
//			int No = m.getNo();
//			String ID = m.getId();
//			String name = m.getName();
//			int age = m.getAge();
//			String addr = m.getAddr();
//		}
//	}

	public void update(Member member) {
		Platform.runLater(() -> {
			BorderPane borderPane = (BorderPane) target;
			borderPane.setCenter(UserMainController.getPrevPage());
		});
	}

	public void delete(NetworkData<?> data) {
		int result = (Integer) data.getV();
		if (result == 1) {
			Main.loginMember = null;
			BorderPane border = (BorderPane) Main.sceneLoader.load(SceneLoader.MAIN_PATH);
			Platform.runLater(() -> {
				MainController.stage.setScene(new Scene(border));
			});
		} else {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("탈퇴 실패");
				alert.setHeaderText("탈퇴에 실패했습니다.");
				alert.show();
			});
		}
	}

	public void getAlert(ArrayList<Object> alertList) {
		Main.alertList = new ArrayList<>();
		Main.alertList.addAll(alertList);
		Platform.runLater(() -> {
			Label alertCount = (Label) MainController.stage.getScene().getRoot().lookup("#alertCount");
			if (alertList.size() != 0) {
				alertCount.setVisible(true);
				alertCount.setText(Integer.toString(alertList.size()));
			} else {
				alertCount.setVisible(false);
			}
		});
	}

}

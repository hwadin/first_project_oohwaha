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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
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

	public void frdList(ArrayList<Member> list) {
		System.out.println(list);
		AnchorPane frdListPage = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
		TableView<Member> frdList = (TableView<Member>) frdListPage.lookup("#tableView");
		TableColumn<Member, ?> tableColumn = new TableColumn<>();
		tableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//		tableColumn.setText("ID");
		tableColumn.setMaxWidth(Double.MAX_VALUE);
		tableColumn.setStyle("-fx-pref-width:335;");
		frdList.getColumns().add(tableColumn);
		frdList.setItems(FXCollections.observableArrayList(list));
		frdList.getSelectionModel().selectFirst();
		
		/*
//		ObservableList<String> obList = new ObservableList<String>();
		for (Member m : list) {
//			Platform.runLater(() -> {
				System.out.println(m.getId());
				frdList.getItems().add(m.getId());
				frdList.getSelectionModel().selectFirst();	
//			});
		}
		*/
		Platform.runLater(() -> {
			if (target instanceof BorderPane) {
				((BorderPane) target).setCenter(frdListPage);
			}
		});
	}

	public void findId(ArrayList<Member> list) {
		System.out.println("dddd");
		AnchorPane findIdPage = (AnchorPane) target;
		ListView findId = (ListView) findIdPage.getChildren().get(0);
		for (Member m : list) {
//			Platform.runLater(() -> {
			System.out.println(m.getId());
			findId.getItems().add(m.getId());
//			});
		}
		Popup pop = new Popup();
		pop.getContent().add(findIdPage);
		pop.setAutoHide(true);
		pop.setX(1085);
		pop.setY(210);
		Platform.runLater(() -> {
			pop.show(MainController.stage);
		});
	}
	
	public void mbList(ArrayList<Member> list) {
		
		AnchorPane mbListPage = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
		AnchorPane mbList = (AnchorPane) mbListPage.getChildren().get(0);
		for(Member m : list) {
			int No = m.getNo();
			String ID = m.getId();
			String name = m.getName();
			int age= m.getAge();
			String addr = m.getAddr();
			
////			for(VBox b: boxList) {
////				ObservableList<Node> bList = b.getChildren();
////				if(bList.size()>3){
////					Label tmp = (Label) bList.get(0);
////					Platform.runLater(() -> {
////						bList.clear();
////						bList.add(tmp);
////					});
////				}
//			}
		}
	}
//		AnchorPane frdListPage = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
//		ListView frdList = (ListView) frdListPage.getChildren().get(1);
////		ObservableList<String> obList = new ObservableList<String>();
//		for (Member m : list) {
//			Platform.runLater(() -> {
//				System.out.println(m.getId());
//				frdList.getItems().add(m.getId());
//			});
//		}
//		Platform.runLater(() -> {
//			if (target instanceof BorderPane) {
//				((BorderPane) target).setCenter(frdListPage);
//			}
//		});
	

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
}

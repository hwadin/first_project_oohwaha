package service;

import java.util.ArrayList;

import application.Main;
import application.SceneLoader;
import controller.MainController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import vo.Member;

public class MemberService {

	public static Parent target;

	public static void setTarget(Parent t) {
		target = t;
	}

	public void frdList(ArrayList<Member> list) {
		AnchorPane frdListPage = (AnchorPane) Main.sceneLoader.load(SceneLoader.F_LIST_PATH);
		ListView frdList = (ListView) frdListPage.getChildren().get(1);
//		ObservableList<String> obList = new ObservableList<String>();
		for (Member m : list) {
			Platform.runLater(() -> {
				System.out.println(m.getId());
				frdList.getItems().add(m.getId());
			});
		}
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
}

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

public class MonthDetailController implements Initializable {

	@FXML
	private Button btnNew;
	@FXML
	private TableView<String> table;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnNew.setOnAction(ev -> {
			Popup pop = new Popup();

			AnchorPane newSchedule = (AnchorPane) Main.sceneLoader.load(SceneLoader.NEW_SCHE_PATH);

			pop.getContent().add(newSchedule);
			pop.setAutoHide(true);
			pop.show(MainController.stage);
		});

	}

}

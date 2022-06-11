package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import network_dto.NetworkData;
import vo.Schedule;

public class MonthDetailController implements Initializable {

	@FXML
	private Button btnNew;
	@FXML
	private TableView<Schedule> table;
	@FXML
	private Label today;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnNew.setOnAction(ev -> {
			Popup pop = new Popup();

			AnchorPane newSchedule = (AnchorPane) Main.sceneLoader.load(SceneLoader.NEW_SCHE_PATH);

			Label targetDay = (Label) newSchedule.lookup("#today");

			targetDay.setText(today.getText());

			Button btnCancel = (Button) newSchedule.lookup("#btnCancel");
			btnCancel.setOnAction(ev2 -> {
				btnNew.setDisable(false);
				table.setDisable(false);
				pop.hide();
			});

			pop.getContent().add(newSchedule);
			pop.setAutoHide(true);
			pop.show(MainController.stage);

			btnNew.setDisable(true);
			table.setDisable(true);

			pop.setOnHidden(ev2 -> {
				if (table.isDisable()) {
					Popup parent = (Popup) btnNew.getScene().getWindow();
					parent.hide();
				}
			});
		});

		table.setOnMouseClicked(ev -> {
			if (ev.getClickCount() > 1) {
				Schedule selectedSchedule = (Schedule) table.getSelectionModel().selectedItemProperty().getValue();

				Connector.send(new NetworkData<Schedule>("schedule/findByNo",
						new Schedule(selectedSchedule.getNo(), Main.loginMember.getNo())));
				Popup pop = (Popup) table.getScene().getWindow();
				pop.hide();
			}
		});

	}

}

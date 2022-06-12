package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class SearchIconController implements Initializable {

	Alert alert;
	@FXML
	private Button btnFrdAdd;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnFrdAdd.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("친구 추가");
			alert.setHeaderText("친구 추가 요청");
			alert.setContentText("검색한 친구를 친구 목록에 추가하시겠습니까 ?");

			alert.showAndWait();

		});
	}

}

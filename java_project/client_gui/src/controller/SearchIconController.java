package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import network_dto.NetworkData;
import vo.Member;

public class SearchIconController implements Initializable {

	Alert alert;
	@FXML
	private Button btnFrdAdd;

	@FXML
	private ListView<String> frdList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnFrdAdd.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("친구 추가");
			alert.setHeaderText("친구 추가 요청");
			alert.setContentText("검색한 친구를 친구 목록에 추가하시겠습니까 ?");


			Member member = Main.loginMember;

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
//				System.out.println(frdList.getSelectionModel().getSelectedItem().getId());
				ArrayList<Member> frdAddList = new ArrayList<Member>();
				String frnd = frdList.getSelectionModel().getSelectedItem();
				frdAddList.add(member);
				frdAddList.add(new Member(frnd));
				Connector.send(new NetworkData<ArrayList<Member>>("member/frdAdd", frdAddList));
			} else {
				alert.close();
			}

		});
	}
}

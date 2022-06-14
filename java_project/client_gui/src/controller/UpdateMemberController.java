package controller;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import network_dto.NetworkData;
import service.MemberService;
import vo.Member;

public class UpdateMemberController implements Initializable {

	@FXML
	private Button btnAccept, btnDelete, btnCancel;

	@FXML
	private Label txtpwchk;

	@FXML
	private RadioButton radioIsOwner;

	@FXML
	private TextField id, pw, pwchk, age, name;

	@FXML
	private ComboBox<String> comboPublic1, comboPublic2;

	@FXML
	private ToggleGroup group;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Member member = Main.loginMember;

		id.setText(member.getId());
		id.setEditable(false);
		pw.setText(member.getPw());
		pwchk.setText(member.getPw());
		name.setText(member.getName());
		if (member.getAge() != 0) {
			age.setText(Integer.toString(member.getAge()));
		}
		if (member.getAddr() != null) {
			String addr = member.getAddr();
			comboPublic1.getSelectionModel().select(addr.split(" ")[0]);
			comboPublic2.getSelectionModel().select(addr.split(" ")[1]);
		}

		boolean isOwner = member.isOwner();
		if (isOwner) {
			radioIsOwner.setText("사업자");
		}

		radioIsOwner.setDisable(true);

		pwchk.setOnKeyReleased(e -> {
			if (!pw.getText().equals(pwchk.getText())) {
				txtpwchk.setText("비밀번호가 일치하지 않습니다.");
				txtpwchk.setStyle("-fx-text-fill:red");
			} else {
				txtpwchk.setText("비밀번호가 일치합니다.");
				txtpwchk.setStyle("-fx-text-fill:green");
			}
		});

		btnAccept.setOnAction(ev -> {
			// 회원정보 수정
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("수정 확인");
			alert.setHeaderText("정보를 수정하시겠습니까?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.loginMember.setName(name.getText());
				Main.loginMember.setAge(Integer.parseInt(age.getText()));
				Main.loginMember.setPw(pw.getText());
				Main.loginMember.setAddr(comboPublic1.getValue() + " " + comboPublic2.getValue());
				MemberService.setTarget((BorderPane) btnAccept.getScene().getRoot());
				Connector.send(new NetworkData<Member>("member/update", Main.loginMember));
			} else if (result.get() == ButtonType.CANCEL) {
				alert.close();
			}
		});
		btnCancel.setOnAction(ev -> {
			// 취소
			BorderPane borderPane = (BorderPane) btnCancel.getScene().getRoot();
			borderPane.setCenter(UserMainController.getPrevPage());
		});
		btnDelete.setOnAction(ev -> {
			// 탈퇴
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("수정 확인");
			alert.setHeaderText("정말로 탈퇴하시겠습니까?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Connector.send(new NetworkData<Member>("member/delete", Main.loginMember));
			} else if (result.get() == ButtonType.CANCEL) {
				alert.close();
			}
		});
	} // END initialize

}

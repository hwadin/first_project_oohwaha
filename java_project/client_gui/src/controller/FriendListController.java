package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import vo.Member;

public class FriendListController implements Initializable {

	@FXML
	private TableView<Member> tableView;
	@FXML
	private AnchorPane frd;
	@FXML
	private TextField frd_no, frd_id, frd_name, frd_age, frd_addr;
	@FXML
	private Button invite;

	int no;
	String id;
	String name;
	int age;
	String addr;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Member> list = FXCollections.observableArrayList();
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				if (click.getClickCount() == 2) {
					Member member = tableView.getSelectionModel().getSelectedItem();
					no = member.getNo();
					id = member.getId();
					name = member.getName();
					age = member.getAge();
					addr = member.getAddr();
					frd_no.setText(Integer.toString(no));
					frd_id.setText(id);
					frd_name.setText(name);
					frd_age.setText(Integer.toString(age));
					frd_addr.setText(addr);
					System.out.println(member);
					System.out.println(no + id + name + addr);
				}
			}
		});
	}
}












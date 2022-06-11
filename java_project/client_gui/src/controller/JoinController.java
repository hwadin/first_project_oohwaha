package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import network_dto.NetworkData;
import vo.Member;


public class JoinController implements Initializable {

	@FXML private AnchorPane Anchor;
	@FXML private ToggleGroup group;
	@FXML private TextField id, name, age;
	@FXML private Button idcheck, back, btnreg;
	@FXML private PasswordField password, passwordchk;
	@FXML private ComboBox<String> comboPublic1, comboPublic2;
	@FXML private BorderPane borderPane;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	@Override
	public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
		RadioButton value = (RadioButton)newValue;
		String text =value.getText();
		System.out.println(text);
		}
	}); // END ToggleGroup
	
	btnreg.setOnAction(e->{
		// TextField
		String ID = id.getText();
		String pass = password.getText();
		String passchk = passwordchk.getText();
		String namee = name.getText();
		String agee = age.getText();
		
		// combobox
		String comboArea = comboPublic1.getValue();
		String comboArea2 = comboPublic2.getValue();
	});	
	
	 //아이디 중복 체크
	idcheck.setOnAction(event->{
		idcheck();
//		alt("아이디중복알림"," 중복된아이디입니다. 다시시도해주세요 :)",AlertType.ERROR);
	});
	
	// 이전 버튼 실행
	 back.setOnAction(e->{ 
		  Scene scene = new Scene((BorderPane) Main.sceneLoader.load(SceneLoader.MAIN_PATH));
		  System.out.println(MainController.stage);
		  MainController.stage.setScene(scene);
	  });
	
	}// initialize END 
	
	public void alt(String title, String content, AlertType alt) {
	      Alert alert = new Alert(alt);
	      alert.setTitle(title);
	      alert.setHeaderText(null);
	      alert.getDialogPane().setGraphic(null);
	      alert.setContentText(content);
	      alert.show();
	   }
	
	public void idcheck() {
		
		if(id.getText() == null || id.getText().equals("")) {
			System.out.println("아이디 작성해주세요.");
			id.requestFocus();
			return;
		}
		Connector.send(new NetworkData<Member>("member/find", new Member(id.getText())));
		
	}
	
}

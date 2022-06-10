package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MonthScheduleController implements Initializable {

	@FXML
	private GridPane grid;

	@FXML
	private VBox box01, box02, box03, box04, box05, box06, box07, box08, box09, box10, box11, box12, box13, box14,
			box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25, box26, box27, box28, box29,
			box30, box31, box32, box33, box34, box35;

	@FXML
	private Label lbl01, lbl02, lbl03, lbl04, lbl05, lbl06, lbl07, lbl08, lbl09, lbl10, lbl11, lbl12, lbl13, lbl14,
			lbl15, lbl16, lbl17, lbl18, lbl19, lbl20, lbl21, lbl22, lbl23, lbl24, lbl25, lbl26, lbl27, lbl28, lbl29,
			lbl30, lbl31, lbl32, lbl33, lbl34, lbl35;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Calendar cal = Calendar.getInstance();

		String currentDate = new SimpleDateFormat("MM").format(cal.getTime());
		System.out.println(currentDate);

		int year = cal.get(Calendar.YEAR); // 올해
		int month = cal.get(Calendar.MONTH); // 이번 달(0~11)
		int dayMonth = cal.get(Calendar.DAY_OF_MONTH); // 이번 달의 오늘
		int firstDay = cal.getActualMinimum(month); // 이번 달의 첫째 날
		int lastDay = cal.getActualMaximum(month); // 이번 달의 마지막 날
		int weeks = cal.get(Calendar.WEEK_OF_MONTH); // 오늘이 현재 월의 몇째 주
		int days = cal.get(Calendar.DAY_OF_WEEK); // 오늘이 현재 주의 몇째 일

		System.out.println("year : " + year);
		System.out.println("month : " + month);
		System.out.println("day : " + dayMonth);
		System.out.println("firstDay : " + firstDay);
		System.out.println("lastDay : " + lastDay);
		System.out.println("주차 : " + weeks);
		System.out.println("요일 : " + days);

	}

}

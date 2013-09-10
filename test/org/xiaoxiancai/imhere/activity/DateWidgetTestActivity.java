package org.xiaoxiancai.imhere.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import java.util.Calendar;

public class DateWidgetTestActivity extends Activity {

	private DatePicker datePicker;
	private TimePicker timePicker;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datewidget_test);

		datePicker = (DatePicker) findViewById(R.id.datepicker);
		timePicker = (TimePicker) findViewById(R.id.timepicker);

		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR);
		minute = calendar.get(Calendar.MINUTE);

		datePicker.init(year, month, day, new OnDateChangedListener() {

			public void onDateChanged(DatePicker view, int year, int month,
					int day) {
				DateWidgetTestActivity.this.year = year;
				DateWidgetTestActivity.this.month = month;
				DateWidgetTestActivity.this.day = day;
				showDate(year, month, day, hour, minute);
			}

		});

		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			public void onTimeChanged(TimePicker view, int hour, int minute) {
				DateWidgetTestActivity.this.hour = hour;
				DateWidgetTestActivity.this.minute = minute;
				showDate(year, month, day, hour, minute);
			}
		});

	}

	private void showDate(int year, int month, int day, int hour, int minute) {
		EditText show = (EditText) findViewById(R.id.show);
		show.setText("the date is : " + year + "年" + month + "月" + day + "日 "
				+ hour + "时" + minute + "分 ");
		System.out.println("the date is : " + year + "年" + month + "月" + day
				+ "日 " + hour + "时" + minute + "分 ");
	}
}

package jixiang.com.myandroid.view;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import jixiang.com.myandroid.R;

public class TestDateTimePickerActivity extends Activity {
	DatePicker datePicker;
	TimePicker timePicker;
	Calendar calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_datepicker_timepicker);
		datePicker = (DatePicker) findViewById(R.id.datepicker);
		timePicker = (TimePicker) findViewById(R.id.timepicker);
		calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"), Locale.US);
		
		/**
		 * 日期选择控件的初始化 year 年 monthOfYear 月 dayOfMonth 日 onDateChangedListener
		 * 日期改变监听
		 */
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				new OnDateChangedListener() {
			        //日期改变的监听
					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						calendar.set(year, monthOfYear, dayOfMonth);
						System.out.println("year="+ year + ", mont=" + monthOfYear + ", daty=" + dayOfMonth);
					}
				});
		/**
		 * 时间改变监听
		 */
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				System.out.println("hour="+ hourOfDay + ", time=" + minute);
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
			}
		});
	}
}

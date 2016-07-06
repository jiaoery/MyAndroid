package jixiang.com.myandroid.dialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.adapterview.FruitAdapter;
import jixiang.com.myandroid.adapterview.TestSpinnerActivity;
import jixiang.com.myandroid.notification.TestNotificationActivity;


public class TestDialogActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_dialog);
		findViewById(R.id.show1).setOnClickListener(this);
		findViewById(R.id.show2).setOnClickListener(this);
		findViewById(R.id.show3).setOnClickListener(this);
		findViewById(R.id.show4).setOnClickListener(this);
		findViewById(R.id.show5).setOnClickListener(this);
		findViewById(R.id.show6).setOnClickListener(this);
		findViewById(R.id.show7).setOnClickListener(this);
		findViewById(R.id.show8).setOnClickListener(this);
		findViewById(R.id.show9).setOnClickListener(this);
		findViewById(R.id.show10).setOnClickListener(this);
		findViewById(R.id.show11).setOnClickListener(this);
		findViewById(R.id.show12).setOnClickListener(this);
		findViewById(R.id.show13).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show1:
			showDiallog1();
			break;
		case R.id.show2:
			showCustomViewDialog();
			break;
		case R.id.show3:
			showSingleChoiceDialog();
			break;
		case R.id.show4:
			showMutlitiChoiceDialog();
			break;
		case R.id.show5:
			showAdapterDialog();
			break;
		case R.id.show6:
			showDatePickerDialog();
			break;
		case R.id.show7:
			showTimePickerDialog();
			break;
		case R.id.show8:
			showProgressDialog();
			break;
		case R.id.show9:
			Intent intent = new Intent(this, ActivityAsDialog.class);
			startActivity(intent);
			break;
		case R.id.show10:
			showMyDialog();
			break;
		case R.id.show11:
			showCustomStyleDialog();
			break;
		case R.id.show12:
			showCustomAlertDialog();
			break;
		case R.id.show13:
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			manager.cancel(TestNotificationActivity.NOTIFICATION_ID1);
			break;
		}
	}

	public void showCustomAlertDialog() {
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(
				new ContextThemeWrapper(this,
						android.R.style.Theme_DeviceDefault_Light_Dialog));
		builder.setTitle("自定义样式的对话框");
		builder.setIcon(R.drawable.a010);
		builder.setMessage("自定义样式的对话框");
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}

	public void showCustomStyleDialog() {
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		builder.setTitle("自定义风格的对话框");
		builder.setIcon(R.drawable.a010);
		builder.setMessage("自定义风格的对话框");
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}

	// 显示自定义的Dialog
	public void showMyDialog() {
		MyDialog dialog = new MyDialog(this);
		dialog.setIcon(R.drawable.a008);
		dialog.setTitle("这是我自定义的Dialog");
		dialog.setMessage("哈哈，这是我的dialog, 好不容易终于写出来了！happy 一下");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("my dialog confirm");
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("my dialog cancel");
			}
		});
		dialog.show();
	}

	public void showProgressDialog() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(R.drawable.a008);
		progressDialog.setTitle("进度条对话框");
		progressDialog.setMax(100);
		progressDialog.setProgress(0);
		// 水平样式
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (progressDialog.getProgress() < progressDialog.getMax()) {
					int progress = progressDialog.getProgress() + 2;
					progressDialog.setProgress(progress);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				progressDialog.dismiss();
			}
		}).start();
	}

	public void showTimePickerDialog() {
		TimePickerDialog timePickerDialog = new TimePickerDialog(this,
				new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						calendar.set(Calendar.MINUTE, minute);
						System.out.println("hour=" + hourOfDay + ", minute="
								+ minute);
						// 你的逻辑代码也需要写在这儿
					}
				}, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), false);
		timePickerDialog.setIcon(R.drawable.a006);
		timePickerDialog.setTitle("时间选择对话框");
		/*
		 * timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { }
		 * });
		 */
		timePickerDialog.show();
	}

	Calendar calendar = Calendar.getInstance(Locale.getDefault());

	public void showDatePickerDialog() {
		/**
		 * context 上下文 OnDateSetListener 日期改变监听 year 初始化的年份 month 初始化的月份
		 * dayOfMonth 初始化的日期
		 */
		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// 当选择日期，也就是日期改变的时候，回设置给calendar
						calendar.set(year, monthOfYear, dayOfMonth);
						System.out.println("你选择的年月日是="
								+ calendar.get(Calendar.YEAR) + "-"
								+ calendar.get(Calendar.MONTH) + "-"
								+ calendar.get(Calendar.DAY_OF_MONTH));
						// 你的逻辑代码应该设置这儿
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		// 设置title icon
		datePickerDialog.setIcon(R.drawable.a006);
		datePickerDialog.setTitle("日期选择对话框");

		/*
		 * datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * //按钮点击事件会触发OnDateSetListener.onDateSet方法 } });
		 */
		// 显示日期选择对话框
		datePickerDialog.show();
	}

	public void showAdapterDialog() {
		// 先生成一个AlertDialog.Builder 对象
		AlertDialog.Builder builder = new AlertDialog.Builder(getWindow()
				.getContext());
		final AlertDialog alertDialog = builder.create();
		// 设置icon title message
		alertDialog.setIcon(R.drawable.a005);
		alertDialog.setTitle("Adapter类的对话框");
		int[] images = { R.drawable.fruit_apple, R.drawable.fruit_little_apple,
				R.drawable.fruit_big_apple, R.drawable.fruit_banana,
				R.drawable.fruit_orange, R.drawable.fruit_peach,
				R.drawable.fruit_pear };
		String[] fruitTitles = getResources().getStringArray(
				R.array.fruit_titles);
		final List<TestSpinnerActivity.FruitBean> list = new ArrayList<TestSpinnerActivity.FruitBean>();
		for (int i = 0; i < images.length && i < fruitTitles.length; i++) {
			TestSpinnerActivity.FruitBean bean = new TestSpinnerActivity.FruitBean();
			bean.title = fruitTitles[i];
			bean.iconId = images[i];
			list.add(bean);
		}
		FruitAdapter adapter = new FruitAdapter(this, list);

		View view = LayoutInflater.from(this).inflate(R.layout.test_gridview,
				null);
		GridView gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				alertDialog.dismiss();
				System.out.println("You cliked =" + list.get(position).title);
			}
		});
		// 设置view
		alertDialog.setView(view);
		// 创建对话框，并显示对话框
		alertDialog.show();
	}

	public void showMutlitiChoiceDialog() {
		// 先生成一个AlertDialog.Builder 对象
		AlertDialog.Builder builder = new AlertDialog.Builder(getWindow()
				.getContext());
		// 设置icon title message
		builder.setIcon(R.drawable.a005);
		builder.setTitle("多选列表对话框");
		final String[] items = { "zhangsan", "lisi", "wangmazi" };
		final boolean[] checkedItems = { true, true, true };
		// 设置单选按钮组
		// checkedItem 默认选中哪几项 true为选中， false 为未选中
		builder.setMultiChoiceItems(items, checkedItems,
				new OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {

					}
				});
		// 设置按钮
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("You cliked nameAAAA=" + items[position]);
			}
		});
		// 创建对话框，并显示对话框
		builder.create().show();
	}

	int position;

	public void showSingleChoiceDialog() {
		// 先生成一个AlertDialog.Builder 对象
		AlertDialog.Builder builder = new AlertDialog.Builder(getWindow()
				.getContext());
		// 设置icon title message
		builder.setIcon(R.drawable.a005);
		builder.setTitle("单选列表对话框");
		final String[] items = { "zhangsan", "lisi", "wangmazi" };
		// 设置单选按钮组
		// checkedItem 默认选中哪一项 -1 一个都不选中， 0 选择第一项 1选中第二项， 后面依次类推
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("You cliked name=" + items[which]);
						position = which;
					}
				});
		// 设置按钮
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("You cliked nameAAAA=" + items[position]);
			}
		});
		// 创建对话框，并显示对话框
		builder.create().show();
	}

	public void showCustomViewDialog() {
		// 先生成一个AlertDialog.Builder 对象
		AlertDialog.Builder builder = new AlertDialog.Builder(getWindow()
				.getContext());
		// 设置icon title message
		builder.setIcon(R.drawable.a005);
		builder.setTitle("你饿了吗？");
		View view = LayoutInflater.from(this).inflate(
				R.layout.steps_relativelayout, null);
		builder.setView(view);
		// builder.setMessage("你早上是不是没吃早饭?");
		// 设置按钮
		builder.setPositiveButton("饿了", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("AAAAAAAAAA");
			}
		});
		builder.setNeutralButton("要饿没饿", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("BBBBBBBBBBBBBB");
			}
		});
		builder.setNegativeButton("没饿", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("CCCCCCCCCCCCCCC");
			}
		});
		// 创建对话框，并显示对话框
		builder.create().show();
	}

	public void showDiallog1() {
		// 先生成一个AlertDialog.Builder 对象
		AlertDialog.Builder builder = new AlertDialog.Builder(getWindow()
				.getContext());
		// 设置icon title message
		builder.setIcon(R.drawable.a005);
		builder.setTitle("你饿了吗？");
		builder.setMessage("你早上是不是没吃早饭?");
		// 设置按钮
		builder.setPositiveButton("饿了", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("AAAAAAAAAA");
			}
		});
		builder.setNeutralButton("要饿没饿", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("BBBBBBBBBBBBBB");
			}
		});
		builder.setNegativeButton("没饿", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("CCCCCCCCCCCCCCC");
			}
		});
		// 创建对话框，并显示对话框
		builder.create().show();
	}
}

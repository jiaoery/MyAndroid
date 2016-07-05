package jixiang.com.myandroid.layout;

import android.app.Activity;
import android.os.Bundle;

import jixiang.com.myandroid.R;

/**
 * @author Administrator
 *
 */
public class StepRelativeLayoutActivity extends Activity{
	/*private int screenWidth;
	private static final int MAX_STEPS = 12;
	private int stepWidth;
	private int stepHeight;
	private RelativeLayout relativeLayout;*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*screenWidth  = getWindow().getWindowManager().getDefaultDisplay().getWidth();
		stepWidth = screenWidth / MAX_STEPS;
		stepHeight = (int) (stepWidth * 0.75);
		System.out.println("width=" + screenWidth);*/
		setContentView(R.layout.steps_relativelayout);
		//relativeLayout = (RelativeLayout) findViewById(R.id.container);
		//addSteps();
	}

	/*public void addSteps(){
		//新生成一个按钮
		Button button = new Button(this);
		//给这个按钮设置背景图
		button.setBackgroundResource(R.drawable.activation_btn);
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(stepWidth, stepHeight);
		layoutParams.leftMargin = stepWidth;
		button.setLayoutParams(layoutParams);
		relativeLayout.addView(button);
	}*/
}

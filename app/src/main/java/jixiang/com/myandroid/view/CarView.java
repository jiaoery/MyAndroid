package jixiang.com.myandroid.view;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import jixiang.com.myandroid.R;

public class CarView extends LinearLayout {
	//宽高
	private int width;
	private int height;
	LayerDrawable layerDrawable;
	View view;
	Button button;

	public CarView(Context context) {
		this(context, null);
	}

	public CarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.test_shape, this);
		button = (Button) view.findViewById(R.id.buttonshape);
		layerDrawable = (LayerDrawable) button.getBackground();
		Drawable drawable = layerDrawable.getDrawable(1);
		ClipDrawable clipDrawable = (ClipDrawable) drawable;
		clipDrawable.setLevel(5000);
	}
	
}

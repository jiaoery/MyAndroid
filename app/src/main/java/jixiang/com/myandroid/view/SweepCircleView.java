package jixiang.com.myandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import jixiang.com.myandroid.R;

/**
 * Created by zn on 2016/12/25.
 * PPT双环形图
 */

public class SweepCircleView extends View{

    private Context mContext;

    private int mCrcleXY;//中心圆心

    private float mRadius;//圆的半径

    private RectF mRectF;//圆弧的外接矩阵

    private int length;//基准长度


    public SweepCircleView(Context context) {
        super(context);
        this.mContext=context;
        initView();
    }



    public SweepCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView();
    }

    public SweepCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SweepCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext=context;
        initView();
    }

    private void initView() {
        mCrcleXY=length/2;
        mRadius= (float) (length*0.5/2);
        mRectF=new RectF((float)(0.1*length) ,(float)(0.1*length),(float)(0.9*length),(float)(0.9*length));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        //宽度
        if(widthMode==MeasureSpec.EXACTLY){
            //WRAP_CONTET
            length=widthSize;
        }else{
            length=200;
            if(widthMode==MeasureSpec.AT_MOST){
                length=Math.min(length,widthSize);
            }
        }
        //高度
        if(heightMode==MeasureSpec.EXACTLY){
            length=Math.min(length,heightSize);
        }else if(heightMode==MeasureSpec.AT_MOST){
            length=Math.min(length,heightSize);
        }

        setMeasuredDimension(length,length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mCirclePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        //api 23之后建议使用的资源获取代码
        mCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.red));
        mCirclePaint.setStrokeWidth(1.0f);
        //绘制内圆
        canvas.drawCircle(mCrcleXY,mCrcleXY,mRadius,mCirclePaint);
        //绘制外圆弧形
        canvas.drawArc(mRectF,270,180,false,mCirclePaint);
        //绘制文字
        canvas.drawText("我爱你",0,3,mCrcleXY,mCrcleXY+3/4,mCirclePaint);
    }
}

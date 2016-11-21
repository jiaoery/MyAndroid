package jixiang.com.myandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import static android.R.attr.path;

/**
 * Created by jixiang52002 on 2016/11/21.
 */

public class PathTextView extends TextView{
    final String DRAW_STR="MyAndroid";
    Path[] paths=new Path[3];
    Paint paint;

    public PathTextView(Context context) {
        super(context);
        init();
    }

    public PathTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public PathTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    /**
     * 初始化部分数据
     */
    private void init() {
        paths[0]=new Path();
        paths[0].moveTo(0,0);
        for(int i=1;i<=20;i++){
            //随机生成20个点，并且将其连接成一条Path线
            paths[0].lineTo(i*30, (float) (Math.random()*30));
        }
        paths[1]=new Path();
        RectF rectF=new RectF(0,0,600,360);
        paths[1].addOval(rectF,Path.Direction.CCW);
        paths[2]=new Path();
        paths[2].addArc(rectF,60,180);
        //初始化画笔
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.translate(40,40);
        //设置从右边开始绘制（右对齐）
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(20);
        //绘制路径
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(paths[0],paint);
        paint.setTextSize(40);
        //沿着路径绘制一段文本
        paint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(DRAW_STR,paths[0],-8,20,paint);
        //对canvas进行坐标变换，画布下移60
        canvas.translate(0,60);
        //绘制路径
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(paths[2],paint);
        //沿着路径绘制一段文本
        paint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(DRAW_STR,paths[2],-10,20,paint);


    }
}

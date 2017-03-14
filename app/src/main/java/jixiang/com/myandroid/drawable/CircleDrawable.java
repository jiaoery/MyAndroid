package jixiang.com.myandroid.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by cqjix on 2017/3/14.
 */

public class CircleDrawable extends Drawable {
    private Paint mPaint;
    private int width;
    private Bitmap bitmap;

    public CircleDrawable(Bitmap bitmap){
        this.bitmap=bitmap;
        BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(shader);
        width=Math.min(bitmap.getWidth(),bitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(width/2,width/2,width/2,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight(){
        return width;
    }


    @Override
    public int getIntrinsicWidth(){
        return width;
    }
}

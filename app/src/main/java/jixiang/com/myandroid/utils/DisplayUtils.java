package jixiang.com.myandroid.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by zn on 2016/12/30.
 * dp、sp、转化为px的工具类
 */

public class DisplayUtils {
    /**
     * 将px转化为dip或dp值，保证尺寸大小不会变
     *
     * @param context
     * @param pxValue   传入px的值
     *  scale               （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);

    }

    /**
     *将dip或dp值转化成px值，保证尺寸大小不变
     * @param context
     * @param dipValue 传入dp的值
     *                 （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context,float dipValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (scale*dipValue+0.5f);
    }

    /**
     * 将px转化为sp的值，保证文字大小不变
     * @param context
     * @param pxValue 传入的px的值
     *                （DisplayMetics类属性 scaleDensity）
     * @return
     */
    public static int px2sp(Context context,float pxValue){
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    /**
     * 将sp值转化为px值，保证文字大小不变
     * @param context
     * @param spValue 传入的sp值
     *                （DisplayMetrics类中属性scaleDenisty）
     * @return
     */
    public static int sp2px(Context context,float spValue){
        final float fontScele=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScele+0.5f);
    }

    /**
     * 用于具体类
     *dp2px
     */
    protected int dp2px(Context context,int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
        ,dp
        ,context.getResources().getDisplayMetrics());
    }

    /**
     *用于具体的实体类
     * sp2px
     */
    protected int sp2px(Context context,int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP
        ,sp
        ,context.getResources().getDisplayMetrics());
    }
}

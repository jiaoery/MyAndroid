package jixiang.com.myandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

import jixiang.com.myandroid.R;

/**
 * Created by zn on 2016/12/25.
 * 具有弹性的listview
 */

public class OverScrollListView extends ListView {
    private int mMaxOverDistance=25;//弹性超过距离
    private Context mContext;

    public OverScrollListView(Context context) {
        super(context,null);
    }



    public OverScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView(attrs);
    }

    public OverScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        initView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OverScrollListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext=context;
        initView(attrs);
    }


    private void initView(@Nullable AttributeSet attrs) {
        DisplayMetrics metrics=mContext.getResources().getDisplayMetrics();
        float density=metrics.density;//基准屏幕比率
        //获取数据
        TypedArray typeArray=mContext.obtainStyledAttributes(attrs,R.styleable.overscoll_listview);
        mMaxOverDistance=typeArray.getInt(R.styleable.overscoll_listview_distance,25);
        mMaxOverDistance= (int) (density*mMaxOverDistance);
    }

    /**
     * 实现弹性滑动的效果
     * @param deltaX
     * @param deltaY
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY
     * @param isTouchEvent
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
    }
}

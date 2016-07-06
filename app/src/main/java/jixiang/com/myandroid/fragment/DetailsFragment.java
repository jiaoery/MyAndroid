package jixiang.com.myandroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DetailsFragment newInstance(int index) {
    	//实例化一个内容的碎片
        DetailsFragment f = new DetailsFragment();

        // Supply index input as an argument.
        //index 是当前选中的文章的下标
        Bundle args = new Bundle();
        args.putInt("index", index);
        //给这个碎片传递一个参数进去
        f.setArguments(args);
        return f;
    }

    //返回当前显示的文章的下标
    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    //创建文章内容的碎片的视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        
        /**
         * <ScrollView
         * android:layout_width="match_parent"
         * android:layout_height="wrap_content"
         * >
         *<TextView
         *   android:layout_width="match_parent"
         *   android:layout_height="wrap_content"
         *   android:padding="4dip"/>
         *
         * </ScrollView>
         */
        
        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        //使用代码计算4dip 是多少像素
        int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                4, getActivity().getResources().getDisplayMetrics());
        //给textView设置内间距
        text.setPadding(padding, padding, padding, padding);
        //把textView 加到scrollView里面
        scroller.addView(text);
        //设置内容, 使用当前index的下标的指定的文章内容
        text.setText(Shakespeare.DIALOGUE[getShownIndex()]);
        return scroller;
    }
}

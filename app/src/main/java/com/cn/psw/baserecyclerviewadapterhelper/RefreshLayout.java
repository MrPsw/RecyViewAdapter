package com.cn.psw.baserecyclerviewadapterhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Mr.peng on 2017/3/1.
 */

public class RefreshLayout extends ViewGroup {
    private final Context mContext;
    private int mLayoutContentHeight=0;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child == getHeader()) {
                    child.layout(0, -child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
                }else if(child==getFooter()){
                    child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                }else {
                    child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                    if (i < getChildCount()) {
                        if (child instanceof ScrollView) {
                            mLayoutContentHeight += getMeasuredHeight();
                            continue;
                        }
                        mLayoutContentHeight += child.getMeasuredHeight();
                    }
                }

            }
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        getHeader();
        getFooter();
    }

    private View getFooter() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout, null);
        TextView tv = (TextView) view.findViewById(R.id.text);
        tv.setText("正在加载");
        return view;
    }

    private View getHeader() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout, null);
        TextView tv = (TextView) view.findViewById(R.id.text);
        tv.setText("正在刷新");
        return view;
    }

}

package com.cn.psw.baserecyclerviewadapterhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.psw.baserecyclerviewadapterhelper.Base.BaseViewHolder;

/**
 * Created by Mr.peng on 2017/3/3.
 */

public class FooterViewHoder extends BaseViewHolder {
    public final LinearLayout mLoading;
    public final FrameLayout mLoadFail;
    public final FrameLayout mLoadPeriod;
    public FooterViewHoder(Context ct, View itemView) {
        super(ct,itemView);
        mLoading= (LinearLayout)itemView.findViewById(R.id.loading_view);
        mLoadFail= (FrameLayout)itemView.findViewById(R.id.load_fail_view);
        mLoadPeriod= (FrameLayout)itemView.findViewById(R.id.load_period_view);
    }
}

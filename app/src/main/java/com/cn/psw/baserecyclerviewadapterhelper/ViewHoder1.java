package com.cn.psw.baserecyclerviewadapterhelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Mr.peng on 2017/3/3.
 */

public class ViewHoder1 extends RecyclerView.ViewHolder {
    public final LinearLayout mLoading;
    public final FrameLayout mLoadFail;


    public ViewHoder1(View itemView) {
        super(itemView);
        mLoading= (LinearLayout)itemView.findViewById(R.id.loading_view);
        mLoadFail= (FrameLayout)itemView.findViewById(R.id.load_fail_view);


    }
}

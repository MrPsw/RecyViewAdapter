package com.cn.psw.baserecyclerviewadapterhelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mr.peng on 2017/3/3.
 */

public class ViewHoder2 extends RecyclerView.ViewHolder {
    public  TextView mTextView;
    public ViewHoder2(View itemView) {
        super(itemView);
        mTextView=(TextView) itemView.findViewById(R.id.using);
    }
}

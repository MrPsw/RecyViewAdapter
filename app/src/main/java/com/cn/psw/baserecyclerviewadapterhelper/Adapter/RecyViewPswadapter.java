package com.cn.psw.baserecyclerviewadapterhelper.Adapter;

import android.content.Context;

import com.cn.psw.baserecyclerviewadapterhelper.Base.BaseViewHolder;
import com.cn.psw.baserecyclerviewadapterhelper.R;
import com.cn.psw.baserecyclerviewadapterhelper.RecyCleViewAdapter;

import java.util.List;

/**
 * Created by Mr.peng on 2017/3/8.
 */

public class RecyViewPswadapter extends RecyCleViewAdapter<String> {


    public RecyViewPswadapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.invitedriverlist_item_layout;
    }

    @Override
    public void bindData(BaseViewHolder holder, int position, String item) {
        holder.getTextView(R.id.name).setText(item);
    }


}

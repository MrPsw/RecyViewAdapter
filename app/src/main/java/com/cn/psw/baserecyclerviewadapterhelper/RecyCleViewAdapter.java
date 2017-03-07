package com.cn.psw.baserecyclerviewadapterhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.peng on 2017/3/3.
 */

public class RecyCleViewAdapter extends RecyclerView.Adapter {
    private final LayoutInflater mInflater;
    List<String> list=new ArrayList<>();
    public  final static  int NORMAL=1;
    public final static  int LOAD=2;


    Context mContext;
    View LoadView;
    private LinearLayout mLoading;
    private FrameLayout mLoadEnd;
    private FrameLayout mLoadFail;
    private ViewHoder1 LoadViewHolder;

    public RecyCleViewAdapter(Context context,List<String> list) {
        mContext=context;
        this.list=list;
        mInflater = LayoutInflater.from(context);

}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        LoadView= mInflater.inflate(R.layout.loadlayout,parent,false);

        switch (viewType){
            case LOAD:

           return LoadViewHolder= new ViewHoder1(LoadView);
            case NORMAL:
               return viewHolder= new ViewHoder2(mInflater.inflate(R.layout.invitedriverlist_item_layout,parent,false));
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHoder2){
            ((ViewHoder2)holder).mTextView.setText(list.get(position)+"");
        }

    }

    @Override
    public int getItemCount() {
        return LoadView!=null?list.size()+1:list.size();
    }


    @Override
    public int getItemViewType(int position) {

      if(position==getItemCount()-1){
            if(LoadView!=null){
                return LOAD;
            }
        }
            return NORMAL;
    }

    public void setLoadViewStatus(LoadStatus L){
        LoadViewHolder. mLoadFail.setVisibility(View.GONE);
        LoadViewHolder. mLoading.setVisibility(View.GONE);
        switch (L){
            case LOAD:
                LoadViewHolder. mLoading.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                LoadViewHolder.mLoadFail.setVisibility(View.VISIBLE);
                break;

        }
    }
    enum LoadStatus {
        LOAD,ERROR,FINISH
    }
}

package com.cn.psw.baserecyclerviewadapterhelper.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected final List<T> mData;
    protected final Context mContext;
    protected LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private  OnItemViewChildOnClikListener mItemViewChildOnClikListener;
    private int [] mArrayid;

    public BaseRecyclerAdapter(Context ctx, List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseViewHolder holder = new BaseViewHolder(mContext,

                mInflater.inflate(getItemLayoutId(viewType), parent, false));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
        }
        if(mItemViewChildOnClikListener!=null){
            for ( int i=0;i<mArrayid.length;i++){
                holder.itemView.findViewById(mArrayid[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemViewChildOnClikListener.onItemViewChildOnClik(holder.itemView,holder.getLayoutPosition());
                    }
                });
            }



        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, position, mData.get(position));

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }
    public void add(List<T> mList) {
        //int itemCounPosition=mData.size()-1;
        if(mList!=null){
            mData.addAll(mList);
        }
        notifyItemRangeInserted(mData.size(),mList.size());
    }
    public List<T> getData() {
    return mData;
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clear() {
        mData.clear();
       notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    /**
     * 给itemView上面控件设置点击事件
     * @param Arrayid  控件id 可以多个，多个时用switch 根据id做自己的逻辑
     * @param listener  接口
     */
    public void setOnItemViewChildOnClikListener(int [] Arrayid,OnItemViewChildOnClikListener listener) {
        mItemViewChildOnClikListener = listener;
        mArrayid=Arrayid;
    }
    abstract public int getItemLayoutId(int viewType);

    abstract public void bindData(BaseViewHolder holder, int position, T item);

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View itemView, int pos);
    }
    public  interface  OnItemViewChildOnClikListener{
        public void onItemViewChildOnClik(View itemView, int pos);
    }
}
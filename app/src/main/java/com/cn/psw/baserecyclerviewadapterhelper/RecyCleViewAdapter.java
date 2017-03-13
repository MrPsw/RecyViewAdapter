package com.cn.psw.baserecyclerviewadapterhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.psw.baserecyclerviewadapterhelper.Base.BaseRecyclerAdapter;
import com.cn.psw.baserecyclerviewadapterhelper.Base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.peng on 2017/3/3.
 */

public abstract class RecyCleViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    Context mContext;
    protected final List<T> mData;
    protected LayoutInflater mInflater;


    public  final static  int NORMAL=1;
    public final static  int LOAD=2;
    public final static  int REFRESH=3;



    private BaseRecyclerAdapter.OnItemClickListener mClickListener;
    private BaseRecyclerAdapter.OnItemLongClickListener mLongClickListener;
    private BaseRecyclerAdapter.OnItemViewChildOnClikListener mItemViewChildOnClikListener;


    private View LoadView; //加载更多是视图
    private View RefreshView;
    private View NoramlView;
    private int [] mArrayid;


    private RecyclerView viewgroup;
    private HeaderViewHoder mHeaderViewHoder;
    private FooterViewHoder mFooterViewHoder;


    public RecyCleViewAdapter(Context context, List<T> data) {
        mContext=context;
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    public int getHeaderViewsCount(){
        return 1;
    }
    public int getFootViewsCount(){
        return 1;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder=null;
        LoadView= mInflater.inflate(R.layout.loadlayout,parent,false);
        RefreshView= mInflater.inflate(R.layout.refreshlayout,parent,false);
        NoramlView=mInflater.inflate(getItemLayoutId(viewType),parent,false);
       viewgroup= ((RecyclerView)parent);
        switch (viewType){
            case REFRESH:

                mHeaderViewHoder = new HeaderViewHoder(mContext,RefreshView);
                viewgroup.scrollTo(0,20);
                setRefreshViewStatus(Refreshtatus.FHINSH);
                return mHeaderViewHoder;

            case LOAD:
                return   mFooterViewHoder= new FooterViewHoder(mContext,LoadView);


            case NORMAL:
              viewHolder= new ContenViewHoder(mContext,NoramlView);
                final BaseViewHolder finalViewHolder = viewHolder;
                if (mClickListener != null) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mClickListener.onItemClick(finalViewHolder.itemView, finalViewHolder.getLayoutPosition());
                        }
                    });
                }
                if (mLongClickListener != null) {
                    viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mLongClickListener.onItemLongClick(finalViewHolder.itemView, finalViewHolder.getLayoutPosition());
                            return true;
                        }
                    });
                }
                if(mItemViewChildOnClikListener!=null){
                    for ( int i=0;i<mArrayid.length;i++){
                        viewHolder.itemView.findViewById(mArrayid[i]).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mItemViewChildOnClikListener.onItemViewChildOnClik(finalViewHolder.itemView,finalViewHolder.getLayoutPosition());
                            }
                        });
                    }
                }
                break;
        }
        return  viewHolder;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(holder instanceof HeaderViewHoder){
        }else if(holder instanceof FooterViewHoder){
        }else{
            bindData(holder, position, mData.get(position-1));
        }

    }



    @Override
    public int getItemCount() {
        return getHeaderViewsCount()+getFootViewsCount()+mData.size();
    }


    @Override
    public int getItemViewType(int position) {

      if(position==getItemCount()-1) {
          return LOAD;
      }else if(position==0){
          return REFRESH;
      }
          return NORMAL;
    }
    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(BaseRecyclerAdapter.OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    /**
     * 给itemView上面控件设置点击事件
     * @param Arrayid  控件id 可以多个，多个时用switch 根据id做自己的逻辑
     * @param listener  接口
     */
    public void setOnItemViewChildOnClikListener(int [] Arrayid,BaseRecyclerAdapter.OnItemViewChildOnClikListener listener) {
        mItemViewChildOnClikListener = listener;
        mArrayid=Arrayid;
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
    public void setLoadViewStatus(LoadStatus L){
        if(getFootViewsCount()>0){

            mFooterViewHoder .mLoading.setVisibility(View.GONE);
            mFooterViewHoder.mLoadFail.setVisibility(View.GONE);
            mFooterViewHoder.mLoadPeriod.setVisibility(View.GONE);
            switch (L){
                case LOAD:
                    mFooterViewHoder.mLoading.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    mFooterViewHoder.mLoadFail.setVisibility(View.VISIBLE);
                    break;
                case PERIOD:
                    mFooterViewHoder.mLoadPeriod.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }
    enum LoadStatus {
        LOAD,ERROR,PERIOD
    }
    public void setRefreshViewStatus(Refreshtatus L){
        if(getHeaderViewsCount()>0){
            mHeaderViewHoder.mRefresh.setVisibility(View.GONE);
            mHeaderViewHoder.mFail.setVisibility(View.GONE);
            mHeaderViewHoder.mFinish.setVisibility(View.GONE);
                switch (L){
                    case LOAD:
                        mHeaderViewHoder.mRefresh.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        mHeaderViewHoder.mFail.setVisibility(View.VISIBLE);

                        break;
                    case FHINSH:
                        mHeaderViewHoder.mFinish.setVisibility(View.VISIBLE);


                        break;
                }
        }


    }
    enum Refreshtatus {
        LOAD,ERROR,FHINSH
    }
}
/**
*******分析
 * 主要为了上啦加载
 * 通过在最后面加载一个loadView 提示
 *  getItemViewType（int position） 根据条件 返回不同ViewType
 *  onCreateViewHolder(ViewGroup parent, int viewType)  根据以上方法传递的ViewType 创建ViewHoder
 *  onBindViewHolder(BaseViewHolder holder, int position)  ViewHoder instanceof  来判断不同item类型设置对应数据和对应的操作
 *  最后通过setLoadViewStatus(LoadStatus L) 设置LoadView的状态
 *  BaseAdapter将公共的方法封装 ，涉及逻辑or个性化的交由子类操作
 *SimpleOnItemTouchListener
 */
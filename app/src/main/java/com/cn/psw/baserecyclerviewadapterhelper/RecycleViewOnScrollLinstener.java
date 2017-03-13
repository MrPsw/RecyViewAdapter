package com.cn.psw.baserecyclerviewadapterhelper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Mr.peng on 2017/2/22.
 */

public abstract class RecycleViewOnScrollLinstener extends RecyclerView.OnScrollListener{



   boolean isUp=false;
    boolean ispulldown=false;


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int lastPosition=-1;
       int firstPosition=-1;
        if(newState== RecyclerView.SCROLL_STATE_IDLE){
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if(layoutManager instanceof GridLayoutManager){
                //通过LayoutManager找到当前显示的最后的item的position
                lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }else if(layoutManager instanceof LinearLayoutManager){
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }else if(layoutManager instanceof StaggeredGridLayoutManager){
                //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                lastPosition = findMax(lastPositions);
            }

            //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
            //如果相等则说明已经滑动到最后了
             int num= recyclerView.getLayoutManager().getItemCount();
            if(lastPosition == num-1&&isUp){
                    LoadData();
            }else if(firstPosition==0&&ispulldown){
                Refresh();
            }

        }



    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(dy>0){
            isUp=true;
        }else {
        ispulldown=true;
        }
    }

    //找到数组中的最大值
    public int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
 public  abstract void LoadData();
 public  abstract void Refresh();
}

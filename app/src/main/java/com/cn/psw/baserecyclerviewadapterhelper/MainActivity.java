package com.cn.psw.baserecyclerviewadapterhelper;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.cn.psw.baserecyclerviewadapterhelper.Adapter.RecyViewPswadapter;
import com.cn.psw.baserecyclerviewadapterhelper.Base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.cn.psw.baserecyclerviewadapterhelper.RecyCleViewAdapter.Refreshtatus.LOAD;

public class MainActivity extends AppCompatActivity {

    private RecyViewPswadapter adapter;
    // private RecycleViewAdapter adapter;


    int code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recycleview = (RecyclerView) findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i + "111");
        }

        adapter = new RecyViewPswadapter(MainActivity.this, list);
        recycleview.setAdapter(adapter);


        recycleview.addOnScrollListener(new RecycleViewOnScrollLinstener() {
            @Override
            public void LoadData() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(),"加载",Toast.LENGTH_LONG).show();


                        try {
                            adapter.setLoadViewStatus(RecyCleViewAdapter.LoadStatus.LOAD);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            //模拟加载
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (code == 1) {
                                        for (int i = 4; i < 10; i++) {
                                            list.add(i + "111");
                                        }
                                        adapter.notifyDataSetChanged();
                                        code = 2;
                                    } else if (code == 2) {
                                        adapter.setLoadViewStatus(RecyCleViewAdapter.LoadStatus.ERROR);
                                        code = 3;
                                    } else {
                                        adapter.setLoadViewStatus(RecyCleViewAdapter.LoadStatus.PERIOD);
                                    }
                                }
                            }, 1000);
                        }



                    }
                });


            }

            @Override
            public void Refresh() {
                Toast.makeText(getApplicationContext(),"刷新",Toast.LENGTH_LONG).show();
                adapter.setRefreshViewStatus(LOAD);
            }
        });

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}

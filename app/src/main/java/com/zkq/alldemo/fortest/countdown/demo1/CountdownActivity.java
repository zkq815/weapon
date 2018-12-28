package com.zkq.alldemo.fortest.countdown.demo1;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.weapon.base.BaseActivity;

import java.util.ArrayList;

/**
 * @author zkq
 * create:2018/12/18 2:42 PM
 * email:zkq815@126.com
 * desc: 倒计时展示
 */
public class CountdownActivity extends BaseActivity {

    private ArrayList<CountBean> testData;
    private int now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        testData = getTest();
        now = (int) (System.currentTimeMillis()/1000);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        //设置布局管理器
        list.setLayoutManager(new LinearLayoutManager(this));
        //设置Adapter
        list.setAdapter(new MyRecyclerAdapter());

    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getBaseContext()).inflate(
                            R.layout.item_time_test,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(testData.get(position).getState()==0 && holder.tv.isGoing()){//初次进入
                holder.tv.setCountdownTime((int)testData.get(position).getTimes(),CountdownActivity.class.getSimpleName()+"_|_"+holder.tv.toString());
//                holder.tv.setCountdownTime((int)testData.get(position).getTimes(),position+"");
            }else{//暂停或停止状态
                holder.tv.setText(""+testData.get(position).getTimes());
            }
            holder.tv2.setText("总倒计时"+testData.get(position).getTimes()+":");
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.tv.isGoing()){
                        holder.tv.pauseTimer();
                    }else{
                        holder.tv.goonTimer();
                    }
                    holder.tv.setGoing(!holder.tv.isGoing());
                    testData.get(position).setState(1);
                }
            });

            holder.tv.setOnTimeListener(new CountdownTime.OnTimeListener() {
                @Override
                public void onTick(int time) {
//                    ZLog.e("activity剩余时间=="+time);
                }

                @Override
                public void onTimeOver(String id) {
//                    ZLog.e("activity结束id=="+time);
                    testData.get(position).setState(1);
                }
            });
        }

        @Override
        public int getItemCount() {
            return testData.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder{
            CountdownView tv;
            TextView tv2;
            MyViewHolder(View itemView) {
                super(itemView);
                tv = (CountdownView) itemView.findViewById(R.id.countdownView);

                tv2 = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountdownTimeQueueManager.getInstance().removePageTime(CountdownActivity.class.getSimpleName());
    }

    private ArrayList<CountBean> getTest(){
        ArrayList<CountBean> testList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CountBean bean = new CountBean();
            bean.setTimes(100);
            bean.setState(0);
            testList.add(bean);
        }
        return testList;
    }

}

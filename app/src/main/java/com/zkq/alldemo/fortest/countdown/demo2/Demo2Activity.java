package com.zkq.alldemo.fortest.countdown.demo2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.countdown.CountdownBean;
import com.zkq.alldemo.fortest.countdown.OnTimerListener;
import com.zkq.weapon.base.BaseActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author zkq
 * create:2019/5/28 12:58 AM
 * email:zkq815@126.com
 * desc:
 */
public class Demo2Activity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rvList;
    @BindView(R.id.v_countdown)
    CustomCountDown vCountdown;

    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_goon)
    Button btnGoon;

    private ArrayList<CountdownBean> dataList;
    private String[] color = {"#5900ffff","#9370DB","#7B68EE"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        ButterKnife.bind(this);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new Adapter(getDataList()));
//        TimerViewUtils.getInstance()
//                .getCustomCountDown(vCountdown,getBean2(),listener)
//                .start();
        vCountdown.setCountdownBean(getBean2()).setOnTimerListener(listener).start();
        vCountdown.setTextColor("#ffffff")
                .setRad(0);
        btnPause.setOnClickListener(v->vCountdown.pause());
        btnGoon.setOnClickListener(v->vCountdown.goon());
//        //年、日、时、分、秒、毫秒 分割符:
//        TimerUtils.getInstance()
//                .getTimerWithBean(tv1, 50000, getBean1(),listener)
//                .startTimer();
//        //年、日、时、分、秒、毫秒，分割符 单位
//        TimerUtils.getInstance()
//                .getTimerWithBean(tv2, 50000, getBean2(),listener)
//                .startTimer();
//        //时、分、秒、毫秒，分割符 单位
//        TimerUtils.getInstance()
//                .getTimerWithBean(tv3, 50000, getBean3(),listener)
//                .startTimer();
//        //时、分、秒，分割符 单位
//        TimerUtils.getInstance()
//                .getTimerWithBean(tv4, 50000, getBean4(),listener)
//                .startTimer();
//        //日、分、秒、毫秒，分割符 单位
//        TimerUtils.getInstance()
//                .getTimerWithBean(tv5, 50000, getBean5(),listener)
//                .startTimer();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ItemHolder>{
        private ArrayList<CountdownBean> mList;

        public Adapter(ArrayList<CountdownBean> list) {
            this.mList = list;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_time_test,parent,false));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            if(dataList.get(position) instanceof CountdownBean){
                holder.llContent.setBackgroundColor(Color.parseColor(color[position%3]));
                holder.btnPause.setText("暂停");
                holder.btnGoon.setText("继续");
                holder.btnRestart.setText("重开");
                if(holder.isFirst){
                    holder.customCountDown.setCountdownBean(dataList.get(position)).setOnTimerListener(listener).start();
                    holder.isFirst = false;
                }
                holder.btnPause.setOnClickListener(v-> holder.customCountDown.pause());
                holder.btnGoon.setOnClickListener(v-> holder.customCountDown.goon());
                holder.btnRestart.setOnClickListener(v-> holder.customCountDown.restart());
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ItemHolder extends RecyclerView.ViewHolder{
            boolean isFirst = true;
            LinearLayout llContent;
            CustomCountDown customCountDown;
            Button btnPause, btnGoon, btnRestart;
            public ItemHolder(View itemView) {
                super(itemView);
                llContent = (LinearLayout) itemView.findViewById(R.id.ll_content);
                customCountDown = (CustomCountDown) itemView.findViewById(R.id.custom);
                btnPause = (Button) itemView.findViewById(R.id.btn_pause);
                btnGoon = (Button) itemView.findViewById(R.id.btn_goon);
                btnRestart = (Button) itemView.findViewById(R.id.btn_restart);
                setIsRecyclable(false);
            }
        }
    }


    private ArrayList<CountdownBean> getDataList(){
        dataList = new ArrayList<>();
        dataList.add(getBean1());
        dataList.add(getBean2());
        dataList.add(getBean3());
        dataList.add(getBean4());
        dataList.add(getBean5());
        dataList.add(getBean6());
        dataList.add(getBean7());
        dataList.add(getBean8());
        dataList.add(getBean9());
        dataList.add(getBean10());
        dataList.add(getBean11());
        dataList.add(getBean12());
        dataList.add(getBean13());
        return dataList;
    }


    private CountdownBean getBean1(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(44);
        bean.setSplit(":");
        bean.setTextColor("#7BAFD4");
        return bean;
    }

    private CountdownBean getBean2(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setFill(true);
        bean.setStrokeWidth(10);
        bean.setRad(20);
        bean.setTimes(15000);
        bean.setInterval(44);
        bean.setSplit("&");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ff00ff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean3(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(300);
        bean.setSplit("-");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#fff68f");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean4(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(200);
        bean.setSplit("*");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean5(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(false);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setTimes(15000);
        bean.setInterval(140);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ff0000");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean6(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setTimes(15000);
        bean.setInterval(240);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean7(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(90);
        bean.setSplit("-");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean8(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(100);
        bean.setSplit("*");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean9(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setTimes(15000);
        bean.setInterval(88);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#000080");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean10(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(32);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#123957");
        bean.setSplitColor("#857321");
        return bean;
    }

    private CountdownBean getBean11(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setTimes(15000);
        bean.setInterval(40);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#000000");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean12(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(40);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#1E90FF");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean13(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(false);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setTimes(15000);
        bean.setInterval(40);
        bean.setSplit("￥");
        bean.setTextColor("#ff00ff");
        bean.setBgColor("#00FA9A");
        bean.setSplitColor("#546718");
        return bean;
    }

    private OnTimerListener listener = new OnTimerListener() {
        @Override
        public void onEveryTime(long time, String id) {
//            ZLog.e("回调id=="+id);
        }

        @Override
        public void onTimeOver(String id) {
//            ZLog.e("结束id=="+id);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.zkq.alldemo.fortest.countdown.demo1;

import android.os.Bundle;
import android.widget.TextView;

import com.zkq.alldemo.R;
import com.zkq.alldemo.fortest.countdown.CountdownBean;
import com.zkq.alldemo.fortest.countdown.OnTimerListener;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.market.util.ZLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zkq
 * create:2018/12/18 2:42 PM
 * email:zkq815@126.com
 * desc: 倒计时展示
 */
public class CountdownActivity extends BaseActivity {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;
    @BindView(R.id.tv13)
    TextView tv13;

    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        ButterKnife.bind(this);
        //年、日、时、分、秒、毫秒 分割符:
        TimerUtils.getInstance()
                .getTimerWithBean(tv1, 50000, getBean1(),listener)
                .startTimer();
        //年、日、时、分、秒、毫秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(tv2, 50000, getBean2(),listener)
                .startTimer();
        //时、分、秒、毫秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(tv3, 50000, getBean3(),listener)
                .startTimer();
        //时、分、秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(tv4, 50000, getBean4(),listener)
                .startTimer();
        //日、分、秒、毫秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(tv5, 50000, getBean5(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv6, 50000, getBean6(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv7, 50000, getBean7(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv8, 50000, getBean8(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv9, 50000, getBean9(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv10, 50000, getBean10(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv11, 50000, getBean11(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv12, 50000, getBean12(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(tv13, 50000, getBean13(),listener)
                .startTimer();


        tv13.setOnClickListener(v-> {
            i++;
            ZLog.e("i==="+i%4);
            if(i%4==1){
                TimerUtils.getInstance().cancelById(String.valueOf(tv13.getId()));
            }else if(i%4==2){
                TimerUtils.getInstance().reStartCountDownById(String.valueOf(tv13.getId()));
            }else if(i%4==3){
                TimerUtils.getInstance().cancelById(String.valueOf(tv13.getId()));
            }else{
                TimerUtils.getInstance().continueCountDownById(String.valueOf(tv13.getId()));
            } });
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
        bean.setInterval(500);
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
        bean.setSplit("-");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ff00ff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean3(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(false);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setInterval(300);
        bean.setSplit("￥");
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
        bean.setShowUnit(true);
        bean.setInterval(200);
        bean.setSplit("￥");
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
        bean.setInterval(140);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ff0000");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean6(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setInterval(240);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean7(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setInterval(90);
        bean.setSplit("-");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean8(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(false);
        bean.setInterval(100);
        bean.setSplit("*");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean9(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setInterval(88);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#000080");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean10(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setInterval(32);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#123957");
        bean.setSplitColor("#857321");
        return bean;
    }

    private CountdownBean getBean11(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setInterval(40);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#000000");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean12(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setInterval(40);
        bean.setSplit("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#1E90FF");
        bean.setSplitColor("#546718");
        return bean;
    }

    private CountdownBean getBean13(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
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
        TimerUtils.getInstance().cancelById(String.valueOf(tv1.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv2.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv3.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv4.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv5.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv6.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv7.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv8.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv9.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv10.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv11.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv12.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(tv13.getId()));
    }

}

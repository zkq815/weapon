package com.zkq.alldemo.fortest.countdown.demo2;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.zkq.alldemo.R;
import com.zkq.alldemo.databinding.ActivityDemo2Binding;
import com.zkq.weapon.base.BaseActivity;
import com.zkq.weapon.market.util.ZLog;

public class Demo2Activity extends BaseActivity {

    private ActivityDemo2Binding mBinding;
    private LinearLayout parent;
    private int padding =10;
    private int textSize = 40;
    int i= 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_demo2);
        parent = (LinearLayout) findViewById(R.id.parent);

        //年、日、时、分、秒、毫秒 分割符:
        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv1, 50000, getBean1(),listener)
                .startTimer();
        //年、日、时、分、秒、毫秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv2, 50000, getBean2(),listener)
                .startTimer();
        //时、分、秒、毫秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv3, 50000, getBean3(),listener)
                .startTimer();
        //时、分、秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv4, 50000, getBean4(),listener)
                .startTimer();
        //日、分、秒、毫秒，分割符 单位
        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv5, 50000, getBean5(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv6, 50000, getBean6(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv7, 50000, getBean7(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv8, 50000, getBean8(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv9, 50000, getBean9(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv10, 50000, getBean10(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv11, 50000, getBean11(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv12, 50000, getBean12(),listener)
                .startTimer();

        TimerUtils.getInstance()
                .getTimerWithBean(mBinding.tv13, 50000, getBean13(),listener)
                .startTimer();


        mBinding.tv13.setOnClickListener(v-> {
            i++;
            ZLog.e("i==="+i%4);
            if(i%4==1){
                TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv13.getId()));
            }else if(i%4==2){
                TimerUtils.getInstance().reStartCountDownById(String.valueOf(mBinding.tv13.getId()));
            }else if(i%4==3){
                TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv13.getId()));
            }else{
                TimerUtils.getInstance().continueCountDownById(String.valueOf(mBinding.tv13.getId()));
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(500);
        bean.setConnection(":");
        bean.setTextColor("#7BAFD4");
        return bean;
    }

    private CountdownBean getBean2(){
        CountdownBean bean = new CountdownBean();
        bean.setShowYear(true);
        bean.setShowDay(true);
        bean.setShowHour(true);
        bean.setShowMinutes(true);
        bean.setShowSecond(true);
        bean.setShowMillisecond(true);
        bean.setShowUnit(true);
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(400);
        bean.setConnection(":");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ff00ff");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(300);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#fff68f");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(200);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(140);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ff0000");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(240);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(90);
        bean.setConnection("-");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(100);
        bean.setConnection("*");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#ffffff");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(88);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#000080");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(32);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#123957");
        bean.setConnectColor("#857321");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(40);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#000000");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(40);
        bean.setConnection("￥");
        bean.setTextColor("#7BAFD4");
        bean.setBgColor("#1E90FF");
        bean.setConnectColor("#546718");
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
        bean.setBgPicUrl("http://imcut.jollychic.com/uploads/jollyimg/imageMaterialLib/201812/23/IL201812231844256834.jpg");
        bean.setInterval(40);
        bean.setConnection("￥");
        bean.setTextColor("#ff00ff");
        bean.setBgColor("#00FA9A");
        bean.setConnectColor("#546718");
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
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv1.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv2.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv3.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv4.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv5.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv6.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv7.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv8.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv9.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv10.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv11.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv12.getId()));
        TimerUtils.getInstance().cancelById(String.valueOf(mBinding.tv13.getId()));
    }
}

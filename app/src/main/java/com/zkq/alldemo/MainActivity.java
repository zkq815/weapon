package com.zkq.alldemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkq.alldemo.base.BaseActivity;


public class MainActivity extends BaseActivity {
    String[] info = {"CatchException"};
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MainAdapter(this));
    }

    class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        Context context;
        LayoutInflater layoutInflater;
        MainAdapter(Context context){
            this.context = context;
            this.layoutInflater = LayoutInflater.from(this.context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(layoutInflater.inflate(R.layout.main_item_viewholder,parent,false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ItemHolder itemHolder = null;
            if(holder instanceof ItemHolder){
                itemHolder = (ItemHolder) holder;
                itemHolder.tvAcivityName.setText(info[position]);
                itemHolder.tvAcivityName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            startActivity(new Intent(MainActivity.this,Class.forName(info[position])));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return info.length;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView tvAcivityName;
        ItemHolder(View view){
            super(view);
            tvAcivityName = (TextView) view.findViewById(R.id.tv_activity);
        }
    }

}

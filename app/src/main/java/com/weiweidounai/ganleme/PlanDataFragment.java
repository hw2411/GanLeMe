package com.weiweidounai.ganleme;

/**
 * Created by haha on 2016/3/2.
 */
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PlanDataFragment extends Fragment implements View.OnClickListener {

    private Context content;
    private PlanAdapter mAdapter = null;
    private List<PlanData> mData = null;
    private int flag = 1;
    private String plans="跑步,阅读,行走,学习";
     public PlanDataFragment(){
    };
    @SuppressLint("ValidFragment")
    public PlanDataFragment(Context content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_view,container,false);
        ListView list_plan_set = (ListView) view.findViewById(R.id.list_plan_set);
        Button add_plan = (Button) view.findViewById(R.id.btn_add_plan);
        mData = new LinkedList<PlanData>();
        mAdapter = new PlanAdapter((LinkedList<PlanData>) mData,content);
        list_plan_set.setAdapter(mAdapter);
        add_plan.setOnClickListener(this);
        add_plan.setVisibility(View.GONE);
        String[] plan = plans.split(",");
        int[] imgs = {R.mipmap.run,R.mipmap.reed,R.mipmap.walk,R.mipmap.study};
        FileHelper fileHelper=new FileHelper(content);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<plan.length;i++){
            mAdapter.add(new PlanData(imgs[i],plan[i],fileHelper.getPanComplete(i,df.format(new Date()))));
        }

        return view;
    }

    @Override
    public void onClick(View v) {

        flag++;
    }
}

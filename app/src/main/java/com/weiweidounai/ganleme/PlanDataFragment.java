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
        reload();

        return view;
    }

    @Override
    public void onClick(View v) {

        flag++;
    }

    public void reload(){
        int[] imgs = {R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e,R.mipmap.f};
        FileHelper fileHelper=new FileHelper(content);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String[] plans = fileHelper.getFileStrings("plansInSet");
        mAdapter.clear();
        if(plans!=null&&!"".equals(plans[0])){
            for(int i=0;i<plans.length;i++){
                mAdapter.add(new PlanData(imgs[(plans.length-1-i)%6],plans[i],0));
            }
        }
    }
}

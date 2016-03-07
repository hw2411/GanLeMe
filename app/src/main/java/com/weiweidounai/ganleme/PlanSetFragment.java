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
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import android.widget.Toast;

public class PlanSetFragment extends Fragment implements View.OnClickListener {

    private Context content;
    private PlanAdapter mAdapter = null;
    private List<PlanType> mData = null;
    private int flag = 1;
    public PlanSetFragment(){
    };
    @SuppressLint("ValidFragment")
    public PlanSetFragment(Context content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_set,container,false);
        ListView list_plan_set = (ListView) view.findViewById(R.id.list_plan_set);
        Button add_plan = (Button) view.findViewById(R.id.btn_add_plan);
        mData = new LinkedList<PlanType>();
        mAdapter = new PlanAdapter((LinkedList<PlanType>) mData,content);
        list_plan_set.setAdapter(mAdapter);
        add_plan.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        mAdapter.add(new PlanType(R.mipmap.ic_icon_qitao,"Ï°¹ß£º " + flag));
//        Toast.makeText(content, "i am in", Toast.LENGTH_SHORT).show();
        flag++;
    }
}

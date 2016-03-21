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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PlanSetFragment extends Fragment implements View.OnClickListener {

    private Context content;
    EditText input_planview;
    TextView plansview;
    FileHelper fileHelper=null;

    public PlanSetFragment(){
    };
    @SuppressLint("ValidFragment")
    public PlanSetFragment(Context content) {

        this.content = content;
        this.fileHelper = new FileHelper(content);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_set_view,container,false);
        Button plan_add = (Button) view.findViewById(R.id.btn_plan_add);
        Button plan_del = (Button) view.findViewById(R.id.btn_plan_del);
        plansview = (TextView) view.findViewById(R.id.textSetPlans);
        input_planview = (EditText) view.findViewById(R.id.edit_plan_input);
        plansview.setText("没有保存习惯");
        plan_add.setOnClickListener(this);
        plan_del.setOnClickListener(this);
        try{
            plansview.setText(fileHelper.read("plansInSet"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_plan_add:
                if(!input_planview.getText().toString().equals("")&&fileHelper.getPlanSize("plansInSet")<6){
                    try {
                        fileHelper.savePlans("plansInSet", input_planview.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(fileHelper.getPlanSize("plansInSet")>=6){
                    Toast.makeText(content, "习惯总数不能超过6个", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_plan_del:
                if(!input_planview.getText().toString().equals("")&&fileHelper.getPlanSize("plansInSet")>0){
                    fileHelper.deletePlan("plansInSet",input_planview.getText().toString());
                }else{
                    Toast.makeText(content, "习惯总数为零，不能删除了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        try{
            plansview.setText(fileHelper.read("plansInSet"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

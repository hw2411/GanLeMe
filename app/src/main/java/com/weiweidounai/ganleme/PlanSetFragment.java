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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PlanSetFragment extends Fragment implements View.OnClickListener {

    private Context content;
    EditText input_planview;
    TextView plansview;

     public PlanSetFragment(){
    };
    @SuppressLint("ValidFragment")
    public PlanSetFragment(Context content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_set_view,container,false);
        Button plan_add = (Button) view.findViewById(R.id.btn_plan_add);
        plansview = (TextView) view.findViewById(R.id.textSetPlans);
        input_planview = (EditText) view.findViewById(R.id.edit_plan_input);
        FileHelper fileHelper=new FileHelper(content);
        plansview.setText("");
        plan_add.setOnClickListener(this);
        try{
            plansview.setText(fileHelper.read("plansInSet"));
        }catch (Exception e){

        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if(!input_planview.getText().toString().equals("")){
            FileHelper fileHelper=new FileHelper(content);
            plansview.setText(plansview.getText().toString()+input_planview.getText().toString()+"\n");
            try {
                fileHelper.save("plansInSet",plansview.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

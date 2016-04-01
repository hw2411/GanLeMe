package com.weiweidounai.ganleme;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by haha on 2016/3/28.
 */
public class StatisticsFragment extends Fragment {
    private Context content;
    public HistogramView histogramView;
    FileHelper fileHelper=null;
    public StatisticsFragment(){

    };
    @SuppressLint("ValidFragment")
    public StatisticsFragment(Context content,int stype) {
        this.content = content;
        this.fileHelper = new FileHelper(content);
        this.histogramView=new HistogramView(content,stype);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.histogramView;
    }
}

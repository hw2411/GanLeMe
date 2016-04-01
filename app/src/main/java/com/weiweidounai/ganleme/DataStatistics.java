package com.weiweidounai.ganleme;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haha on 2016/3/31.
 */
public class DataStatistics {
    private Context mContext;
    FileHelper fileHelper;
    public DataStatistics(Context mContext) {
        super();
        this.mContext = mContext;
        fileHelper = new FileHelper(mContext);
    }

    public ArrayList<Integer> StatisticsComplete(ArrayList<String> countdays){
        ArrayList<Integer> re =new  ArrayList<Integer>();
        String[] plans = fileHelper.getFileStrings("plansInSet");
        int dayshavedata =0;
        Map<String,Integer> datamap = new HashMap<String, Integer>();
        if(plans==null||"".equals(plans[0]))
            return re;
        for(int i=0;i<plans.length;i++){
            datamap.put(plans[i],0);
        }
        for(String dayincount:countdays){
            String[] datas=fileHelper.getFileStrings(dayincount);
            if(datas!=null){
                dayshavedata++;
                for(int j=0;j<datas.length;j++){
                    String[] value = datas[j].split(",");
                    if(datamap.containsKey(value[0]))
                    datamap.put(value[0],datamap.get(value[0])+Integer.parseInt(value[1]));
                }
            }
        }
        if(dayshavedata==0){
            for(int i=0;i<plans.length;i++){
                re.add(0);
            }
        }else {
            for(int i=0;i<plans.length;i++){
                re.add((int)(datamap.get(plans[i])/dayshavedata));
            }
        }
        return re;
    }





}

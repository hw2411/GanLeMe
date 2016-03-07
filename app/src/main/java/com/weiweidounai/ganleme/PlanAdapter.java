package com.weiweidounai.ganleme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by haha on 2016/3/7.
 */
public class PlanAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<PlanType> mData;

    public PlanAdapter() {
    }

    public PlanAdapter(LinkedList<PlanType> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_plan_set, parent, false);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.text_plan_type = (TextView) convertView.findViewById(R.id.txt_type);
            holder.sb_plan_complete = (SeekBar) convertView.findViewById(R.id.sb_plan_complete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img_icon.setImageResource(mData.get(position).getImgId());
        holder.text_plan_type.setText(mData.get(position).getPlanType());
        Sbarlistener sblistener = new Sbarlistener(null,position);
        holder.sb_plan_complete.setOnSeekBarChangeListener(sblistener);
        return convertView;
    }

    //添加一个元素
    public void add(PlanType data) {
        if (mData == null) {
            mData = new LinkedList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position,PlanType data){
        if (mData == null) {
            mData = new LinkedList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(PlanType data) {
        if(mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if(mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if(mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }





    private class ViewHolder {
        ImageView img_icon;
        TextView text_plan_type;
        SeekBar sb_plan_complete;
    }
    private class Sbarlistener implements SeekBar.OnSeekBarChangeListener {
        TextView tempview;
        int temppos;
        int p_val;
        public Sbarlistener(TextView tv,int pos){
            tempview=tv;
            temppos=pos;
            p_val=1;
        }
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            p_val=progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Toast.makeText(mContext, "release SeekBar:pos:"+temppos+",value:"+p_val, Toast.LENGTH_SHORT).show();
        }
    }
}

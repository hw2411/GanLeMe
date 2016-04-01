package com.weiweidounai.ganleme;

/**
 * Created by haha on 2016/3/31.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DaysCount {



    public final int FIRST_DAY = Calendar.MONDAY;

    public void printWeekdays() {
        Calendar calendar = Calendar.getInstance();
        setToWeekFirstDay(calendar);
        for (int i = 0; i < 7; i++) {
            printDay(calendar);
            calendar.add(Calendar.DATE, 1);
        }
    }

    public void setToWeekFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    public void printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(calendar.getTime()));
    }

    public ArrayList<String> getMonthdays(){
        ArrayList<String> re=new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
        int days0 = Integer.parseInt(dateFormat2.format(calendar.getTime()).substring(0,6)+"00");
        while (Integer.parseInt(dateFormat2.format(calendar.getTime()))>days0) {
            re.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, -1);
        }
        return re;
    }

    public ArrayList<String> getWeekdays(){
        ArrayList<String> re=new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            re.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, -1);
        }
        re.add(dateFormat.format(calendar.getTime()));
        return re;
    }

}

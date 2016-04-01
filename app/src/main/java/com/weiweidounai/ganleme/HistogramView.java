package com.weiweidounai.ganleme;

/**
 * Created by haha on 2016/3/28.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;

public class HistogramView extends View {

    private Paint xLinePaint;// 坐标轴 轴线 画笔：
    private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
    private Paint titlePaint;// 绘制文本的画笔
    private Paint paint;// 矩形画笔 柱状图的样式信息
    ArrayList<Integer> progress ;
    // 条，显示各个柱状的数据
    ArrayList<Integer> aniProgress;// 实现动画的值
    private final int TRUE = 1;// 在柱状图上显示数字
    ArrayList<Integer> text;// 设置点击事件，显示哪一条柱状的信息
    private Bitmap bitmap;
    // 坐标轴左侧的数标
    private String[] ySteps;
    // 坐标轴底部的习惯
    private String[] xPlans;
    private int flag;// 是否使用动画
    FileHelper fileHelper;
    DataStatistics dataStatistics;
    int sttpye=0;

    private HistogramAnimation ani;

    public HistogramView(Context context,int stype) {
        super(context);
        fileHelper=new FileHelper(context);
        dataStatistics=new DataStatistics(context);
        this.sttpye=stype;
        init();
        start(2);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        ySteps = new String[] { "100%", "75%", "50%", "25%", "0%" };
        xPlans = fileHelper.getFileStrings("plansInSet");
        aniProgress = new ArrayList<Integer>();
        text = new ArrayList<Integer>();
        if(this.sttpye==0)
            progress=dataStatistics.StatisticsComplete(new DaysCount().getWeekdays());
        else
            progress=dataStatistics.StatisticsComplete(new DaysCount().getMonthdays());
        aniProgress.clear();
        text.clear();
        for (int i = 0; i < progress.size(); i++) {
            aniProgress.add(0) ;
            text.add(0) ;
        }

        ani = new HistogramAnimation();
        ani.setDuration(1000);

        xLinePaint = new Paint();
        hLinePaint = new Paint();
        titlePaint = new Paint();
        paint = new Paint();

        // 给画笔设置颜色
        xLinePaint.setColor(Color.DKGRAY);
        hLinePaint.setColor(Color.LTGRAY);
        titlePaint.setColor(Color.BLACK);

        // 加载画图
        bitmap = BitmapFactory
                .decodeResource(getResources(), R.mipmap.blue);
    }

    public void start(int flag) {
        xPlans = fileHelper.getFileStrings("plansInSet");
        if(this.sttpye==0)
            progress=dataStatistics.StatisticsComplete(new DaysCount().getWeekdays());
        else
            progress=dataStatistics.StatisticsComplete(new DaysCount().getMonthdays());
        aniProgress.clear();
        text.clear();
        for (int i = 0; i < progress.size(); i++) {
            aniProgress.add(0) ;
            text.add(0) ;
        }
        this.flag = flag;
        this.startAnimation(ani);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight() - dp2px(50);
        // 绘制底部的线条
        canvas.drawLine(dp2px(30), height + dp2px(3), width - dp2px(30), height
                + dp2px(3), xLinePaint);

        int leftHeight = height - dp2px(5);// 左侧外周的 需要划分的高度：

        int hPerHeight = leftHeight / 4;// 分成四部分

        hLinePaint.setTextAlign(Align.CENTER);
        // 设置四条虚线
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(dp2px(30), dp2px(10) + i * hPerHeight, width
                    - dp2px(30), dp2px(10) + i * hPerHeight, hLinePaint);
        }

        // 绘制 Y 周坐标
        titlePaint.setTextAlign(Align.RIGHT);
        titlePaint.setTextSize(sp2px(12));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);
        // 设置左部的数字
        for (int i = 0; i < ySteps.length; i++) {
            canvas.drawText(ySteps[i], dp2px(25), dp2px(13) + i * hPerHeight,
                    titlePaint);
        }

        // 绘制 X 周 做坐标
        int xAxisLength = width - dp2px(30);
        if(xPlans!=null&&!"".equals(xPlans[0])){
            int columCount = xPlans.length + 1;
            int step = xAxisLength / columCount;

            // 设置底部的数字
            for (int i = 0; i < columCount - 1; i++) {
                // text, baseX, baseY, textPaint
                canvas.drawText(xPlans[i], dp2px(25) + step * (i + 1), height
                        + dp2px(20), titlePaint);
            }
            // 绘制矩形
            if (aniProgress != null && aniProgress.size() > 0) {
                for (int i = 0; i < aniProgress.size(); i++) {// 循环遍历将柱状图形画出来
                    int value = aniProgress.get(i);
                    paint.setAntiAlias(true);// 抗锯齿效果
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(sp2px(15));// 字体大小
                    paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
                    Rect rect = new Rect();// 柱状图的形状

                    rect.left = step * (i + 1);
                    rect.right = dp2px(30) + step * (i + 1);
                    int rh = (int) (leftHeight - leftHeight * (value / 100.0));
                    rect.top = rh + dp2px(10);
                    rect.bottom = height;

                    canvas.drawBitmap(bitmap, null, rect, paint);
                    // 是否显示柱状图上方的数字
                    if (this.text.get(i) == TRUE) {
                        canvas.drawText(value + "%", dp2px(15) + step * (i + 1)
                                - dp2px(15), rh + dp2px(5), paint);
                    }

                }
            }
        }



    }

    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

    /**
     * 设置点击事件，是否显示数字
     */
    public boolean onTouchEvent(MotionEvent event) {
        int step = (getWidth() - dp2px(30)) / (text.size()+1);
        int x = (int) event.getX();
        for (int i = 0; i < text.size(); i++) {
            if (x > (dp2px(15) + step * (i + 1) - dp2px(15))
                    && x < (dp2px(15) + step * (i + 1) + dp2px(15))) {
                text.set(i,1);
                for (int j = 0; j < text.size(); j++) {
                    if (i != j) {
                        text.set(j,0);
                    }
                }
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    invalidate();
                } else {
                    postInvalidate();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 集成animation的一个动画类
     *
     * @author 李垭超
     */
    private class HistogramAnimation extends Animation {
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f && flag == 2) {
                for (int i = 0; i < aniProgress.size(); i++) {
                    aniProgress.set(i,(int) (progress.get(i) * interpolatedTime))  ;
                }
            } else {
                for (int i = 0; i < aniProgress.size(); i++) {
                    aniProgress.set(i,progress.get(i)) ;
                }
            }
            invalidate();
        }
    }

}

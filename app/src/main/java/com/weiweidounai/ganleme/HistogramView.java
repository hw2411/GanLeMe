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

    private Paint xLinePaint;// ������ ���� ���ʣ�
    private Paint hLinePaint;// ������ˮƽ�ڲ� ���߻���
    private Paint titlePaint;// �����ı��Ļ���
    private Paint paint;// ���λ��� ��״ͼ����ʽ��Ϣ
    ArrayList<Integer> progress ;
    // ������ʾ������״������
    ArrayList<Integer> aniProgress;// ʵ�ֶ�����ֵ
    private final int TRUE = 1;// ����״ͼ����ʾ����
    ArrayList<Integer> text;// ���õ���¼�����ʾ��һ����״����Ϣ
    private Bitmap bitmap;
    // ��������������
    private String[] ySteps;
    // ������ײ���ϰ��
    private String[] xPlans;
    private int flag;// �Ƿ�ʹ�ö���
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

        // ������������ɫ
        xLinePaint.setColor(Color.DKGRAY);
        hLinePaint.setColor(Color.LTGRAY);
        titlePaint.setColor(Color.BLACK);

        // ���ػ�ͼ
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
        // ���Ƶײ�������
        canvas.drawLine(dp2px(30), height + dp2px(3), width - dp2px(30), height
                + dp2px(3), xLinePaint);

        int leftHeight = height - dp2px(5);// ������ܵ� ��Ҫ���ֵĸ߶ȣ�

        int hPerHeight = leftHeight / 4;// �ֳ��Ĳ���

        hLinePaint.setTextAlign(Align.CENTER);
        // ������������
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(dp2px(30), dp2px(10) + i * hPerHeight, width
                    - dp2px(30), dp2px(10) + i * hPerHeight, hLinePaint);
        }

        // ���� Y ������
        titlePaint.setTextAlign(Align.RIGHT);
        titlePaint.setTextSize(sp2px(12));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);
        // �����󲿵�����
        for (int i = 0; i < ySteps.length; i++) {
            canvas.drawText(ySteps[i], dp2px(25), dp2px(13) + i * hPerHeight,
                    titlePaint);
        }

        // ���� X �� ������
        int xAxisLength = width - dp2px(30);
        if(xPlans!=null&&!"".equals(xPlans[0])){
            int columCount = xPlans.length + 1;
            int step = xAxisLength / columCount;

            // ���õײ�������
            for (int i = 0; i < columCount - 1; i++) {
                // text, baseX, baseY, textPaint
                canvas.drawText(xPlans[i], dp2px(25) + step * (i + 1), height
                        + dp2px(20), titlePaint);
            }
            // ���ƾ���
            if (aniProgress != null && aniProgress.size() > 0) {
                for (int i = 0; i < aniProgress.size(); i++) {// ѭ����������״ͼ�λ�����
                    int value = aniProgress.get(i);
                    paint.setAntiAlias(true);// �����Ч��
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(sp2px(15));// �����С
                    paint.setColor(Color.parseColor("#6DCAEC"));// ������ɫ
                    Rect rect = new Rect();// ��״ͼ����״

                    rect.left = step * (i + 1);
                    rect.right = dp2px(30) + step * (i + 1);
                    int rh = (int) (leftHeight - leftHeight * (value / 100.0));
                    rect.top = rh + dp2px(10);
                    rect.bottom = height;

                    canvas.drawBitmap(bitmap, null, rect, paint);
                    // �Ƿ���ʾ��״ͼ�Ϸ�������
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
     * ���õ���¼����Ƿ���ʾ����
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
     * ����animation��һ��������
     *
     * @author ���볬
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

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

public class HistogramView extends View {

    private Paint xLinePaint;// ������ ���� ���ʣ�
    private Paint hLinePaint;// ������ˮƽ�ڲ� ���߻���
    private Paint titlePaint;// �����ı��Ļ���
    private Paint paint;// ���λ��� ��״ͼ����ʽ��Ϣ
    private int[] progress = { 1000, 2000, 3000, 4000, 5000, 8000, 10000 };// 7
    // ������ʾ������״������
    private int[] aniProgress;// ʵ�ֶ�����ֵ
    private final int TRUE = 1;// ����״ͼ����ʾ����
    private int[] text;// ���õ���¼�����ʾ��һ����״����Ϣ
    private Bitmap bitmap;
    // ��������������
    private String[] ySteps;
    // ������ײ���������
    private String[] xWeeks;
    private int flag;// �Ƿ�ʹ�ö���

    private HistogramAnimation ani;

    public HistogramView(Context context) {
        super(context);
        init();
        start(2);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        ySteps = new String[] { "10k", "7.5k", "5k", "2.5k", "0" };
        xWeeks = new String[] { "��һ", "�ܶ�", "����", "����", "����", "����", "����" };
        text = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        aniProgress = new int[] { 0, 0, 0, 0, 0, 0, 0 };;
        ani = new HistogramAnimation();
        ani.setDuration(2000);

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
        int columCount = xWeeks.length + 1;
        int step = xAxisLength / columCount;

        // ���õײ�������
        for (int i = 0; i < columCount - 1; i++) {
            // text, baseX, baseY, textPaint
            canvas.drawText(xWeeks[i], dp2px(25) + step * (i + 1), height
                    + dp2px(20), titlePaint);
        }

        // ���ƾ���
        if (aniProgress != null && aniProgress.length > 0) {
            for (int i = 0; i < aniProgress.length; i++) {// ѭ��������7����״ͼ�λ�����
                int value = aniProgress[i];
                paint.setAntiAlias(true);// �����Ч��
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(sp2px(15));// �����С
                paint.setColor(Color.parseColor("#6DCAEC"));// ������ɫ
                Rect rect = new Rect();// ��״ͼ����״

                rect.left = step * (i + 1);
                rect.right = dp2px(30) + step * (i + 1);
                int rh = (int) (leftHeight - leftHeight * (value / 10000.0));
                rect.top = rh + dp2px(10);
                rect.bottom = height;

                canvas.drawBitmap(bitmap, null, rect, paint);
                // �Ƿ���ʾ��״ͼ�Ϸ�������
                if (this.text[i] == TRUE) {
                    canvas.drawText(value + "", dp2px(15) + step * (i + 1)
                            - dp2px(15), rh + dp2px(5), paint);
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
        int step = (getWidth() - dp2px(30)) / 8;
        int x = (int) event.getX();
        for (int i = 0; i < 7; i++) {
            if (x > (dp2px(15) + step * (i + 1) - dp2px(15))
                    && x < (dp2px(15) + step * (i + 1) + dp2px(15))) {
                text[i] = 1;
                for (int j = 0; j < 7; j++) {
                    if (i != j) {
                        text[j] = 0;
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
                for (int i = 0; i < aniProgress.length; i++) {
                    aniProgress[i] = (int) (progress[i] * interpolatedTime);
                }
            } else {
                for (int i = 0; i < aniProgress.length; i++) {
                    aniProgress[i] = progress[i];
                }
            }
            invalidate();
        }
    }

}

package com.unipock.unipaytool.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.unipock.unipaytool.utils.LogUtils;


/**
 * 签名
 */
public class SignatureView extends View {

    // 记录是否绘制
    boolean isDraw = false;
    //画笔
    private Paint paint;
    //画布
    private Canvas cacheCanvas;
    //位图
    private Bitmap cacheBitmap;
    //图片保存路径
    private Path path;

    private ImageView ivShowSignature;

    public void setIvShowSignature(ImageView ivShowSignature) {
        this.ivShowSignature = ivShowSignature;
    }

    public ImageView getIvShowSignature() {
        return ivShowSignature;
    }

    //位图缓存
    public Bitmap getCacheBitmap() {
        if (!isDraw) {
            return null;
        }
        return cacheBitmap;
    }

    public SignatureView(Context context) {
        super(context);
        init();
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init() {

        //设置画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        path = new Path();
        //创建位图
        cacheBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        //用自定义位图构建画布
        cacheCanvas = new Canvas(cacheBitmap);
        //设置画布为白色
        cacheCanvas.drawColor(Color.WHITE);

    }

    /**
     * 清除画板，重置画笔
     */
    public void clear() {
        isDraw =false;
        if (cacheCanvas != null) {
            paint.setColor(Color.WHITE);
            cacheCanvas.drawPaint(paint);
            paint.setColor(Color.BLACK);
            cacheCanvas.drawColor(Color.WHITE);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
        canvas.drawPath(path, paint);


        // 获取bitmap 赋予签名控件
        if (ivShowSignature!=null) {
            ivShowSignature.setImageBitmap(cacheBitmap);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int curW = cacheBitmap != null ? cacheBitmap.getWidth() : 0;
        int curH = cacheBitmap != null ? cacheBitmap.getHeight() : 0;
        if (curW >= w && curH >= h) {
            return;
        }
        if (curW < w) curW = w;
        if (curH < h) curH = h;
        Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas();
        newCanvas.setBitmap(newBitmap);
        if (cacheBitmap != null) {
            newCanvas.drawBitmap(cacheBitmap, 0, 0, null);
        }
        cacheBitmap = newBitmap;
        cacheCanvas = newCanvas;
    }

    private float cur_x, cur_y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                cur_x = x;
                cur_y = y;
                path.moveTo(cur_x, cur_y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                path.quadTo(cur_x, cur_y, x, y);
                cur_x = x;
                cur_y = y;

                isDraw = true;
                LogUtils.e(getClass().getName(), "isDraw: " + isDraw);
                break;
            }
            case MotionEvent.ACTION_UP: {
                cacheCanvas.drawPath(path, paint);
                path.reset();

                break;
            }
        }

        invalidate();
        return true;
    }
}

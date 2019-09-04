package com.example.vasshayden_ce07;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.vasshayden_ce07.objects.CsvUtil;
import com.example.vasshayden_ce07.objects.HiddenTreasure;

import java.util.ArrayList;
import java.util.Random;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {

    private forResult listener;


    private Rect mDimensions;

    private Paint mBlankPaint;
    private Paint mTextPaint;
    private Paint mItemDotPaint;
    private Bitmap mBackground;
    private Bitmap mHole;

    private ArrayList<Point> mPoints;
    private  ArrayList<HiddenTreasure> mTreasures;
    private ArrayList<HiddenTreasure> mFoundTreasures;

    private boolean hasStarted = false;


    public DrawingSurface(Context context) {
        super(context);
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //interface methods

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(!hasStarted){
            storeDimensions(holder);
            mTreasures = CsvUtil.getData(getContext(), R.raw.items);
            setRandomPoints(mTreasures);
            hasStarted = true;

            if(getContext() instanceof forResult){
                listener = (forResult)getContext();
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        storeDimensions(holder);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onFinishInflate() {
        //set up paint elements / inializie objects
        super.onFinishInflate();
        setWillNotDraw(false);
        getHolder().addCallback(this);
        Resources res = getResources();

        mBackground = BitmapFactory.decodeResource(res, R.drawable.field);
        mHole = BitmapFactory.decodeResource(res, R.drawable.hole);

        mBlankPaint = new Paint();
        mItemDotPaint = new Paint();
        mItemDotPaint.setColor(Color.WHITE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(60.0f);

        mBlankPaint = new Paint();
        mBlankPaint.setColor(Color.BLACK);

        mPoints = new ArrayList<>();
        mFoundTreasures = new ArrayList<>();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBackground, null, mDimensions, mBlankPaint);

        // draws holes
        float offset = mHole.getWidth() / 2.0f;
        for(Point p: mPoints){
            canvas.drawBitmap(mHole, p.x - offset, p.y - offset, mBlankPaint);
        }

        //places dots for found items
        for(HiddenTreasure ht : mFoundTreasures){
            canvas.drawCircle(ht.getPoint().x, ht.getPoint().y, 15.0f, mItemDotPaint);
        }

        //set text
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        int positionX = (getWidth() / 2);
        int positionY = getHeight() - 2000;
        canvas.drawText("Items " +
                        mFoundTreasures.size() +
                        " / " + mTreasures.size(),
                positionX,
                positionY,
                mTextPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point touchDown = new Point((int)event.getX(), (int)event.getY());
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mPoints.add(touchDown);
        }

        //if tap is in range of the items point, it gets added to the found items array
        for (HiddenTreasure ht : mTreasures) {
            if (isInRange(ht.getPoint(), touchDown) && !mFoundTreasures.contains(ht)){
                mFoundTreasures.add(ht);
            }
        }

        //interface to pass items back
        listener.passFoundItems(mFoundTreasures);
        postInvalidate();

        return super.onTouchEvent(event);
    }

    private void storeDimensions(SurfaceHolder _holder){
        //lock the canvas to get an instace of it back
        Canvas canvas = _holder.lockCanvas();
        mDimensions = new Rect(0,0, canvas.getWidth(), canvas.getHeight());
        _holder.unlockCanvasAndPost(canvas);

    }

    //sets random points for the hidden objects
    private void setRandomPoints(ArrayList<HiddenTreasure>treasures){
        for (HiddenTreasure ht : treasures) {

            Random r = new Random();
            int randomXPoint = r.nextInt(mBackground.getWidth());
            int randomYPoint = r.nextInt(mBackground.getHeight());

            Point p = new Point(randomXPoint, randomYPoint);
            ht.setPoint(p);
        }
    }

    //check to see if the users tap is within a designated range of an object
    private boolean isInRange(Point a, Point b){
        return a.x >= b.x - 55 && a.x <= b.x + 55
                && a.y >= b.y - 55 && a.y <= b.y + 55;
    }

    //interface to pass found items array
    public interface forResult{
        void passFoundItems(ArrayList<HiddenTreasure> foundTreasures);
    }
}

package id.xaxxis.moviecatalogue.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import id.xaxxis.moviecatalogue.R;

public class CustomBottomNavigationView extends BottomNavigationView {

    private Path mPath;
    private Paint mPaint;

    private final int CURVE_CIRCLE_RADIUS = 128 / 2;
    private Point firstCurveStartPoint = new Point();
    private Point firstCurveEndPoint = new Point();
    private Point firstCurveControlPoint1 = new Point();
    private Point firstCurveControlPoint2 = new Point();

    private Point secondCurveStartPoint = new Point();
    private Point secondCurveEndPoint = new Point();
    private Point secondCurveControlPoint1 = new Point();
    private Point secondCurveControlPoint2 = new Point();
    private int navigationBarWidth;
    private int navigationBarHeight;

    public CustomBottomNavigationView(Context context) {
        super(context);
        init();
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        navigationBarWidth = getWidth();
        navigationBarHeight = getHeight();
        firstCurveStartPoint.set((navigationBarWidth / 2) - (CURVE_CIRCLE_RADIUS * 2) - (CURVE_CIRCLE_RADIUS / 3), 0);
        firstCurveEndPoint.set(navigationBarWidth / 2, CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4));
        secondCurveStartPoint = firstCurveEndPoint;
        secondCurveEndPoint.set((navigationBarWidth / 2) + (CURVE_CIRCLE_RADIUS * 2) + (CURVE_CIRCLE_RADIUS / 3), 0);

        firstCurveControlPoint1.set(firstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4), firstCurveStartPoint.y);
        firstCurveControlPoint2.set(firstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2) + CURVE_CIRCLE_RADIUS, firstCurveEndPoint.y);

        secondCurveControlPoint1.set(secondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2) - CURVE_CIRCLE_RADIUS, secondCurveStartPoint.y);
        secondCurveControlPoint2.set(secondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)), secondCurveEndPoint.y);

        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(firstCurveStartPoint.x, firstCurveStartPoint.y);

        mPath.cubicTo(firstCurveControlPoint1.x, firstCurveControlPoint1.y,
                firstCurveControlPoint2.x, firstCurveControlPoint2.y,
                firstCurveEndPoint.x, firstCurveEndPoint.y);

        mPath.cubicTo(secondCurveControlPoint1.x, secondCurveControlPoint1.y,
                secondCurveControlPoint2.x, secondCurveControlPoint2.y,
                secondCurveEndPoint.x, secondCurveEndPoint.y);

        mPath.lineTo(navigationBarWidth, 0);
        mPath.lineTo(navigationBarWidth, navigationBarHeight);
        mPath.lineTo(0, navigationBarHeight);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}

package cn.xiaochebao.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

import cn.xiaochebao.app.R;

/**
 * Created by Alan on 2017/04/25 0025.
 */
public class RoundProgressBar extends View {

    private DecimalFormat mFormat = new DecimalFormat("#0.00");
    private Paint paint;
    private int roundColor;
    private int roundProgressColor;

    private int textColor;
    private float textSize;
    private float roundWidth;
    private float max;
    private float progress;
    private boolean textIsDisplayable = true;
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public RoundProgressBar(Context context) {
        super(context);
    }

    public RoundProgressBar(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        paint = new Paint();
        style = STROKE;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int centre = getWidth() / 2;
        int radius = (int) (centre - roundWidth / 2);
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius, paint);


        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        double percent = (progress / max) * 100;
        String strPercent = mFormat.format(percent) + "%";

        float textWidth = paint.measureText(strPercent);

        if (textIsDisplayable && style == STROKE)
        {
            canvas.drawText(strPercent, centre - textWidth / 2, centre + textSize / 2, paint);
        }

        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        switch (style)
        {
            case STROKE:
            {
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
                break;
            }
            case FILL:
            {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 270, 360 * progress / max, true, paint);
                break;
            }
        }

    }

    public synchronized float getMax()
    {
        return max;
    }

    public synchronized void setMax(int max)
    {
        if (max < 0)
        {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public synchronized float getProgress()
    {
        return progress;
    }

    public synchronized void setProgress(float progress)
    {
        if (progress < 0)
        {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max)
        {
            progress = max;
        }
        if (progress <= max)
        {
            this.progress = progress;
            postInvalidate();
        }

    }

    public int getCricleColor()
    {
        return roundColor;
    }

    public void setCricleColor(int cricleColor)
    {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor()
    {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor)
    {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }

    public float getTextSize()
    {
        return textSize;
    }

    public void setTextSize(float textSize)
    {
        this.textSize = textSize;
    }

    public float getRoundWidth()
    {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth)
    {
        this.roundWidth = roundWidth;
    }

    public void setDefaultConfig()
    {
        setCricleColor(getResources().getColor(R.color.ltgray));
        setMax(100);
        setRoundWidth(getResources().getDimension(R.dimen.width_pgb_round));
        setProgress(0);
        setTextSize(getResources().getDimension(R.dimen.text_pgb_round));
        setCricleProgressColor(getResources().getColor(R.color.cyan2));
        setTextColor(getResources().getColor(R.color.lightgray));
    }
}

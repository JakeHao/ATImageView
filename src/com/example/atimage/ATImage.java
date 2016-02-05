package com.example.atimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

public class ATImage extends ImageView
{
    private String text;
    private int textColor;
    private int textSize;
    private int drawHeight;

    public ATImage(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ATImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public ATImage(Context context)
    {
        super(context);
    }

    private void init(Context context, AttributeSet attrs)
    {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ATImageView);
        text = arr.getString(R.styleable.ATImageView_text);
        textColor = arr.getColor(R.styleable.ATImageView_textColor, Color.WHITE);
        textSize = arr.getInt(R.styleable.ATImageView_textSize, (int)dipTopx(getContext(), 14));
        arr.recycle();
        drawHeight = (int)dipTopx(getContext(), 18);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        /**
         * getClipBounds() 获得该View的矩形框
         */
        Rect r = getClipBounds();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(44, 47, 52));
        paint.setAlpha(180);
        canvas.drawRect(0, getHeight() - drawHeight, getWidth(), getHeight(), paint);

        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Align.LEFT);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        Paint.FontMetrics f = textPaint.getFontMetrics();
        float textWidth = getTextWidth(textPaint);
        float textHeight = getTextHeight(textPaint);
        float x = getWidth() / 2 - textWidth / 2;
        float y = getHeight() - drawHeight / 2 + textHeight / 2 - f.descent;
        // x: 文字左边的距离
        // y: 文字基线的位置
        canvas.drawText(getEllipseText(textPaint), x, y, textPaint);
    }

    /**
     * 测量文字的宽度
     * 
     * @param paint
     * @return
     */
    private float getTextWidth(TextPaint paint)
    {
        return paint.measureText(getEllipseText(paint));
    }

    /**
     * 测量文字的高度
     * 
     * @param paint
     * @return
     */
    private float getTextHeight(Paint paint)
    {
        Paint.FontMetrics f = paint.getFontMetrics();
        return (float)Math.ceil(f.descent - f.ascent);
    }

    private String getEllipseText(TextPaint textPaint)
    {
        //第三个参数为: 文字所占的最大宽度
        return TextUtils.ellipsize(new SpannableString(text), textPaint, getWidth() - 0, TruncateAt.END).toString();
    }

    private float dipTopx(Context context, int size)
    {
        Resources res;
        if (context == null) res = Resources.getSystem();
        else res = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, res.getDisplayMetrics());
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }

    public int getTextSize()
    {
        return textSize;
    }

    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

}

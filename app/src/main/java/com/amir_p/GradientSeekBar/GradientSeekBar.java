package com.amir_p.GradientSeekBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class GradientSeekBar extends FrameLayout {
    private Paint backgroundPaint;
    private Path clipPath;
    private SeekBar seekBar;
    private int cornerRadius;

    public interface OnValueChangeListener {
        void onValueChanged(float value);
    }

    public static final int CENTER = 0, LEFT = 1, RIGHT = 2, UNDEFINED = -1;

    public GradientSeekBar(@NonNull Context context) {
        this(context, null, 0);
    }

    public GradientSeekBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientSeekBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(null);
        seekBar = new SeekBar(getContext());
        clipPath = new Path();
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        if (attrs != null) {
            TypedArray stdArray = context.obtainStyledAttributes(attrs,
                    new int[]{android.R.attr.background, android.R.attr.fontFamily}, defStyleAttr, 0);
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.GradientSeekBar, defStyleAttr, 0);
            if (stdArray != null) {
                setBackgroundColor(stdArray.getColor(0, Color.LTGRAY));
                stdArray.recycle();
            }
            if (typedArray != null) {
                setTitle(typedArray.getText(R.styleable.GradientSeekBar_title));
                setEmptyTextColor(typedArray.getColor(R.styleable.GradientSeekBar_emptyTextColor, Color.BLACK));
                setFilledTextColor(typedArray.getColor(R.styleable.GradientSeekBar_filledTextColor, Color.WHITE));
                setProgressStartColor(typedArray.getColor(R.styleable.GradientSeekBar_progressStartColor, Color.BLACK));
                setProgressEndColor(typedArray.getColor(R.styleable.GradientSeekBar_progressEndColor, Color.GRAY));
                setValue(typedArray.getFloat(R.styleable.GradientSeekBar_value, 0f));
                setTitleGravity(typedArray.getInt(R.styleable.GradientSeekBar_titleGravity, UNDEFINED));
                setTitleTextSize((int) typedArray.getDimension(R.styleable.GradientSeekBar_titleTextSize, getResources().getDimension(R.dimen.titleTextSize)));
                setCornerRadius((int) typedArray.getDimension(R.styleable.GradientSeekBar_cornerRadius, getResources().getDimension(R.dimen.cornerRadius)));
                setTitleMargin((int) typedArray.getDimension(R.styleable.GradientSeekBar_titleMargin, getResources().getDimension(R.dimen.titleMargin)));
                typedArray.recycle();
            }
        }
        addView(seekBar, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipPath(clipPath);
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        clipPath.reset();
        clipPath.addRoundRect(new RectF(0, 0, w, h), cornerRadius, cornerRadius, Path.Direction.CW);
        invalidate();
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        seekBar.setOnValueChangeListener(onValueChangeListener);
    }

    public void setBackgroundColor(@ColorInt int color) {
        backgroundPaint.setColor(color);
        invalidate();
    }

    public int getBackgroundColor() {
        return backgroundPaint.getColor();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidate();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setValue(float value) {
        seekBar.setValue(value);
    }

    public float getValue() {
        return seekBar.getValue();
    }

    public void setTitleTextSize(float textSize) {
        seekBar.setTitleTextSize(textSize);
    }

    public float getTitleTextSize() {
        return seekBar.getTitleTextSize();
    }

    public void setTitleGravity(int titleGravity) {
        seekBar.setTitleGravity(titleGravity);
    }

    public int getTitleGravity() {
        return seekBar.getTitleGravity();
    }

    public void setTitle(String title) {
        seekBar.setTitle(title);
    }

    public void setTitle(CharSequence title) {
        setTitle(title.toString());
    }

    public void setTitle(@StringRes int title) {
        setTitle(getContext().getResources().getString(title));
    }

    public String getText() {
        return seekBar.getTitle();
    }

    public void setTypeface(Typeface typeface) {
        seekBar.setTypeface(typeface);
    }

    public Typeface getTypeface() {
        return seekBar.getTypeface();
    }

    public void setProgressEndColor(@ColorInt int foregroundEndColor) {
        seekBar.setProgressEndColor(foregroundEndColor);
    }

    public int getProgressEndColor() {
        return seekBar.getProgressEndColor();
    }

    public void setProgressStartColor(@ColorInt int foregroundStartColor) {
        seekBar.setProgressStartColor(foregroundStartColor);
    }

    public int getProgressStartColor() {
        return seekBar.getProgressStartColor();
    }

    public void setEmptyTextColor(@ColorInt int emptyTextColor) {
        seekBar.setEmptyTextColor(emptyTextColor);
    }

    public int getEmptyTextColor() {
        return seekBar.getEmptyTextColor();
    }

    public void setFilledTextColor(@ColorInt int filledTextColor) {
        this.seekBar.setFilledTextColor(filledTextColor);
    }

    public int getFilledTextColor() {
        return seekBar.getFilledTextColor();
    }

    public void setTitleMargin(int titleMargin) {
        seekBar.setTitleMargin(titleMargin);
    }

    public int getTitleMargin() {
        return seekBar.getTitleMargin();
    }

    private class SeekBar extends View {
        private Paint maskedTitlePaint, foregroundPaint, titlePaint, indicatorPaint;
        private Rect textBounds;
        private int titleMargin, indicatorMargin, indicatorWidth;
        private float value;
        private String title;
        private boolean showIndicator = true;
        private int foregroundStartColor, foregroundEndColor;
        private int titleGravity = 1;
        private OnValueChangeListener onValueChangeListener;

        public SeekBar(Context context) {
            this(context, null);
        }

        public SeekBar(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public SeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            foregroundPaint = new Paint();
            titlePaint = new Paint();
            maskedTitlePaint = new Paint();
            indicatorPaint = new Paint();
            textBounds = new Rect();

            foregroundPaint.setAntiAlias(true);

            maskedTitlePaint.setAntiAlias(true);
            maskedTitlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            titlePaint.setAntiAlias(true);

            indicatorPaint.setColor(Color.WHITE);

            indicatorMargin = (int) getResources().getDimension(R.dimen.indicatorMargin);
            indicatorWidth = (int) getResources().getDimension(R.dimen.indicatorWidth);

            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            int direction = getLayoutDirection();
            Shader shader;
            if (direction == LAYOUT_DIRECTION_RTL) {
                shader = new LinearGradient(0, 0, w, 0, foregroundEndColor, foregroundStartColor, Shader.TileMode.CLAMP);
            } else {
                shader = new LinearGradient(0, 0, w, 0, foregroundStartColor, foregroundEndColor, Shader.TileMode.CLAMP);
            }
            foregroundPaint.setShader(shader);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float filledWidth = value * getWidth();
            int direction = getLayoutDirection();
            int titleStartX = titleMargin;
            float maskRectLeft = filledWidth;
            float maskRectRight = getWidth();
            float filledRectLeft = 0;
            float filledRectRight = filledWidth;
            if (direction == LAYOUT_DIRECTION_RTL) {
                filledRectLeft = getWidth() - filledWidth;
                filledRectRight = getWidth();
                maskRectLeft = 0;
                maskRectRight = getWidth() - filledWidth;
            }
            if (titleGravity == RIGHT || (titleGravity == UNDEFINED && direction == LAYOUT_DIRECTION_RTL)) {
                titleStartX = getWidth() - ((int) textBounds.exactCenterX() * 2) - titleMargin;
            } else if (titleGravity == CENTER) {
                titleStartX = (getWidth() / 2) - (int) textBounds.exactCenterX();
            }
            canvas.drawRect(filledRectLeft, 0, filledRectRight, getHeight(), foregroundPaint);
            if (title != null) {
                canvas.drawText(title, titleStartX, ((float) (getHeight() / 2)) - textBounds.exactCenterY(), titlePaint);
            }
            canvas.drawRect(maskRectLeft, 0, maskRectRight, getHeight(), maskedTitlePaint);
//            if (showIndicator) {
//                canvas.drawRect(filledWidth - indicatorMargin, indicatorMargin, filledWidth - indicatorMargin + indicatorWidth, getHeight() - indicatorMargin, indicatorPaint);
//            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
                int direction = getLayoutDirection();
                if (direction == LAYOUT_DIRECTION_RTL) {
                    setValue(1 - (event.getX() / getWidth()));
                } else {
                    setValue(event.getX() / getWidth());
                }
            }
            return true;
        }

        private void normalizeValue() {
            if (value > 1) {
                value = 1;
            } else if (value < 0) {
                value = 0;
            }
        }

        public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
            this.onValueChangeListener = onValueChangeListener;
        }

        public void setTitleTextSize(float textSize) {
            titlePaint.setTextSize(textSize);
            titlePaint.getTextBounds(title, 0, title.length(), textBounds);
            invalidate();
        }

        public float getTitleTextSize() {
            return titlePaint.getTextSize();
        }

        public void setTitleGravity(int titleGravity) {
            this.titleGravity = titleGravity;
            invalidate();
        }

        public int getTitleGravity() {
            return titleGravity;
        }

        public void setValue(float value) {
            this.value = value;
            normalizeValue();
            invalidate();
            if (onValueChangeListener != null) {
                onValueChangeListener.onValueChanged(value);
            }
        }

        public float getValue() {
            return value;
        }

        public void setTitle(String title) {
            this.title = title;
            titlePaint.getTextBounds(title, 0, title.length(), textBounds);
            invalidate();
        }

        public String getTitle() {
            return title;
        }

        public void setTypeface(Typeface typeface) {
            titlePaint.setTypeface(typeface);
            invalidate();
        }

        public Typeface getTypeface() {
            return titlePaint.getTypeface();
        }

        public void setProgressEndColor(int foregroundEndColor) {
            this.foregroundEndColor = foregroundEndColor;
            invalidate();
        }

        public int getProgressEndColor() {
            return foregroundEndColor;
        }

        public void setProgressStartColor(int foregroundStartColor) {
            this.foregroundStartColor = foregroundStartColor;
            invalidate();
        }

        public int getProgressStartColor() {
            return foregroundStartColor;
        }

        public void setEmptyTextColor(int emptyTextColor) {
            maskedTitlePaint.setColor(emptyTextColor);
            invalidate();
        }

        public int getEmptyTextColor() {
            return maskedTitlePaint.getColor();
        }

        public void setFilledTextColor(int filledTextColor) {
            titlePaint.setColor(filledTextColor);
            invalidate();
        }

        public int getFilledTextColor() {
            return titlePaint.getColor();
        }

        public void setTitleMargin(int titleMargin) {
            this.titleMargin = titleMargin;
            invalidate();
        }

        public int getTitleMargin() {
            return titleMargin;
        }
    }
}
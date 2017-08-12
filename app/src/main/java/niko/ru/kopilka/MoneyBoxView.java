package niko.ru.kopilka;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import niko.ru.kopilka.DialogEnterMoney.OnEnterMoneyListener;

public class MoneyBoxView extends View {

  private Paint figurePaint;
  private Paint textPaint;
  private int height;
  private int width;
  private int backgroundColor;
  private int padding = 50;
  private float startAngle = 0;
  private float endAngle = 0;
  private float included = 4000f;
  private float total = 20000f;
  private String rate = "ГРН";
  private int radius;
  private int cx;
  private int cy;

  private FragmentManager fragmentManager;
  private String descText = "На покупку SAMSUNG GALAXY 10";

  public void setFragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }

  public MoneyBoxView(Context context, FragmentManager fragmentManager) {
    super(context);
    this.fragmentManager = fragmentManager;
    init(null);
  }

  public MoneyBoxView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    backgroundColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    figurePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    figurePaint.setStyle(Style.STROKE);
    textPaint.setStyle(Style.FILL);
    textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
    textPaint.setTextAlign(Align.CENTER);
    getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        width = getWidth();
        height = getHeight();
      }
    });
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    endAngle = ((included * 100 / total) / 100) * 360;
    canvas.drawColor(backgroundColor);
    drawTopText(canvas);
    drawMiddleText(canvas);
    drawDescentText(canvas);
    drawFirstCircle(canvas);
    drawSecondCircle(canvas);
  }

  private void drawTopText(Canvas canvas) {
    textPaint.setTextSize(TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
    canvas.drawText(descText, width / 2,
        height / 8, textPaint);
  }

  private void drawMiddleText(Canvas canvas) {
    textPaint.setTextSize(TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, getResources().getDisplayMetrics()));
    canvas.drawText(included + " / " + total + " " + rate, width / 2,
        height / 2 - (textPaint.descent() + textPaint.ascent() / 2), textPaint);
  }

  private void drawDescentText(Canvas canvas) {
    textPaint.setTextSize(TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
    canvas.drawText((included * 100 / total) + "%", width / 2,
        height / 2 + height / 5, textPaint);
  }

  @SuppressLint("NewApi")
  private void drawSecondCircle(Canvas canvas) {
    figurePaint.setColor(ContextCompat.getColor(getContext(), R.color.red));
    figurePaint.setStrokeWidth(20);
    canvas.drawArc(padding + 14, height / 2 - height / 4 - 4, width - padding - 14,
        height / 2 + height / 4 + 4, startAngle, endAngle, false, figurePaint);
  }

  private void drawFirstCircle(Canvas canvas) {
    figurePaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
    figurePaint.setStrokeWidth(10);
    radius = width / 2 - padding;
    cx = width / 2;
    cy = height / 2;
    canvas.drawCircle(cx, cy, radius, figurePaint);
  }

  private void openDialog() {
    DialogEnterMoney enterMoney = new DialogEnterMoney();
    enterMoney.show(fragmentManager, "dialog");
    enterMoney.setOnEnterMoneyListener(new OnEnterMoneyListener() {
      @Override
      public void onEnterMoney(float money) {
        included = money;
        postInvalidate();
      }
    });
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int x = (int) event.getX();
    int y = (int) event.getY();

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        int dst = (int) Math.sqrt(Math.pow((cx - x), 2) + Math.pow((cy - y), 2));
        if (dst < radius) {
          openDialog();
        }
        break;
    }
    postInvalidate();
    return true;
  }
}

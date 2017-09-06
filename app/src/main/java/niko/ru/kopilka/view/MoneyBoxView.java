package niko.ru.kopilka.view;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import niko.ru.kopilka.model.Task;
import niko.ru.kopilka.view.DialogEnterMoney.OnEnterMoneyListener;
import niko.ru.kopilka.R;

public class MoneyBoxView extends View {

  private Paint figurePaint;
  private Paint textPaint;
  private int height;
  private int width;
  private int backgroundColor;
  private int padding = 50;
  private float speed = 1.2f;
  private float startAngle = 0;
  private float endAngle = 0;
  private long id;
  private float included = 0;
  private float total;
  private String rate;
  private int radius;
  private int cx;
  private int cy;

  private FragmentManager fragmentManager;
  private float pos;
  private SharedPreferences sharedPreferences;


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
    sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
    //included = getIncludedValue();
    backgroundColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    figurePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    figurePaint.setStyle(Style.STROKE);
    textPaint.setStyle(Style.FILL);
    textPaint.setTypeface(Typeface.create("roboto", Typeface.NORMAL));
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

  public void set(long id, String rate, float included, float total) {
    this.id = id;
    this.rate = rate;
    this.included = included;
    this.total = total;
    postInvalidate();
  }

  private void animateDrawCircle() {
    if (included == 0) {
      pos = 0;
    } else {
      pos = ((included * 100 / total) / 100) * 360;
    }
    if (endAngle < pos) {
      endAngle += speed;
      postInvalidate();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    animateDrawCircle();
    canvas.drawColor(backgroundColor);
    drawMiddleText(canvas);
    drawDescentText(canvas);
    drawFirstCircle(canvas);
    drawSecondCircle(canvas);
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
    canvas.drawText((int) (included * 100 / total) + "%", width / 2,
        height / 2 + height / 5, textPaint);
  }

  @SuppressLint("NewApi")
  private void drawSecondCircle(Canvas canvas) {
    figurePaint.setColor(ContextCompat.getColor(getContext(), R.color.red));
    figurePaint.setStrokeWidth(20);
    figurePaint.setStrokeCap(Cap.ROUND);
    canvas
        .drawArc(cx - radius + 15, cy - radius + 15, cx + radius - 15, cy + radius - 15,
            startAngle + 90,
            endAngle, false, figurePaint);
    // canvas.drawArc(padding + 14, height / 2 - height / 4 - 4, width - padding - 14, height / 2 + height / 4 + 4, startAngle, endAngle, false, figurePaint);
    //canvas.drawArc(width /6 - padding, height / 2 - height / 4 - padding, width - padding, height / 2 + height / 4 , startAngle, endAngle, false, figurePaint);
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
        included += money;
        setIncludedValue(included);
        postInvalidate();
      }

      @Override
      public void onWithDrawMoney(float money) {
        included -= money;
        setIncludedValue(included);
        endAngle = 0;
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

  private void setIncludedValue(float value) {
    Task task = Task.findById(Task.class, id);
    task.included = value;
    task.save();
  }

}

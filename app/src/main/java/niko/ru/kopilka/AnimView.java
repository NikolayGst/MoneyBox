package niko.ru.kopilka;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;


public class AnimView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

  //private TextTopThread textTopThread;
  private boolean run;
  private Thread thread;

  private int width, height;
  private SurfaceHolder holder;
  private Paint paint;

  public AnimView(Context context) {
    super(context);
    init();
  }


  public void setRun(boolean run) {
    this.run = run;
  }

  public boolean isRun() {
    return run;
  }

  public AnimView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setTextSize(80);
    paint.setTextAlign(Align.CENTER);
    paint.setStyle(Style.FILL);
    paint.setColor(Color.WHITE);
    getHolder().addCallback(this);
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
  public void surfaceCreated(SurfaceHolder holder) {
    this.holder = holder;
    thread = new Thread(this);
    thread.start();
  }

  private void drawBackground(SurfaceHolder holder) {

  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }


  @Override
  public void run() {
    try {
      Canvas canvas = holder.lockCanvas(null);
      canvas.drawColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));


      canvas.drawText("Работаем..", canvas.getWidth() / 2,
          canvas.getHeight() / 2 - (paint.descent() + paint.ascent() / 2), paint);

      holder.unlockCanvasAndPost(canvas);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    boolean retry = true;
    try {
      while (retry) {
        thread.join();
        retry = false;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}

package niko.ru.kopilka;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;

public class TextTopThread extends Thread {

  private Paint paint;
  private SurfaceHolder holder;
  private boolean run;

  public TextTopThread(SurfaceHolder holder) {
    this.holder = holder;

  }

  public void setRun(boolean run) {
    this.run = run;
  }

  public boolean isRun() {
    return run;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(1500);
    Canvas canvas = holder.lockCanvas(null);
    canvas.drawText("Работаем..", canvas.getWidth() / 2,
        canvas.getHeight() / 2 - (paint.descent() + paint.ascent() / 2), paint);
    holder.unlockCanvasAndPost(canvas);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

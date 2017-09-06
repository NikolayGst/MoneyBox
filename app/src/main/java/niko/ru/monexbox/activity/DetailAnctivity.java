package niko.ru.monexbox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import niko.ru.monexbox.R;
import niko.ru.monexbox.model.Task;
import niko.ru.monexbox.view.MoneyBoxView;

public class DetailAnctivity extends AppCompatActivity {

  private TextView desc;
  private MoneyBoxView moneyBox;
  private long id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_detail);

    initView();

    initData();

    initAnimation();


  }

  private void initView() {
    desc = (TextView) findViewById(R.id.desc);
    moneyBox = (MoneyBoxView) findViewById(R.id.moneyBox);
    moneyBox.setFragmentManager(getSupportFragmentManager());
  }

  private void initData() {
    Bundle extras = getIntent().getExtras();

    if (extras != null) {
      id = extras.getLong("id");
      Task task = Task.findById(Task.class, id);
      desc.setText(task.description);
      moneyBox.set(id,task.rate, task.included, task.total);
    }
  }

  private void initAnimation() {
    AlphaAnimation descAnim = new AlphaAnimation(0, 1f);
    descAnim.setDuration(1500);
    descAnim.setAnimationListener(new AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        AlphaAnimation moneyBoxAnim = new AlphaAnimation(0, 1f);
        moneyBoxAnim.setDuration(1000);
        moneyBox.setVisibility(View.VISIBLE);
        moneyBox.startAnimation(moneyBoxAnim);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });

    desc.startAnimation(descAnim);
  }

}

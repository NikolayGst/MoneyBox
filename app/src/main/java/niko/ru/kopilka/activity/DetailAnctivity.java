package niko.ru.kopilka.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import niko.ru.kopilka.R;
import niko.ru.kopilka.view.MoneyBoxView;

public class DetailAnctivity extends AppCompatActivity {

  private TextView desc;
  private MoneyBoxView moneyBox;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //  setContentView(new MoneyBoxView(this, getSupportFragmentManager()));
    //setContentView(new AnimView(this));
    setContentView(R.layout.activity_detail);
    desc = (TextView) findViewById(R.id.desc);
    moneyBox = (MoneyBoxView) findViewById(R.id.moneyBox);
    Bundle extras = getIntent().getExtras();

    if (extras != null) {
      desc.setText(extras.getString("desc"));
      moneyBox.set(extras.getString("rate"), extras.getFloat("total"));
    }

    moneyBox.setFragmentManager(getSupportFragmentManager());
    //moneyBox.setVisibility(View.VISIBLE);
    final AlphaAnimation descAnim = new AlphaAnimation(0, 1f);
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

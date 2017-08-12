package niko.ru.kopilka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      setContentView(new MoneyBoxView(this, getSupportFragmentManager()));
    //setContentView(new AnimView(this));
  }
}

package niko.ru.moneybox.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import niko.ru.moneybox.R;
import niko.ru.moneybox.activity.fragment.CreateTaskFragment;
import niko.ru.moneybox.activity.fragment.ListTasksFragment;

public class MainActivity extends AppCompatActivity {

  private CreateTaskFragment createTaskFragment;
  private ListTasksFragment listTasksFragment;
  private FragmentTransaction transaction;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      transaction = getSupportFragmentManager().beginTransaction();
      switch (item.getItemId()) {
        case R.id.create:
          transaction.replace(R.id.container, createTaskFragment);
          break;
        case R.id.list:
          transaction.replace(R.id.container, listTasksFragment);
          break;
      }
      transaction.commit();
      return true;
    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    listTasksFragment = new ListTasksFragment();
    createTaskFragment = new CreateTaskFragment();
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    navigation.setSelectedItemId(R.id.create);
  }

}

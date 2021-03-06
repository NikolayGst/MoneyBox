package niko.ru.moneybox.activity.fragment;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import java.util.Arrays;
import niko.ru.moneybox.R;
import niko.ru.moneybox.activity.DetailActivity;
import niko.ru.moneybox.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTaskFragment extends Fragment {


  private EditText desc;
  private EditText rate;
  private EditText total;
  private CardView cardView;
  private Point size;
  private AlertDialog alertDialog;
  private String[] rates;

  public CreateTaskFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_create_task, container, false);
    rates = getResources().getStringArray(R.array.rates);
    cardView = (CardView) inflate.findViewById(R.id.card_view);

    initScreenSize();

    AnimationSet animationSet = new AnimationSet(true);
    TranslateAnimation up = new TranslateAnimation(0, 0, size.y, -200);
    up.setDuration(1000);
    TranslateAnimation down = new TranslateAnimation(0, 0, -200, 200);
    down.setDuration(1600);
    animationSet.addAnimation(up);
    animationSet.addAnimation(down);

    cardView.startAnimation(animationSet);

    desc = (EditText) inflate.findViewById(R.id.desc);
    rate = (EditText) inflate.findViewById(R.id.rate);
    total = (EditText) inflate.findViewById(R.id.total);

    initDialogSelectRate();

    rate.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        alertDialog.show();
      }
    });

    Button createTask = (Button) inflate.findViewById(R.id.createTask);
    createTask.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (desc.getText().toString().length() != 0 && total.getText().toString().length() != 0
            && rate.getText().toString().length() != 0) {

          Task task = new Task(desc.getText().toString(),
              rate.getText().toString(),
              Float.parseFloat(total.getText().toString()));
          task.save();

          Intent intent = new Intent(getActivity(), DetailActivity.class);
          intent.putExtra("id", task.getId());
          startActivity(intent);
        }
      }
    });

    return inflate;
  }

  private void initDialogSelectRate() {
    Builder alertDialogBuilder = new Builder(getActivity());
    alertDialogBuilder.setTitle(R.string.select_rate)
        .setSingleChoiceItems(rates, -1, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int item) {
            rate.setText(rates[item]);
            dialog.dismiss();
          }
        });
    alertDialog = alertDialogBuilder.create();
  }

  private void initScreenSize() {
    size = new Point();
    Display display = getActivity().getWindowManager().getDefaultDisplay();
    display.getSize(size);
  }

  @Override
  public void onResume() {
    super.onResume();
    desc.getText().clear();
    total.getText().clear();
    rate.getText().clear();
  }
}

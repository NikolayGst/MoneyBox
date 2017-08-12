package niko.ru.kopilka.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import niko.ru.kopilka.R;
import niko.ru.kopilka.activity.DetailAnctivity;
import niko.ru.kopilka.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTaskFragment extends Fragment {


  public CreateTaskFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_create_task, container, false);
    final EditText desc = (EditText) inflate.findViewById(R.id.desc);
    final EditText rate = (EditText) inflate.findViewById(R.id.rate);
    final EditText total = (EditText) inflate.findViewById(R.id.total);
    Button createTask = (Button) inflate.findViewById(R.id.createTask);
    createTask.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (desc.getText().toString().length() != 0 && total.getText().toString().length() != 0
            && rate.getText().toString().length() != 0) {

          Task task = new Task(desc.getText().toString(), rate.getText().toString(), Float.parseFloat(total.getText().toString()));
          task.save();

          Intent intent = new Intent(getActivity(), DetailAnctivity.class);
          intent.putExtra("id", task.getId());/*
          intent.putExtra("desc", desc.getText().toString());
          intent.putExtra("rate", rate.getText().toString());
          intent.putExtra("total", Float.parseFloat(total.getText().toString()));*/
          startActivity(intent);
        }
      }
    });
    return inflate;
  }

}

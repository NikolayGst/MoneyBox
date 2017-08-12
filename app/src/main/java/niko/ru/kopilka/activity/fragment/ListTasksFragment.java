package niko.ru.kopilka.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import niko.ru.kopilka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTasksFragment extends Fragment {


  public ListTasksFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_list_task, container, false);
  }

}

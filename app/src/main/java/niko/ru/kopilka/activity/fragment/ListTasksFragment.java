package niko.ru.kopilka.activity.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.nitrico.lastadapter.Holder;
import com.github.nitrico.lastadapter.ItemType;
import com.github.nitrico.lastadapter.LastAdapter;
import java.util.List;
import niko.ru.kopilka.BR;
import niko.ru.kopilka.R;
import niko.ru.kopilka.activity.DetailAnctivity;
import niko.ru.kopilka.databinding.ItemLayoutBinding;
import niko.ru.kopilka.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTasksFragment extends Fragment {


  private RecyclerView recyclerTask;
  private TextView empty;
  private List<Task> tasks;
  private LastAdapter lastAdapter;

  public ListTasksFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_list_task, container, false);

    recyclerTask = (RecyclerView) inflate.findViewById(R.id.recyclerTask);
    empty = (TextView) inflate.findViewById(R.id.empty);

    recyclerTask.setLayoutManager(new LinearLayoutManager(getContext()));

    return inflate;
  }

  @Override
  public void onResume() {
    super.onResume();
    tasks = Task.listAll(Task.class);

    if (checkEmptyList()) {
      /*SlideInLeftAnimator slideInLeftAnimator = new SlideInLeftAnimator();
      slideInLeftAnimator.setChangeDuration(1500);
      slideInLeftAnimator.setMoveDuration(1500);
      recyclerTask.setItemAnimator(slideInLeftAnimator);
*/
      /*   TranslateAnimation animation = new TranslateAnimation(-100, 0, 0, 0);
         animation.setDuration(1000);
         View
         root.setAnimation(animation);
         animation.start();*/
      lastAdapter = new LastAdapter(tasks, BR.task)
          .map(Task.class, new ItemType<ItemLayoutBinding>(R.layout.item_layout) {
            @Override
            public void onCreate(final Holder<ItemLayoutBinding> holder) {
              super.onCreate(holder);
           /*   TranslateAnimation animation = new TranslateAnimation(-100, 0, 0, 0);
              animation.setDuration(1000);
              View
              root.setAnimation(animation);
              animation.start();*/
              View root = holder.getBinding().getRoot();

              root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                  Task task = holder.getBinding().getTask();
                  startDetailActivity(task);
                }
              });

              root.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                  Builder builder = new Builder(getContext());
                  builder.setMessage("Вы хотите удалить выбранное желание?");
                  builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      Task task = holder.getBinding().getTask();
                      task.delete();

                      int position = holder.getAdapterPosition();
                      tasks.remove(position);
                      lastAdapter.notifyItemRemoved(position);

                      checkEmptyList();
                    }
                  });
                  builder.setNegativeButton("Нет", null);
                  AlertDialog dialog = builder.create();
                  dialog.show();
                  return true;
                }
              });
            }
          })
          .into(recyclerTask);
    }
  }

  private boolean checkEmptyList() {
    if (tasks.size() != 0) {
      recyclerTask.setVisibility(View.VISIBLE);
      empty.setVisibility(View.INVISIBLE);
      return true;
    } else {
      recyclerTask.setVisibility(View.INVISIBLE);
      empty.setVisibility(View.VISIBLE);
      return false;
    }
  }

  private void startDetailActivity(Task task) {
    Intent intent = new Intent(getActivity(), DetailAnctivity.class);
    intent.putExtra("id", task.getId());
    startActivity(intent);
  }

}

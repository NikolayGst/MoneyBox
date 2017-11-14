package niko.ru.moneybox.activity.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import com.github.nitrico.lastadapter.Holder;
import com.github.nitrico.lastadapter.ItemType;
import com.github.nitrico.lastadapter.LastAdapter;
import java.util.List;
import niko.ru.moneybox.BR;
import niko.ru.moneybox.R;
import niko.ru.moneybox.activity.DetailActivity;
import niko.ru.moneybox.databinding.ItemLayoutBinding;
import niko.ru.moneybox.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTasksFragment extends Fragment {


  private RecyclerView recyclerTask;
  private TextView empty;
  private List<Task> tasks;
  private LastAdapter lastAdapter;

  private AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1f);

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

              AnimationSet set = new AnimationSet(false);
              TranslateAnimation translate = new TranslateAnimation(0, 0, -20, 0);
              translate.setDuration(300);
              AlphaAnimation alpha = new AlphaAnimation(0, 1f);
              alpha.setDuration(300);
              set.addAnimation(alpha);
              set.addAnimation(translate);

              View root = holder.getBinding().getRoot();
              root.startAnimation(set);
              root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                  startDetailActivity(holder.getBinding().getTask());
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

              int progress = holder.getBinding().getTask().getProgress();

              holder.getBinding().imageCompleted
                  .setVisibility(progress >= 100 ? View.VISIBLE : View.INVISIBLE);

              holder.getBinding().progressBar.getProgressDrawable().mutate()
                  .setColorFilter(ContextCompat.getColor(getContext(),
                      progress >= 100 ? R.color.green_completed : R.color.red),
                      Mode.SRC_IN);
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
      alphaAnimation.setDuration(1500);
      empty.startAnimation(alphaAnimation);
      return false;
    }
  }

  private void startDetailActivity(Task task) {
    Intent intent = new Intent(getActivity(), DetailActivity.class);
    intent.putExtra("id", task.getId());
    startActivity(intent);
  }

}

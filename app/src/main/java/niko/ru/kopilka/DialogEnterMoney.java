package niko.ru.kopilka;

import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DialogEnterMoney extends DialogFragment {

  public interface OnEnterMoneyListener {

    void onEnterMoney(float money);
  }

  private OnEnterMoneyListener onEnterMoneyListener;

  public void setOnEnterMoneyListener(
      OnEnterMoneyListener onEnterMoneyListener) {
    this.onEnterMoneyListener = onEnterMoneyListener;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_enter_money, container, false);
    final EditText edit = (EditText) view.findViewById(R.id.editMoney);
    Button btn = (Button) view.findViewById(R.id.save);
    edit.getBackground().mutate().setColorFilter(
        new PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
            Mode.SRC_IN));
    btn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onEnterMoneyListener.onEnterMoney(Float.parseFloat(edit.getText().toString()));
        dismiss();
      }
    });
    return view;
  }
}

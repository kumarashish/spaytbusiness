package common;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {
    MyEditText editText;
    boolean isTextChanging=false;
    public MyEditText(Context context) {

        super(context);
        editText=this;

        addTextWatcher(editText);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        editText=this;
        addTextWatcher(editText);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        editText=this;
        addTextWatcher(editText);
    }

  public void addTextWatcher(final EditText editText)
  {
      editText.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              isTextChanging=false;
          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              isTextChanging=true;
          }

          @Override
          public void afterTextChanged(Editable editable) {
              isTextChanging=false;
              handleEditText(editable);
          }
      });


  }
  public void handleEditText(Editable editable)
  {if(isTextChanging==false) {
      if ((editable.length() == 2) && (!editable.toString().contains(":"))) {
          editText.setText(editText.getText().toString() + ":");
          editText.setSelection(editText.getText().length());
      }
  }
  }
}


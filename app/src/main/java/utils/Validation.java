package utils;

import android.app.Activity;

import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import com.spaytbusiness.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jaya.krishna on 14-12-2016.
 */

public class Validation {
    private static Pattern pattern;
    private static Matcher matcher;
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
    private static final String PASSWORD_REGEX = "[_A-Za-z0-9]{8,16}";
    Activity context;

    public Validation( Activity context) {
        this.context = context;
    }


    /***
     * validate password with given password regular expression
     */
    public boolean isPasswordValid(EditText password,Activity act) {
        boolean matches = false;
        if (isNotNull(password)) {
            String pwd = password.getText().toString().trim();
            if (pwd.length() < 4) {
                Toast.makeText(context, "Password should be at least 4 characters ", Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
        }

        return matches;
    }

    public boolean isPassword_ConfirmPasswordSame(EditText password, EditText confirmPassword) {
        if (password.getText().toString().equals(confirmPassword.getText().toString())) {
            return true;
        }else{
            if(confirmPassword.getText().toString().length()==0)
            {
                Toast.makeText(context, "Confirm password should not be blank. ", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Password and confirm password should be same. ", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    public void requestFocus(View view) {
        if (view.requestFocus()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }
    }


    public boolean validateEmail(EditText edt_email, TextInputLayout inputLayoutEmail) {
        String email = edt_email.getText().toString().trim();

        if (email.isEmpty() == false) {
            if (isValidEmail(email)) {
                inputLayoutEmail.setErrorEnabled(false);
                return true;
            } else {
                inputLayoutEmail.setError(context.getString(R.string.err_msg_email));
                requestFocus(edt_email);
                return false;
            }

        } else {
            inputLayoutEmail.setError(context.getString(R.string.err_msg_email_null));
            requestFocus(edt_email);
            return false;

        }


    }


    public boolean validatePassword(EditText edt_password, TextInputLayout  inputLayoutPassword) {
        if (edt_password.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(context.getString(R.string.err_msg_password));
            requestFocus(edt_password);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
    public boolean validateConfirmPassword(EditText edt_password, TextInputLayout  inputLayoutPassword) {
        if (edt_password.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(context.getString(R.string.err_msg_confirm_password));
            requestFocus(edt_password);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
    public boolean  validatePasswordConfirmPassword(EditText edt_password, TextInputLayout  inputLayoutPassword,EditText confirmpassword, TextInputLayout  inputLayoutConfirmPassword) {
        if (edt_password.getText().toString().equals(confirmpassword.getText().toString())) {
            return true;
        }else{
            requestFocus(edt_password);
            inputLayoutConfirmPassword.setError(context.getString(R.string.err_msg_different_password));
        }
        return false;
    }

    /**
     * check whether string contains value or not
     */
    public boolean isNameValidated(EditText txt) {
        try {

            if ((txt != null) && (txt.getText().toString().trim().length() > 0)) {
                return true;
            } else {
                Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
            return false;

        }
    }


    /**
     * check whether string contains value or not
     */
    public boolean isNotNull(EditText txt) {
        try {

            if ((txt != null) && (txt.getText().toString().trim().length() > 0)) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.fillInStackTrace();
            return false;

        }
    }



}

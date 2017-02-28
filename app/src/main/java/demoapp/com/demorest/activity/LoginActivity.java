package demoapp.com.demorest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quickblox.sample.core.utils.KeyboardUtils;
import com.quickblox.sample.core.utils.Toaster;

import demoapp.com.demorest.R;
import demoapp.com.demorest.utils.CustomSharedPreference;

/**
 * Created by Administrator on 2/27/2017.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    TextView mCreateAccount;
    TextInputLayout inputLayoutMobile, inputLayoutPassword;
    EditText _mobileText, _passwordText;
    Button _LoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(CustomSharedPreference.getUserLoginStatus(LoginActivity.this).equals("LoggedIn")){
            goToMainPage();
        }

        setContentView(R.layout.login);

        inputLayoutMobile = (TextInputLayout) findViewById(R.id.inputLayoutMobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);

        _mobileText = (EditText) findViewById(R.id.input_mobile);
        _passwordText = (EditText) findViewById(R.id.input_password);

        _LoginButton = (Button) findViewById(R.id.btn_login);
        _LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mCreateAccount = (TextView) findViewById(R.id.create_account);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        finish();
    }

    private void goToMainPage() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected View getSnackbarAnchorView() {
        return null;
    }

    public void login() {
        Log.d(TAG, "Login");
        _LoginButton.setEnabled(false);
        hideKeyboard();
        Toaster.longToast("Please Create A New Account!");
    }
    private void hideKeyboard() {
        KeyboardUtils.hideKeyboard(_mobileText);
        KeyboardUtils.hideKeyboard(_passwordText);
    }

}
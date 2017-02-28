package demoapp.com.demorest.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.sample.core.utils.KeyboardUtils;
import com.quickblox.sample.core.utils.SharedPrefsHelper;
import com.quickblox.sample.core.utils.Toaster;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import demoapp.com.demorest.R;
import demoapp.com.demorest.db.QbUsersDbManager;
import demoapp.com.demorest.services.SignUpService;
import demoapp.com.demorest.utils.Consts;
import demoapp.com.demorest.utils.CustomSharedPreference;
import demoapp.com.demorest.utils.QBEntityCallbackImpl;
import demoapp.com.demorest.utils.UsersUtils;


public class SignupActivity extends BaseActivity {
    private static final String TAG = "SignupActivity";

    TextInputLayout inputLayoutName, inputLayoutMobile, inputLayoutPassword, inputLayoutRePassword;
    EditText _nameText, _mobileText, _passwordText, _reEnterPasswordText;
    Button _signupButton, mGPlusSignUpButton;
    String name, mobile, password;

    private QBUser userForSave;
    private QbUsersDbManager dbManager;
    String mRoomName;
    public static String mCurrentUserName, mCurrentUserLogin, mCurrentUserPassword;
    SharedPrefsHelper sharedPrefsHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefsHelper = SharedPrefsHelper.getInstance();

        if (sharedPrefsHelper.hasQbUser()) {
            startOldUserLoginService(sharedPrefsHelper.getQbUser());
            startNewActivity();
            return;
        }

        setContentView(R.layout.signup);

        mRoomName = "HomeRent";

        dbManager = QbUsersDbManager.getInstance(getApplicationContext());

        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.inputLayoutMobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        inputLayoutRePassword = (TextInputLayout) findViewById(R.id.inputLayoutRePassword);

        _nameText = (EditText) findViewById(R.id.input_name);
        _mobileText = (EditText) findViewById(R.id.input_mobile);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);

        _signupButton = (Button) findViewById(R.id.btn_signup);
        mGPlusSignUpButton = (Button) findViewById(R.id.btn_google_signup);

        mGPlusSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.longToast("Coming Soon...");
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    @Override
    protected View getSnackbarAnchorView() {
        return null;
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        name = _nameText.getText().toString();
        mCurrentUserName = String.valueOf(_nameText.getText());
        CustomSharedPreference.setUserFullName(SignupActivity.this, mCurrentUserName);
        mobile = _mobileText.getText().toString();
        mCurrentUserLogin = String.valueOf(_mobileText.getText());
        CustomSharedPreference.setUserLogin(SignupActivity.this, mCurrentUserLogin);
        String primarypassword = _passwordText.getText().toString();
        password = _reEnterPasswordText.getText().toString();
        mCurrentUserPassword = String.valueOf(_reEnterPasswordText.getText());
        CustomSharedPreference.setUserPassword(SignupActivity.this, mCurrentUserPassword);
        hideKeyboard();
        startSignUpNewUser(createUserWithEnteredData());

    }
    private void hideKeyboard() {
        KeyboardUtils.hideKeyboard(_nameText);
        KeyboardUtils.hideKeyboard(_mobileText);
        KeyboardUtils.hideKeyboard(_passwordText);
        KeyboardUtils.hideKeyboard(_reEnterPasswordText);
    }

    private void startSignUpNewUser(final QBUser newUser) {
        showProgressDialog(R.string.dlg_creating_new_user);
        requestExecutor.signUpNewUser(newUser, new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser result, Bundle params) {
                        loginToChat(result);
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        if (e.getHttpStatusCode() == Consts.ERR_LOGIN_ALREADY_TAKEN_HTTP_STATUS) {
                            signInCreatedUser(newUser, true);
                        } else {
                            hideProgressDialog();
                            Toaster.longToast(R.string.sign_up_error);
                        }
                    }
                }
        );
    }

    private void loginToChat(final QBUser qbUser) {
        qbUser.setPassword(password);
        userForSave = qbUser;
        startLoginService(qbUser);
    }

    private void startLoginService(QBUser qbUser) {
        Intent tempIntent = new Intent(this, SignUpService.class);
        PendingIntent pendingIntent = createPendingResult(Consts.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
        SignUpService.start(this, qbUser, pendingIntent);
    }

    private void startNewActivity() {
        Log.d(TAG, "startNewActivity");
        hideProgressDialog();
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
        CustomSharedPreference.setUserLoginStatus(SignupActivity.this, "LoggedIn");
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void saveUserData(QBUser qbUser) {
        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        sharedPrefsHelper.save(Consts.PREF_CURREN_ROOM_NAME, qbUser.getTags().get(0));
        sharedPrefsHelper.saveQbUser(qbUser);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Consts.EXTRA_LOGIN_RESULT_CODE) {
            boolean isLoginSuccess = data.getBooleanExtra(Consts.EXTRA_LOGIN_RESULT, false);
            String errorMessage = data.getStringExtra(Consts.EXTRA_LOGIN_ERROR_MESSAGE);
            Log.d(TAG, "onActivityResult");
            if (isLoginSuccess) {
                saveUserData(userForSave);
                signInCreatedUser(userForSave, false);
            } else {
                Toaster.longToast("Error to SignUp " + errorMessage);
                _nameText.setText(userForSave.getFullName());
            }
        }
    }

    private void signInCreatedUser(final QBUser user, final boolean deleteCurrentUser) {

        requestExecutor.signInUser(user, new QBEntityCallbackImpl<QBUser>() {
            @Override
            public void onSuccess(QBUser result, Bundle params) {
                if (deleteCurrentUser) {
                    removeAllUserData(result);
                } else {
                    // Loading user list
                    Log.d(TAG, "signInUser");
                    startNewActivity();
                }
            }

            @Override
            public void onError(QBResponseException responseException) {
                hideProgressDialog();
                Toaster.longToast(R.string.sign_in_error_with_error);
            }
        });
    }

    private void removeAllUserData(final QBUser user) {
        requestExecutor.deleteCurrentUser(user.getId(), new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                UsersUtils.removeUserData(getApplicationContext());
                startSignUpNewUser(createUserWithEnteredData());
            }

            @Override
            public void onError(QBResponseException e) {
               hideProgressDialog();
                Toaster.longToast(R.string.sign_up_error);
            }
        });
    }

    private void startLoadUsers() {
        String currentRoomName = "TalkRemit";
        requestExecutor.loadUsersByTag(currentRoomName, new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> result, Bundle params) {
                Log.i("LoadUsers", "Friends: " + result.toString());
                dbManager.saveAllUsers(result, true);
                startNewActivity();
            }

            @Override
            public void onError(QBResponseException responseException) {
                showErrorSnackbar(R.string.loading_users_error, responseException, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startLoadUsers();
                    }
                });
            }
        });
    }

    protected void startOldUserLoginService(QBUser qbUser) {
        SignUpService.start(this, qbUser);
    }


    private QBUser createUserWithEnteredData() {
        return createQBUserWithCurrentData(name, mobile, password, mRoomName);
    }

    private QBUser createQBUserWithCurrentData(String userName, String mobile, String password, String chatRoomName) {
        QBUser qbUser = null;
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(chatRoomName) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(password)) {
            StringifyArrayList<String> userTags = new StringifyArrayList<>();
            userTags.add(chatRoomName);

            qbUser = new QBUser();
            qbUser.setFullName(userName);
            qbUser.setLogin(mobile);
            qbUser.setPassword(password);
            qbUser.setTags(userTags);
        }

        return qbUser;
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed for Wrong Data", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 4 || name.length() > 20) {
            _nameText.setError("should between 4 and 20 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        /*if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }*/

        if (mobile.isEmpty() || mobile.length() != 11) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 16) {
            _passwordText.setError("between 8 and 16 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 8 || reEnterPassword.length() > 16 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }
        return valid;
    }
}
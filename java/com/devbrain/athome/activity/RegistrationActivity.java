package com.devbrain.athome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devbrain.athome.modal.Device;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.rest.NetworkTransaction;
import com.devbrain.athome.rest.NetworkTransactionListener;
import com.devbrain.athome.rest.RestAPIURL;
import com.devbrain.athome.rest.TransactionType;
import com.devbrain.athome.R;
import com.devbrain.athome.views.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Honey Agarwal on 25-Apr-17.
 */

public class RegistrationActivity extends AppCompatActivity {

    EditText nameEditText, emailEditText;
    EditText phoneEditText, pinCodeEditText;
    EditText addressEditText, cityEditText;
//    EditText passCodeEditText;
    Button nextButton;
    CustomProgressDialog customProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameEditText = (EditText) findViewById(R.id.user_name_et);
        emailEditText = (EditText) findViewById(R.id.user_email_et);
        phoneEditText = (EditText) findViewById(R.id.user_phone_et);
//        passCodeEditText = (EditText) findViewById(R.id.user_password_et);
        addressEditText = (EditText) findViewById(R.id.user_address_et);
        cityEditText = (EditText) findViewById(R.id.user_city_et);
        pinCodeEditText = (EditText) findViewById(R.id.user_pin_code_et);
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFormVerified())
                    sendRegistrationDetails(RegistrationActivity.this);
            }
        });
        getUserDetails(RegistrationActivity.this);
    }

    public boolean isFormVerified() {
        if (isEditTextEmpty(nameEditText) && isEditTextEmpty(addressEditText)
                && isEditTextEmpty(cityEditText) && isEditTextEmpty(pinCodeEditText)
                && isValidMail(emailEditText) && isValidMobile(phoneEditText)
//                && isValidPassword(passCodeEditText)
                ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidMail(EditText editText) {
        String email = editText.getText().toString();
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            editText.setError("Enter Valid Email Id");
            return false;
        }
    }

    private boolean isValidMobile(EditText editText) {
        String phone = editText.getText().toString();
        if (android.util.Patterns.PHONE.matcher(phone).matches() && phone.length() == 10) {
            return true;
        } else {
            editText.setError("Enter Valid Phone Number");
            return false;
        }
    }

    private boolean isValidPassword(EditText editText) {
        String password = editText.getText().toString();
        if (password.length() >= 6) {
            return true;
        } else {
            editText.setError("Enter Valid Password");
            return false;
        }
    }


    public boolean isEditTextEmpty(EditText editText) {
        String strUserName = editText.getText().toString();

        if (TextUtils.isEmpty(strUserName)) {
            editText.setError("Cant be empty");
            return false;
        }
        return true;
    }

    public String getAddressString() {
        StringBuilder address = new StringBuilder();
        address.append(addressEditText.getText());
        address.append("," + cityEditText.getText());
        address.append("," + pinCodeEditText.getText());
        return address.toString();
    }

    public void setAddressString(String address) {
        if (address != null) {
            String[] addrStrings = address.split(",");
            int length = addrStrings.length;
            if (length > 2) {
                StringBuilder addrline = new StringBuilder();
                for (int i = 0; i < length - 2; i++) {
                    addrline.append(addrStrings[i]);
                }
                addressEditText.setText(addrline.toString());
                cityEditText.setText(addrStrings[length - 2]);
                pinCodeEditText.setText(addrStrings[length - 1]);
            }
        }
    }

    private void getUserDetails(final Context context) {
        customProgressBar = new CustomProgressDialog(this);
        if (!customProgressBar.isShowing()) {
            customProgressBar.show();
        }
        JSONObject jObjDeviceInfo = Device.getInstance(context).getDeviceRegistrationJSON();

        NetworkTransaction.getInstance(context).ProcessRequest(TransactionType.POST, RestAPIURL.USER_DETAILS_URL, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
                customProgressBar.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(object);
                    setAddressString(jsonObject.getString("address"));
                    nameEditText.setText(jsonObject.getString("name"));
                    emailEditText.setText(jsonObject.getString("emailID"));
                    phoneEditText.setText(jsonObject.getString("contactNumber"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
//                sharedPrefEditor.putString(CategoryJSONParser.JSON_CATEGORIES, object);
//                sharedPrefEditor.commit();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                customProgressBar.dismiss();
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED);
            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                customProgressBar.dismiss();
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                customProgressBar.dismiss();
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }
        });

    }


    private void sendRegistrationDetails(final Context context) {
        customProgressBar = new CustomProgressDialog(this);
        if (!customProgressBar.isShowing()) {
            customProgressBar.show();
        }
        JSONObject jObjDeviceInfo = Device.getInstance(context).getDeviceRegistrationJSON();
        try {
            jObjDeviceInfo.put("emailID", emailEditText.getText());
            jObjDeviceInfo.put("name", nameEditText.getText());
//            jObjDeviceInfo.put("password", passCodeEditText.getText());
            jObjDeviceInfo.put("contactNumber", phoneEditText.getText());
            jObjDeviceInfo.put("address", getAddressString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTransaction.getInstance(context).ProcessRequest(TransactionType.POST, RestAPIURL.REGISTRATION_URL, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
                customProgressBar.dismiss();
                Intent intent = new Intent();
                intent.putExtra("address", getAddressString());
                intent.putExtra("email", emailEditText.getText() + "");
                intent.putExtra("name", nameEditText.getText() + "");
                intent.putExtra("phone_no", phoneEditText.getText() + "");
                setResult(RESULT_OK, intent);
                finish();
//                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

//                sharedPrefEditor.putString(CategoryJSONParser.JSON_CATEGORIES, object);
//                sharedPrefEditor.commit();
            }

            @Override
            public void onFail(String object, Object obj) {
                Utility.printLog("onFail: " + object);
                customProgressBar.dismiss();
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();
                setResult(RESULT_CANCELED);
            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
                customProgressBar.dismiss();
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                customProgressBar.dismiss();
                Toast.makeText(context, object, Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}

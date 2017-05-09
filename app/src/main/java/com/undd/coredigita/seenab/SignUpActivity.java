package com.undd.coredigita.seenab;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    RadioGroup userType_Radio, gender_Radio;
    int checked, checked1;
    Button signUp;
    EditText fName, lName, email, pass, coPass, mobile, bio;
    String firstName, lastName, mail, password, coPassword, phone, userType, gender, biodata;
    private static final String URL = "http://seenab.coredigita.com/ajax_pages/register_user.php";
    private static final String KEY_FNAME = "first_name";
    private static final String KEY_LNAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private static final String KEY_CON = "contact";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_USERTYPE = "user_type";
    private static final String KEY_BIO = "bio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fName = (EditText) findViewById(R.id.editT_fname);
        lName = (EditText) findViewById(R.id.editT_lName);
        email = (EditText) findViewById(R.id.editT_Email);
        pass = (EditText) findViewById(R.id.editT_pass);
        coPass = (EditText) findViewById(R.id.editT_copass);
        mobile = (EditText) findViewById(R.id.editT_mobile);
        bio = (EditText) findViewById(R.id.editT_bio);
        signUp = (Button) findViewById(R.id.btn_signUp);
        userType_Radio = (RadioGroup) findViewById(R.id.userType);
        gender_Radio = (RadioGroup) findViewById(R.id.radio_gender);
        gender_Radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group1, int checkedId1) {
                checked = userType_Radio.indexOfChild(findViewById(checkedId1));
                switch (checked1) {
                    case 0:
                        gender = "Male";
                        break;
                    case 1:
                        gender = "Female";
                        break;
                }
            }
        });
        userType_Radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checked = userType_Radio.indexOfChild(findViewById(checkedId));
                switch (checked) {
                    case 0:
                        userType = "Employer";
                        break;
                    case 1:
                        userType = "Employee";
                        break;
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
                if (!validation_SignUp()){

                } else {
                    postRegister();
                }
            }
        });
    }
    public void postRegister(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_FNAME, firstName);
                params.put(KEY_LNAME, lastName);
                params.put(KEY_EMAIL, mail);
                params.put(KEY_PASS, password);
                params.put(KEY_CON, phone);
                params.put(KEY_GENDER, gender);
                params.put(KEY_USERTYPE, userType);
                params.put(KEY_BIO, biodata);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void initialize(){
        firstName = fName.getText().toString().trim();
        lastName = lName.getText().toString().trim();
        mail = email.getText().toString().trim();
        password = pass.getText().toString().trim();
        phone = mobile.getText().toString().trim();
        biodata = bio.getText().toString().trim();
        coPassword = coPass.getText().toString().trim();
    }

    public boolean validation_SignUp(){
        Boolean valid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int userType_chkid = userType_Radio.getCheckedRadioButtonId();
        int gender_chkid = gender_Radio.getCheckedRadioButtonId();
        if (firstName.isEmpty()){
            fName.setError("Enter your First Name");
            valid = false;
        }
        if (lastName.isEmpty()){
            lName.setError("Enter your Last Name");
            valid = false;
        }
        if (mail.isEmpty()){
            email.setError("Enter your Emial-ID");
            valid = false;
        }
        if (password.isEmpty()){
            pass.setError("Enter your password");
            valid = false;
        }
        if (biodata.isEmpty()){
            bio.setError("Write your biodata here");
            valid = false;
        }
        if (phone.isEmpty()){
            mobile.setError("Give your Mobile number");
            valid = false;
        }
        if (phone.length()!= 10) {
            mobile.setError("Mobile number should be 10 digits");
            valid = false;
        }
        if (!coPassword.equals(password)){
            coPass.setError("Password doesn't not matched");
            valid = false;
        }
        if (!mail.matches(emailPattern)){
            email.setError("Invalid Email-ID");
            valid = false;
        }
        if (userType_chkid == -1){
            Toast.makeText(SignUpActivity.this,"Select Employer or Employee",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (gender_chkid == -1){
            Toast.makeText(SignUpActivity.this,"Select Male or Female",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
}

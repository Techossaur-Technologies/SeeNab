package com.undd.coredigita.seenab;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class WelcomeActivity extends AppCompatActivity {

    String url = "http://seenab.coredigita.com/ajax_pages/dashboard_moderator.php";
    LinearLayout myLinearLayout;
    Button btn_signIn;
    TextView tv_signUp;
    private boolean loggedIn = false;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        myLinearLayout = (LinearLayout)findViewById(R.id.layout_moderator);
        btn_signIn = (Button)findViewById(R.id.bt_signIn);
        tv_signUp = (TextView)findViewById(R.id.tv_signUp);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LogInActivity.class));
            }
        });
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
            }
        });
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.optJSONArray("Data");
                            List<Button> buttons = new ArrayList<Button>(jsonArray.length());
                            final Intent intent = new Intent(WelcomeActivity.this, ModerateActivity.class);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                final String moderator_name = jsonObject.optString("moderator_name").toString();
                                final int moderator_id = Integer.parseInt(jsonObject.optString("moderator_id").toString());
                                Button button = new Button(WelcomeActivity.this);
                                button.setId(moderator_id);
                                button.setText(moderator_name);
//                                button.setBackgroundColor(0xff0000ff);
                                myLinearLayout.addView(button);
                                buttons.add(button);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent.putExtra("id", moderator_id);
                                        intent.putExtra("name", moderator_name);
                                        startActivity(intent);
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(WelcomeActivity.this, UsersActivity.class);
            startActivity(intent);
        }
    }
}

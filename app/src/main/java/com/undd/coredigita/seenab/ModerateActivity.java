package com.undd.coredigita.seenab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class ModerateActivity extends AppCompatActivity {
    TextView tv_header;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderate);
        tv_header = (TextView)findViewById(R.id.tv_noticeHeader);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
//            id = bundle.getInt("id");
//            tv_header.setText(""+id);
            String name = bundle.getString("name");
            tv_header.setText(name+"'s Notification:");

        }
        String url = "http://seenab.coredigita.com/ajax_pages/moderator_messages.php?id="+id;

    }
}

package com.android.rahul.findme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button mGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mGetStarted = (Button)findViewById(R.id.get_started);
        mGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "REQUEST TO SHARE YOUR LOCATION. CLICK ON THE LINK TO ACCEPT TO SHARE YOUR LOCATION. http://open.findme.app/launch");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
}

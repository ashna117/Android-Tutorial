package com.ashna.sampleintent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        String nameOfSubject=getIntent().getStringExtra("subj");
        Toast.makeText(AnotherActivity.this,nameOfSubject, Toast.LENGTH_SHORT).show();
    }

    public void prev(View view) {
        Intent prevIntent=new Intent(this,MainActivity.class);
        startActivity(prevIntent);
    }
}

package com.helios.whatstheweather.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.helios.whatstheweather.R;
import com.helios.whatstheweather.helpers.CustomSharedPreference;
import com.helios.whatstheweather.helpers.Helper;

public class ChangeLocationActivity extends AppCompatActivity {

    private AutoCompleteTextView cityactv;
    private Button change;
    private CustomSharedPreference sharedPreference;
    private String[] cities = {"Dubai", "Venice", "Tokyo", "Seoul", "Singapore", "Beijing", "Kathmandu"};
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        setTitle(Helper.MANAGER_CITY);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        cityactv = (AutoCompleteTextView) findViewById(R.id.cityAutoCompleteTextView);
        change = (Button) findViewById(R.id.change_location);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,cities);
        cityactv.setAdapter(adapter);
        cityactv.setThreshold(1);

        sharedPreference = new CustomSharedPreference(ChangeLocationActivity.this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
            }

            return super.onOptionsItemSelected(item);
        }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // to simulate "restart" of the activity.

    }

    public void saveCity(View view) {
        String enteredLocation = cityactv.getText().toString();

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityactv.getWindowToken(), 0);

        if (TextUtils.isEmpty(enteredLocation)) {
            Toast.makeText(ChangeLocationActivity.this, Helper.LOCATION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            for(int i=0; i<cities.length; i++) {
                if (enteredLocation.equals(cities[i])) {
                    flag = 1;
                    sharedPreference.setLocationInPreference(cityactv.getText().toString());
                    Intent intent = new Intent(ChangeLocationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            if(flag == 0)
                Toast.makeText(ChangeLocationActivity.this, Helper.LOCATION_VALIDATION_MESSAGE, Toast.LENGTH_SHORT).show();
            Log.d("flag",flag+"");

        }

    }

    public void goCurrentLocation(View view) {
        sharedPreference.setLocationInPreference("");
        Intent intent = new Intent(ChangeLocationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

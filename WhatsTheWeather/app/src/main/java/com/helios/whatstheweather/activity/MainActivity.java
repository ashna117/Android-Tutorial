package com.helios.whatstheweather.activity;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.pavlospt.CircleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.helios.whatstheweather.AppController;
import com.helios.whatstheweather.R;
import com.helios.whatstheweather.entity.WeatherObject;
import com.helios.whatstheweather.helpers.CustomSharedPreference;
import com.helios.whatstheweather.helpers.Helper;
import com.helios.whatstheweather.json.FiveWeathers;
import com.helios.whatstheweather.json.Forecast;
import com.helios.whatstheweather.json.LocationMapObject;
import com.helios.whatstheweather.adapters.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private RecyclerView recyclerView;
    private TextView cityCountry;
    private TextView currentDate;
    private ImageView weatherImage;
    private CircleView circleTitle;
    private TextView windResult;
    private TextView humidityResult;
    private LocationManager locationManager;
    private final int REQUEST_LOCATION = 200;
    private Location location;
    private double longitude;
    private double latitude;
    private String apiUrl;
    private String storedCityName;
    private LocationMapObject locationMapObject;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String isLocationSaved;
    private CustomSharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityCountry = (TextView) findViewById(R.id.city_country);
        currentDate = (TextView) findViewById(R.id.current_date);
        weatherImage = (ImageView) findViewById(R.id.weather_icon);
        circleTitle = (CircleView) findViewById(R.id.weather_result);
        windResult = (TextView) findViewById(R.id.wind_result);
        humidityResult = (TextView) findViewById(R.id.humidity_result);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        sharedPreference = new CustomSharedPreference(MainActivity.this);
        isLocationSaved = sharedPreference.getLocationInPreference();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            if (isLocationSaved.equals("")) {
                // make API call with longitude and latitude
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&APPID=caa1dfd44d096f6b4d9fc03c9d0fd285&units=metric";
                    makeJsonObject(apiUrl);
                }
            } else {
                // make API call with city name
                storedCityName = sharedPreference.getLocationInPreference();
                if (storedCityName != null && !storedCityName.equals("")) {
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + storedCityName + "&APPID=caa1dfd44d096f6b4d9fc03c9d0fd285&units=metric";
                    makeJsonObject(url);
                }
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 5);
        recyclerView = (RecyclerView) findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_newcity) {
            Intent intent = new Intent(MainActivity.this, ChangeLocationActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.show_map) {

            Intent intent = new Intent(MainActivity.this, ShowMapActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("city", storedCityName);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //make api call
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&APPID=caa1dfd44d096f6b4d9fc03c9d0fd285&units=metric";
                        makeJsonObject(apiUrl);
                    }else{
                        apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=90.41&lon=23.71&APPID=62f6de3f7c0803216a3a13bbe4ea9914&units=metric";
                        makeJsonObject(apiUrl);
                    }
                }
            }else{
                Toast.makeText(MainActivity.this, getString(R.string.permission_notice), Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }

    private String convertTimeToDay(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault());
        String days = "";
        try {
            Date date = format.parse(time);
            System.out.println("Our time " + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            days = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            System.out.println("Our time " + days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    private void fiveDaysApiJsonObjectCall(String city){
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q="+city+ "&APPID=caa1dfd44d096f6b4d9fc03c9d0fd285&units=metric";
        final List<WeatherObject> daysOfTheWeek = new ArrayList<WeatherObject>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Forecast forecast = gson.fromJson(response, Forecast.class);
                if (null == forecast) {
                    Toast.makeText(getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Response Good 2", Toast.LENGTH_LONG).show();
                    int[] everyday = new int[]{0,0,0,0,0,0,0};
                    List<FiveWeathers> weatherInfo = forecast.getList();
                    for(int i = 0; i < weatherInfo.size(); i++){
                        String time = weatherInfo.get(i).getDt_txt();
                        String shortDay = convertTimeToDay(time);
                        String temp = weatherInfo.get(i).getMain().getTemp();
                        String tempMin = weatherInfo.get(i).getMain().getTemp_min();
                        if(convertTimeToDay(time).equals("Mon") && everyday[0] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[0] = 1;
                        }
                        if(convertTimeToDay(time).equals("Tue") && everyday[1] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[1] = 1;
                        }
                        if(convertTimeToDay(time).equals("Wed") && everyday[2] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[2] = 1;
                        }
                        if(convertTimeToDay(time).equals("Thu") && everyday[3] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[3] = 1;
                        }
                        if(convertTimeToDay(time).equals("Fri") && everyday[4] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[4] = 1;
                        }
                        if(convertTimeToDay(time).equals("Sat") && everyday[5] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[5] = 1;
                        }
                        if(convertTimeToDay(time).equals("Sun") && everyday[6] < 1){
                            daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                            everyday[6] = 1;
                        }
                        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, daysOfTheWeek);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void makeJsonObject(final String apiUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                locationMapObject = gson.fromJson(response, LocationMapObject.class);
                if (null == locationMapObject) {
                    Toast.makeText(getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Response Good 1", Toast.LENGTH_LONG).show();
                    String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
                    String todayDate = getTodayDateInStringFormat();
                    Long tempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
                    String weatherTemp = String.valueOf(tempVal) + "°";
                    String weatherDescription = Helper.capitalizeFirstLetter(locationMapObject.getWeather().get(0).getDescription());
                    String windSpeed = locationMapObject.getWind().getSpeed();
                    String humidityValue = locationMapObject.getMain().getHumudity();

                    // populate View data
                    cityCountry.setText(city);
                    currentDate.setText(todayDate);
                    circleTitle.setTitleText(weatherTemp.toString());
                    circleTitle.setSubtitleText(weatherDescription.toString());
                    windResult.setText(windSpeed + " km/h");
                    humidityResult.setText(humidityValue + " %");
                    Log.e("Name",locationMapObject.getName());
                    fiveDaysApiJsonObjectCall(locationMapObject.getName());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

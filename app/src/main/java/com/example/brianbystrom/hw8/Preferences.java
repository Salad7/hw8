package com.example.brianbystrom.hw8;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import static com.example.brianbystrom.hw8.MainActivity.MyPREFERENCES;

/**
 * Created by msalad on 4/8/2017.
 */

public class Preferences extends AppCompatActivity implements GetCityAsync.IData, GetCurrentWeatherAsync.IData{


    EditText setCityET;
     EditText setCountryET;
     RadioButton celc;
   RadioButton far ;
    Button currentCity;
    Button tempUnit;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setCityET = new EditText(Preferences.this);
setCityET.setHint("City Name (i.e. Charlotte)");
        setCountryET = new EditText(Preferences.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setCountryET.setHint("Country name (i.e. US)");

        celc = new RadioButton(Preferences.this);
        celc.setText("Celcius");
       far = new RadioButton(Preferences.this);
        far.setText("Fahrenheit");
        currentCity = (Button) findViewById(R.id.btn_current_city) ;
        tempUnit = (Button) findViewById(R.id.btn_tempunit);
        tempUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Preferences.this);
                builder.setTitle("Enter city details:");
                AlertDialog dialog = builder.create();
                LinearLayout ll = new LinearLayout(Preferences.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.removeAllViews();
                ll.addView(celc);
                ll.addView(far);
                builder.setView(ll);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(celc.isChecked()){
                            Log.d("check","Celcius is checked");////
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove("F");
                            editor.commit();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);

                        }
                        else if(far.isChecked()){
                            Log.d("check","Far is checked");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean("F",true);
                            editor.commit();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


                dialog = builder.create();
                dialog.show();
            }
        });


        currentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Preferences.this);
        builder.setTitle("Enter city details:");
        AlertDialog dialog = builder.create();
        LinearLayout ll = new LinearLayout(Preferences.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.removeAllViews();
        ll.addView(setCityET);
        ll.addView(setCountryET);
        builder.setView(ll);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //setCity.setValue(setCityET.getText().toString());
                //setCountry.setValue(setCountryET.getText().toString());

                String cityURL = "http://dataservice.accuweather.com/locations/v1/"+setCountryET.getText().toString()+"/search?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q="+setCityET.getText().toString();
                new GetCityAsync(Preferences.this).execute(cityURL);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });


        dialog = builder.create();
        dialog.show();
            }
        });
    }

    public void setupData(ArrayList<City> s) {
        Log.d("hit","inside setupData");
        //After city data comes back, sets it to Shared preferences

        if(s.size() > 0) {

            City city = s.get(0);
            Log.d("CITY", s.get(0).getKey() + " | " + s.get(0).getName() + " | " + s.get(0).getCountry());
            Toast.makeText(this, "City set.", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedpreferences.edit();

            String cityString;
            cityString = city.getKey() + "|" + city.getName() + "|" + city.getCountry();
            Log.d("CITYSTRING", cityString);
            editor.putString("CURRENT CITY", cityString);
            editor.commit();

            //Shows progress bar and attempts to pull back data
            //showProgressBar();
            String weatherURL = "http://dataservice.accuweather.com/currentconditions/v1/"+s.get(0).getKey()+"?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
            Log.d("URLd", weatherURL);

            Log.d("added","added");
            Log.d("URL", weatherURL);
            new GetCurrentWeatherAsync(Preferences.this).execute(weatherURL);


        } else {
            Log.d("CITY","NO CITY FOUND");
            Toast.makeText(this, "No city found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void currentWeather(ArrayList<Weather> s) {

    }

}

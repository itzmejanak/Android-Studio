package com.weather.SkyScout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;  // Use this import
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    SearchView search;
    RecyclerView rcv;

    ArrayList<HourlyModel> weatherList;
    Button next_button;
    RequestQueue requestQueue;
    GetCity cityFinder;

    // Views
    TextView temeperature, updating;
    TextView textView2;
    TextView wind_speed;
    TextView humidity_level;
    TextView rain_probability;
    ImageView weather_condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        search = findViewById(R.id.search_view);
        search.clearFocus();
        rcv = findViewById(R.id.recycler_view);
        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        weatherList = new ArrayList<>();

        rcv.setAdapter(new HourlyAdapter(this, weatherList));

        next_button = findViewById(R.id.next_button);
        next_button.setOnClickListener(this);

        populateWeatherList();

        // initilize variables
        temeperature = findViewById(R.id.temeperature);
        textView2 = findViewById(R.id.textView2);
        wind_speed = findViewById(R.id.wind_speed);
        humidity_level = findViewById(R.id.humidity_level);
        rain_probability = findViewById(R.id.rain_probability);
        weather_condition = findViewById(R.id.weather_condition);
        cityFinder = new GetCity("", this);

        //animation views
        updating = findViewById(R.id.updating);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearchQuery(query);
                if (!query.isEmpty()) {
                    cityFinder.setCity_name(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void handleSearchQuery(String query) {
        if(!query.isEmpty()) {
            fetchWeatherData(query);
            updating.setText("Searching");
            AnimationUtils.fadeOut(MainActivity.this, updating);
        }else {
            Toast.makeText(this, "Empty searched", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) {
            startActivity(new Intent(MainActivity.this, NextActivity.class));
        }
    }

    private void fetchWeatherData(String city) {
        cityFinder = new GetCity(city, this);
        WeatherChecker weatherStatus = new WeatherChecker();

        cityFinder.findLatLon(new GetCity.LatLonCallback() {
            @Override
            public void onLatLonReceived(String lat, String lon) {
                String currentWeatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current=temperature_2m,relative_humidity_2m,is_day,weather_code,wind_speed_10m";
                String hourlyWeatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&hourly=temperature_2m,weathercode,precipitation,wind_speed_10m,relative_humidity_2m";

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                // Request for current weather data
                JsonObjectRequest currentWeatherRequest = new JsonObjectRequest(Request.Method.GET, currentWeatherUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    updateCurrentWeather(response, weatherStatus);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                error.printStackTrace();
                            }
                        });

                // Request for hourly weather data
                JsonObjectRequest hourlyWeatherRequest = new JsonObjectRequest(Request.Method.GET, hourlyWeatherUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    updateHourlyWeather(response, weatherStatus);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                error.printStackTrace();
                            }
                        });

                // Add both requests to the queue
                queue.add(currentWeatherRequest);
                queue.add(hourlyWeatherRequest);
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }


    private void updateCurrentWeather(JSONObject response, WeatherChecker weatherStatus) throws JSONException {
        JSONObject current = response.getJSONObject("current");

        double temp = current.getDouble("temperature_2m");
        int humidity = current.getInt("relative_humidity_2m");
        double windSpeed = current.getDouble("wind_speed_10m");
        int weatherCode = current.getInt("weather_code");

        temeperature.setText(String.format("%.0f°", temp));
        textView2.setText(weatherStatus.getWeatherName(weatherCode));
        wind_speed.setText(String.format("%.1f km/h", windSpeed));
        humidity_level.setText(String.format("%d %%", humidity));
        int img = weatherStatus.weatherImage(weatherCode);
        weather_condition.setImageResource(img);
        AnimationUtils.fadeIn(this, temeperature, textView2, wind_speed, humidity_level, weather_condition);
        AnimationUtils.fadeIn(MainActivity.this, updating);
        updating.setText(cityFinder.getCity_name());
    }

    private void updateHourlyWeather(JSONObject response, WeatherChecker weatherStatus) throws JSONException {
        JSONObject hourly = response.getJSONObject("hourly");
        JSONArray times = hourly.getJSONArray("time");
        JSONArray temperatures = hourly.getJSONArray("temperature_2m");
        JSONArray weatherCodes = hourly.getJSONArray("weathercode");
        JSONArray precipitations = hourly.getJSONArray("precipitation");
        JSONArray windSpeeds = hourly.getJSONArray("wind_speed_10m");
        JSONArray humidities = hourly.getJSONArray("relative_humidity_2m");

        weatherList.clear(); // Clear previous data

        for (int i = 0; i < times.length(); i++) {
            String time = times.getString(i);
            String formattedTime = formatTime(time);
            if(formattedTime.equals("00:00")){
                formattedTime = "24:00";
            }
            double temp = temperatures.getDouble(i);
            int weatherCode = weatherCodes.getInt(i);
            double precipitation = precipitations.getDouble(i);
            double windSpeed = windSpeeds.getDouble(i);
            int humidity = humidities.getInt(i);
            WeatherChecker weatherStatu = new WeatherChecker();
            int img = weatherStatu.weatherImage(weatherCode);


            String tempText = String.format("%.0f°C", temp);
            weatherList.add(new HourlyModel(tempText, formattedTime, img));
        }

        rcv.getAdapter().notifyDataSetChanged(); // Notify adapter about data changes
    }


    private String formatTime(String time) {
        // Extract the hour from the ISO8601 time string (e.g., "2024-06-24T00:00")
        String hour = time.substring(11, 16);
        return hour;
    }

    private void populateWeatherList() {
        weatherList.add(new HourlyModel("25°C", "10:00", R.drawable.sunny));
        weatherList.add(new HourlyModel("18°C", "11:00", R.drawable.cloudy));
        weatherList.add(new HourlyModel("20°C", "12:00", R.drawable.rainy));
        weatherList.add(new HourlyModel("22°C", "01:00", R.drawable.thunder));
        weatherList.add(new HourlyModel("15°C", "02:00", R.drawable.thunder2));
        weatherList.add(new HourlyModel("28°C", "03:00", R.drawable.sunny));
        weatherList.add(new HourlyModel("10°C", "04:00", R.drawable.snowy));
        weatherList.add(new HourlyModel("19°C", "05:00", R.drawable.foggy));
        weatherList.add(new HourlyModel("23°C", "06:00", R.drawable.cloudy));
        weatherList.add(new HourlyModel("21°C", "07:00", R.drawable.snowy));
        weatherList.add(new HourlyModel("17°C", "08:00", R.drawable.sunny));
        weatherList.add(new HourlyModel("24°C", "09:00", R.drawable.rainy));
        weatherList.add(new HourlyModel("16°C", "10:00", R.drawable.thunder2));
    }

}

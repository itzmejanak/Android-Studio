package com.weather.SkyScout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NextActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rcv_next;
    ImageView back_btn;
    ArrayList<SevenDaysDataModel> data;
    GetCity cityName;
    RequestQueue queue;
    SevenDaysAdapter adapter;
    ImageView weather_image;
    TextView date_day, temp, weather_names, wind_speed_next, humidity_level_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Apply insets listener to adjust padding based on system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        data = new ArrayList<>();
        rcv_next = findViewById(R.id.recycler_view_next);
        rcv_next.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new SevenDaysAdapter(this,data);
        rcv_next.setAdapter(adapter);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);

        weather_image = findViewById(R.id.weather_image);
        date_day = findViewById(R.id.date);
        temp = findViewById(R.id.temp);
        weather_names = findViewById(R.id.weather_names);
        wind_speed_next = findViewById(R.id.wind_speed_next);
        humidity_level_next = findViewById(R.id.humidity_level_next);

        queue = Volley.newRequestQueue(NextActivity.this);

        if (isNetworkAvailable()) {
            String city = new GetCity(this).getCity_name();
            if (city.isEmpty()) {
                Toast.makeText(this, "Search First \uD83D\uDD0D", Toast.LENGTH_LONG).show();
            }else {
                fetchSevenDaysData(city);
                Toast.makeText(this, "" + CapitalizeFirstLetter.capitalizeFirstLetter(city), Toast.LENGTH_SHORT).show();
            }
        } else {
            DemoData();
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void fetchSevenDaysData(String city) {
        data.clear();
        GetCity cityFinder = new GetCity(city, this);
        WeatherChecker weatherStatus = new WeatherChecker();

        cityFinder.findLatLon(new GetCity.LatLonCallback() {
            @Override
            public void onLatLonReceived(String lat, String lon) {
                String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon +
                        "&daily=temperature_2m_max,temperature_2m_min,weathercode,precipitation_sum,wind_speed_10m_max,relative_humidity_2m_max";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject daily = response.getJSONObject("daily");
                                    tomrrowData(daily);

                                    JSONArray times = daily.getJSONArray("time");
                                    JSONArray maxTemps = daily.getJSONArray("temperature_2m_max");
                                    JSONArray minTemps = daily.getJSONArray("temperature_2m_min");
                                    JSONArray weatherCodes = daily.getJSONArray("weathercode");

                                    for (int i = 0; i < times.length(); i++) {
                                        String day = getDayOfWeek(times.getString(i)); // Convert date to day of the week
                                        int img = weatherStatus.weatherImage(weatherCodes.getInt(i));
                                        String weatherName = weatherStatus.getWeatherName(weatherCodes.getInt(i));
                                        String maxTemp = String.format("%.0f°C", maxTemps.getDouble(i));
                                        String minTemp = String.format("%.0f°C", minTemps.getDouble(i));

                                        data.add(new SevenDaysDataModel(day, img, weatherName, maxTemp, minTemp));
                                    }

                                    // Notify adapter that data has changed
                                    adapter.notifyDataSetChanged();

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

                queue.add(jsonObjectRequest);
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }

    private String getDayOfWeek(String date) {
        // Convert date (e.g., "2024-06-24") to day of the week (e.g., "Mon")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dateObj = format.parse(date);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
            return dayFormat.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_btn) {
            finish();
        }
    }

    void DemoData() {
        data.add(new SevenDaysDataModel("Mon", R.drawable.sunny, "Sunny", "30°C", "20°C"));
        data.add(new SevenDaysDataModel("Tue", R.drawable.cloudy, "Cloudy", "28°C", "18°C"));
        data.add(new SevenDaysDataModel("Wed", R.drawable.rainy, "Rainy", "25°C", "17°C"));
        data.add(new SevenDaysDataModel("Thu", R.drawable.stormy, "Stormy", "22°C", "16°C"));
        data.add(new SevenDaysDataModel("Fri", R.drawable.snowy, "Snowy", "0°C", "-5°C"));
        data.add(new SevenDaysDataModel("Sat", R.drawable.windy, "Windy", "20°C", "15°C"));
        data.add(new SevenDaysDataModel("Sun", R.drawable.foggy, "Foggy", "18°C", "14°C"));
    }

    private void tomrrowData(JSONObject daily) {
        try {
            JSONArray times = daily.getJSONArray("time");
            JSONArray maxTemps = daily.getJSONArray("temperature_2m_max");
            JSONArray minTemps = daily.getJSONArray("temperature_2m_min");
            JSONArray weatherCodes = daily.getJSONArray("weathercode");
            JSONArray windSpeeds = daily.getJSONArray("wind_speed_10m_max");
            JSONArray humidityLevels = daily.getJSONArray("relative_humidity_2m_max");

            int index = 1; // Tomorrow's data is at index 1
            String date = getDayDate(times.getString(index));
            int weatherCode = weatherCodes.getInt(index);
            String weatherName = new WeatherChecker().getWeatherName(weatherCode);
            String maxTemp = String.format("%.0f°", maxTemps.getDouble(index));
            String minTemp = String.format("%.0f°", minTemps.getDouble(index));
            String windSpeed = String.format("%.1f km/h", windSpeeds.getDouble(index));
            String humidity = String.format("%d%%", humidityLevels.getInt(index));

            date_day.setText(date);
            weather_image.setImageResource(new WeatherChecker().weatherImage(weatherCode));
            temp.setText("/"+maxTemp);
            weather_names.setText(weatherName);
            wind_speed_next.setText(windSpeed);
            humidity_level_next.setText(humidity);
            AnimationUtils.fadeIn(this, date_day,weather_image,temp,weather_names,wind_speed_next,humidity_level_next);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getDayDate(String date) {
        // Convert date (e.g., "2024-06-24") to just the day (e.g., "24")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dateObj = format.parse(date);
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
            return dayFormat.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}

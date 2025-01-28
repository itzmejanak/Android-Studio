package com.weather.SkyScout;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetCity {
    private static final String TAG = "GetCity";
    static public String city_name;
    private String lat;
    private String lon;
    private RequestQueue queue;
    private Context context;

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public interface LatLonCallback {
        void onLatLonReceived(String lat, String lon);
        void onError(String error);
    }

    public GetCity(String city_name, Context context) {
        this.city_name = city_name;
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }
    public GetCity(Context context){
        this.context = context;
    }

    public String getCity_name() {
        return this.city_name;
    }

    public void findLatLon(LatLonCallback callback) {
        String baseUrl = "https://api.openweathermap.org/data/2.5/find";
        String apiKey = "5a90b7a5849ad2368826e2e7c5806677";
        String url = baseUrl + "?q=" + city_name + "&appid=" + apiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if the response contains the "list" array
                            if (response.has("list")) {
                                JSONArray cityList = response.getJSONArray("list");
                                if (cityList.length() > 0) {
                                    // Get the first city in the list
                                    JSONObject cityInfo = cityList.getJSONObject(0);
                                    JSONObject coord = cityInfo.getJSONObject("coord");
                                    lat = coord.getString("lat");
                                    lon = coord.getString("lon");
                                    callback.onLatLonReceived(lat, lon);
                                } else {
                                    callback.onError("City not found in the response.");
                                }
                            } else {
                                callback.onError("Invalid response format: 'list' not found.");
                            }
                        } catch (JSONException e) {
                            callback.onError("JSON parsing error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError("Network error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }
}

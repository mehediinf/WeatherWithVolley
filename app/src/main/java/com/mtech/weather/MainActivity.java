package com.mtech.weather;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tVWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tVWeatherData = findViewById(R.id.tVId);
        String API_END_POINT = "https://api.openweathermap.org/data/2.5/weather?q=gazipur&appid=230b7c0afce588d4f27e852c1a1a24d7&units=metric";

        // Response.Listener successListener - successful response handling
        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJson(response); // Parse JSON data
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON Parsing Error", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Response.ErrorListener errorListener - error response handling
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        // StringRequest - Requesting data from API
        StringRequest request = new StringRequest(Request.Method.GET, API_END_POINT, successListener, errorListener);

        // Adding request to queue
        Volley.newRequestQueue(this).add(request);
    }

    // Parse JSON data from response
    private void parseJson(String response) throws JSONException {
        JSONObject root = new JSONObject(response);

        // Extracting weather data from JSON
        String base = root.getString("base");
        String cityName = root.getString("name");

        // Temperature details
        JSONObject mainObject = root.getJSONObject("main");
        String temp = mainObject.getString("temp");
        String minTemp = mainObject.getString("temp_min");
        String maxTemp = mainObject.getString("temp_max");

        // Weather description
        JSONArray weatherArray = root.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        String description = weatherObject.getString("description");

        // Displaying parsed data in TextView
        tVWeatherData.setText("Base: " + base + "\nCity Name: " + cityName + "\nTemp: " + temp + "°C\nMin Temp: " + minTemp + "°C\nMax Temp: " + maxTemp + "°C\nDescription: " + description);
    }
}

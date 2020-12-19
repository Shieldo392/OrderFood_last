package weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.project_ad41_qlnh.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import static android.text.Html.fromHtml;

public class fragment_weather extends Fragment {

    com.example.project_ad41_qlnh.databinding.ActivityFragmentWeatherBinding binding;

    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";
    private static final String OPEN_WEATHER_MAP_API = "2ae7e6a9303eec04e37be1839c808dd3";
    Typeface weatherFont;
    static String latitue;
    static String longitude;


    public static fragment_weather newInstance() {

        Bundle args = new Bundle();

        fragment_weather fragment = new fragment_weather();
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_weather, container, false);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestPermissions();

        FusedLocationProviderClient mFusedLocationProviderClient;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions();

        }
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitue = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());


                    weatherFont = Typeface.createFromAsset(getContext().getAssets(), "font/weathericons-regular-webfont.ttf");
                    binding.weatherIcon.setTypeface(weatherFont);

                    String[] jsonData = getJONReponsed();

                    binding.cityField.setText(jsonData[0]);
                    binding.detailsField.setText(jsonData[1]);
                    binding.tempField.setText(jsonData[2]);
                    binding.humidityField.setText(getString(R.string.humidity) +jsonData[3]);
                    binding.pressureField.setText("Pressure: " + jsonData[4]);
                    binding.updateOnField.setText(jsonData[5]);
                    binding.weatherIcon.setText(fromHtml(jsonData[6]));
                }
            }
        });



        return binding.getRoot();
    }


    private String[] getJONReponsed() {
        String[] jsonData = new String[7];
        JSONObject jsonWeather = null;

        try {
            jsonWeather = getWeatherJSON(latitue, longitude);

        } catch (Exception e) {
            Log.d("Error", "Không thể xấc đinh được vị trí");
        }
        try {
            if (jsonWeather != null) {
                JSONObject details = jsonWeather.getJSONArray("weather").getJSONObject(0);
                JSONObject main = jsonWeather.getJSONObject("main");
                DateFormat df = DateFormat.getDateInstance();

                String city = jsonWeather.getString("name") + "," + jsonWeather.getJSONObject("sys").getString("country");
                String decription = details.getString("description");
                double temp = main.getDouble("temp") - 273;
                String temperature= String.format("%.0f", temp) + "°";
                String humidity = main.getString("humidity") + "%";
                String pressure = main.getString("pressure") + "hPa";
                String updatedOn = df.format(new Date(jsonWeather.getLong("dt") * 1000));
                String iconText = setWeatherIcon(details.getInt("id"), jsonWeather.getJSONObject("sys").getLong("sunrise")*1000,
                        jsonWeather.getJSONObject("sys").getLong("sunset")*1000);
                jsonData[0] = city;
                jsonData[1] = decription;
                jsonData[2] = temperature;
                jsonData[3] = humidity;
                jsonData[4] = pressure;
                jsonData[5] = updatedOn;
                jsonData[6] = iconText;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonData;
    }

    private static String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "&#f00d;";

            } else {
                icon = "&#f02e;";
            }
        } else {
            switch (id) {
                case 2:
                    icon = "&#xf01e;";
                    break;
                case 3:
                    icon = "&#xf01c;";
                    break;
                case 7:
                    icon = "&#xf014;";
                    break;
                case 8:
                    icon = "&#xf013;";
                    break;
                case 6:
                    icon = "&#xf01b;";
                    break;
                case 5:
                    icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }

    private static JSONObject getWeatherJSON(String latitue, String longitude) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, latitue, longitude, OPEN_WEATHER_MAP_API));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer json = new StringBuffer(0x400);
            String tmp = "";
            while ((tmp = reader.readLine()) != null) {
                json.append(tmp).append("\n");

            }
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if (data.getInt("cod") != 200) {
                return null;
            }
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

}
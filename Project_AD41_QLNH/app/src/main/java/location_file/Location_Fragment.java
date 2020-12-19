package location_file;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityLocationFragmentBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Home.Home;

public class Location_Fragment extends Fragment implements LocationListener {
    ActivityLocationFragmentBinding binding;
    private static final String TAG = Home.class.getSimpleName();
    private PermissionsRequestor permissionsRequestor;
    private MapView mapView;
    private MapObjects mapObjectsExample;
    private GeoCoordinates current;
    private List<GeoCoordinates> geoCoordinatesList;

    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";
    private static final String OPEN_WEATHER_MAP_API = "2ae7e6a9303eec04e37be1839c808dd3";
    Typeface weatherFont;
    static String latitue;
    static String longitude;

    PlatformPositioningProvider platformPositioningProvider;

    String linkURL = DeFile.URL;
    String result = "";

    List<Location_Object> resDesList;
    boolean flag = false;


    public static Location_Fragment newInstance() {

        Bundle args = new Bundle();

        Location_Fragment fragment = new Location_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_location__fragment, container, false);

        new getDestionationRestaurant().execute();
        binding.mvLocation.onCreate(savedInstanceState);

        platformPositioningProvider = new PlatformPositioningProvider(getActivity());
        platformPositioningProvider.startLocating(new PlatformPositioningProvider.PlatformLocationListener() {
            @Override
            public void onLocationUpdated(Location location) {
                if (location != null) {
                    current = new GeoCoordinates(location.getLatitude(), location.getLongitude());
                    latitue = location.getLatitude() +"";
                    longitude = location.getLongitude()+"";
                    handleAndroidPermissions();
                    platformPositioningProvider.stopLocating();
                    flag= true;
                }
            }
        });

        binding.btnDirection.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(flag)
                    addRouteButtonClicked(v);
                else Toast.makeText(getContext(), "please wait a minute...", Toast.LENGTH_LONG).show();;
            }
        });

        binding.mvLocation.setOnReadyListener(new MapView.OnReadyListener() {
            @Override
            public void onMapViewReady() {
                //handleAndroidPermissions();
                Log.d(TAG, "HERE Rendering Engine attached.");
            }
        });
        getWeather_Container();



        return binding.getRoot();
    }

    private void getWeather_Container() {
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
                    //binding.weatherIcon.setTypeface(weatherFont);

                    String[] jsonData = getJONReponsed();

//                    binding.cityField.setText(jsonData[0]);
//                    binding.detailsField.setText(jsonData[1]);
                    binding.tempField.setText(jsonData[2]);
//                    binding.humidityField.setText(getString(R.string.humidity) +jsonData[3]);
//                    binding.pressureField.setText("Pressure: " + jsonData[4]);
//                    binding.updateOnField.setText(jsonData[5]);
                    //binding.weatherIcon.setText(Html.fromHtml(jsonData[6]));
                }
            }
        });

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
                String temperature= String.format("%.0f", temp) + "°C";
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

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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

    private void handleAndroidPermissions() {

        Toast.makeText(getActivity(), "wait a minute...", Toast.LENGTH_LONG).show();
        permissionsRequestor = new PermissionsRequestor(getActivity());
        permissionsRequestor.request(new PermissionsRequestor.ResultListener() {

            @Override
            public void permissionsGranted() {
                loadMapScene();
            }


            @Override
            public void permissionsDenied() {

                Log.e(TAG, "Permissions denied by user.");
            }
        });
    }

    private void loadMapScene() {
        binding.mvLocation.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    mapObjectsExample = new MapObjects(getContext(), binding.mvLocation, current, geoCoordinatesList);
                    // mapObjectsExample.showMapPolyline();
                } else {
                    Log.d(TAG, "onLoadScene failed: " + mapError.toString());
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsRequestor.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mvLocation.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mvLocation.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mvLocation.onDestroy();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        // update location
        float lat = (float) location.getLatitude();
        float lng = (float) location.getLongitude();
        current = new GeoCoordinates(lat, lng);
    }


    public void addRouteButtonClicked(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mapObjectsExample.addRoute();
        }
    }


    public void clearMapButtonClicked(View view) {
        mapObjectsExample.cleanMap();
    }

    public class getDestionationRestaurant extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.processBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(linkURL);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    // trả về chuỗi ở link
                    result += (char) byteCharacter;
                }
//                binding.tvShow.setText(result);
//                Toast.makeText(getBaseContext(),result,Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }



            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            binding.processBar.setVisibility(View.GONE);
            getJsonArray();

        }

        private void getJsonArray() {

            resDesList = new ArrayList<>();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    double lat = object.getDouble("latitude");
                    double lng = object.getDouble("longitude");
                    resDesList.add(new Location_Object((float) lat, (float) lng));
                    int dem = resDesList.size();
                }
                getGeoList();
                int count = geoCoordinatesList.size();
            } catch (Exception e) {

            }


        }
    }

    private void getGeoList() {
        geoCoordinatesList = new ArrayList<>();
        for (int i = 0; i < resDesList.size(); i++) {
            Location_Object object = resDesList.get(i);
            geoCoordinatesList.add(new GeoCoordinates(object.getLatitude(), object.getLongitude()));
        }
    }


}
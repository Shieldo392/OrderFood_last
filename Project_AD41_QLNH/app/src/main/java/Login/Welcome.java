package Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityWelcomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Home.Home;
import Home.ImageAdapter;
import Home.ImagesSlide;


public class Welcome extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    private List<ImagesSlide> list_welcome;
    private ImageAdapter imageAdapter;
    int postion_current = 0;
    Timer timer;
    int dem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(Welcome.this, R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new getImage().execute();

        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Home.class);
                startActivity(intent);
            }
        });
        //createSlide();



    }
    public void createSlide(){
        //list_welcome = InitData.LIST_WELCOME();
        imageAdapter = new ImageAdapter(this,list_welcome);
        binding.vpgWelCome.setAdapter(imageAdapter);
        createSlideShow();
    }
    private void createSlideShow(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(postion_current == list_welcome.size())
                    postion_current = 0;
                binding.vpgWelCome.setCurrentItem(postion_current++, true);

            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);
    }
    class getImage extends AsyncTask<Void, Void, Void>{
        String url_welcome = DeFile.URL_WELCOME;
        String result_wel = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.prWelcome.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(url_welcome);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    // trả về chuỗi ở link
                    result_wel += (char) byteCharacter;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            binding.prWelcome.setVisibility(View.GONE);
            getJSON();
            createSlide();
        }

        private void getJSON(){
            list_welcome = new ArrayList<ImagesSlide>();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result_wel);
                for(int i = 0; i< jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String src = object.getString("src");
                    int id = object.getInt("id");
                    list_welcome.add(new ImagesSlide(src, id));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
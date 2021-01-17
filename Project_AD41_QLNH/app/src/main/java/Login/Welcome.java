package Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityWelcomeBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Home.Home;
import Home.ImageAdapter;
import data.AdversitedObject;
import data.remote.ApiUtils;
import data.remote.SOService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Welcome extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    private List<AdversitedObject> list_welcome;
    private ImageAdapter imageAdapter;
    int postion_current = 0;
    Timer timer;
    int dem = 0;
    SOService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(Welcome.this, R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        new getImage().execute();
        mService= ApiUtils.getSOService();

        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Home.class);
                startActivity(intent);
            }
        });
        loadAnswer();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
        

    }

    private void loadAnswer() {
        mService.getAnswers_wel().enqueue(new Callback<List<AdversitedObject>>() {
            @Override
            public void onResponse(Call<List<AdversitedObject>> call, Response<List<AdversitedObject>> response) {
                if(response.isSuccessful()){
                    list_welcome = response.body();
                    createSlide();
                }
            }

            @Override
            public void onFailure(Call<List<AdversitedObject>> call, Throwable t) {

            }
        });
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



}
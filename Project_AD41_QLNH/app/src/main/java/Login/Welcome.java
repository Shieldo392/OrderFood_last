package Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.project_ad41_qlnh.InitData;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityWelcomeBinding;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Home.Home;
import Home.ImagesSlide;
import Home.ImageAdapter;


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

        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Home.class);
                startActivity(intent);
            }
        });
        createSlide();



    }
    public void createSlide(){
        list_welcome = InitData.LIST_WELCOME();
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
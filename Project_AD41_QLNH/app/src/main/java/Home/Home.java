package Home;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import FoodOject.fragment_menuFood;
import personal.fragment_personal;

public class Home extends AppCompatActivity {
    ActivityHomeBinding binding;
    List<SanPham> sanPhamList;
    SanPhamAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(Home.this, R.layout.activity_home);
        getFragment(Fragment_Home.newInstance());



        binding.designNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.frag_home:
                    getFragment(Fragment_Home.newInstance());
                    break;
                case R.id.frag_location:


                    break;
                case R.id.frag_love:

                    getFragment(fragment_menuFood.newInstance());
                    break;
                case R.id.frag_personal:
                    getFragment(fragment_personal.newInstance());
                    break;
            }
            return true;
        }

    };
    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frHome, fragment).commit();
    }
}
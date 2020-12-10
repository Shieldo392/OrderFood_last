package Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import FoodOject.fragment_menuFood;
import location_file.Location_Fragment;
import payMent.Bill_Order;
import personal.fragment_personal;

public class Home extends AppCompatActivity implements Fragment_Home.OnDataPass {
    ActivityHomeBinding binding;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    int dem = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(Home.this, R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();
        Fragment_Home frag = new Fragment_Home();
        sharedPreferences = getSharedPreferences("ORDER", MODE_PRIVATE) ;
        int check = sharedPreferences.getInt("ORDER_ID", -1);
        if(check == -1){
            sharedPreferences.edit().putInt("ORDER_ID", 0).apply() ;
        }


        binding.designNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        try{

            int _count = getIntent().getIntExtra("_count", 0);
            Bundle bundle = new Bundle();
            bundle.putInt("_bill_count", _count);
            frag = new Fragment_Home();
            frag.setArguments(bundle);
            getFragment(frag.newInstance());

        }catch (Exception e){

        }
        //getFragment(frag.newInstance());
        binding.btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(Bill_Order.newInstance());
            }
        });




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
                    getFragment(Location_Fragment.newInstance());

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 11:
                resultCode = getIntent().getIntExtra("_count", 0);
                Bundle bundle = new Bundle();
                bundle.putInt("_bill_count", resultCode);
                // set Fragmentclass Arguments
                Fragment_Home frag = new Fragment_Home();
                frag.setArguments(bundle);
        }

    }

    @Override
    public void onDataPass(int data) {
        dem = data;
        binding.tvCount.setText(dem+"");
        int count = dem;
        if(count == 0)
            getFragment(Fragment_Home.newInstance());
    }
}
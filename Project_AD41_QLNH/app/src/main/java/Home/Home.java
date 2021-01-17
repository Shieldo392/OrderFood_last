package Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityHomeBinding;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import FoodOject.fragment_menuFood;
import data.FoodObject;
import data.remote.ApiUtils;
import data.remote.SOService;
import location_file.Location_Fragment;
import payMent.Bill_Order;
import payMent.Fragment_History_Bill;
import payMent.ItemBill;
import payMent.SqlHelper;
import personal.fragment_personal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import voucher.Vouchers;

public class Home extends AppCompatActivity implements Fragment_Home.OnDataPass {
    ActivityHomeBinding binding;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    int dem;
    List<FoodObject> list;
    List<String> name_food;
    Fragment_Home frag;
    SqlHelper sqlHelper;
    SOService mService;
    private InterstitialAd mInterstitialAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mService = ApiUtils.getSOService();

        binding = DataBindingUtil.setContentView(Home.this, R.layout.activity_home);
        binding.designNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadAnswer();


        sqlHelper = new SqlHelper(getBaseContext());
        getSoLuong();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });


        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.attvSearch.getText().toString().trim();
                binding.attvSearch.setText("");
                binding.attvSearch.focusSearch(View.FOCUS_DOWN);
                FoodObject food = isFood(name);
                if(food == null){
                    Toast.makeText(getBaseContext(), "Không tìm thấy!", Toast.LENGTH_LONG).show();
                    return;
                }
                openDialog(food);
            }
        });
        //getSearch();
        binding.btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(Bill_Order.newInstance());
            }
        });

        fragmentManager = getSupportFragmentManager();
        frag = new Fragment_Home();
        sharedPreferences = getSharedPreferences("ORDER", MODE_PRIVATE) ;
        int check = sharedPreferences.getInt("ORDER_ID", -1);
        if(check == -1){
            sharedPreferences.edit().putInt("ORDER_ID", 0).apply() ;
        }
        getFragment(frag.newInstance());

    }

    private void loadAnswer() {
        list = new ArrayList<>();
        name_food = new ArrayList<>();
        mService.getAnswers_mainmeals().enqueue(new Callback<List<FoodObject>>() {
            @Override
            public void onResponse(Call<List<FoodObject>> call, Response<List<FoodObject>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    for(int i  =0; i<list.size(); i++){
                        name_food.add(list.get(i).getTenSP());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FoodObject>> call, Throwable t) {

            }
        });
        mService.getAnswers_listfood().enqueue(new Callback<List<FoodObject>>() {
            @Override
            public void onResponse(Call<List<FoodObject>> call, Response<List<FoodObject>> response) {
                if(response.isSuccessful()){
                    for(int i = 0; i<response.body().size(); i++){
                        list.add(response.body().get(i));
                        name_food.add(response.body().get(i).getTenSP());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FoodObject>> call, Throwable t) {

            }
        });
        mService.getAnswers_drinktea().enqueue(new Callback<List<FoodObject>>() {
            @Override
            public void onResponse(Call<List<FoodObject>> call, Response<List<FoodObject>> response) {
                if(response.isSuccessful()){
                    for(int i = 0; i<response.body().size(); i++){
                        list.add(response.body().get(i));
                        name_food.add(response.body().get(i).getTenSP());
                    }
                    getSearch();
                }
            }

            @Override
            public void onFailure(Call<List<FoodObject>> call, Throwable t) {

            }
        });


    }

    public void getSoLuong(){
        int dem = 0;
        if(sqlHelper.getList() != null){
            List<ItemBill> bills = sqlHelper.getList();
            for(int i = 0; i<bills.size(); i++){
                dem+=bills.get(i).getCount();
             }
        }
        binding.tvCount.setText(dem +"");

    }

    public void getSearch(){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, name_food);
        binding.attvSearch.setAdapter(adapter);
        binding.attvSearch.setThreshold(2);
    }

    public FoodObject isFood(String name){
        for(int i = 0; i<list.size(); i++){
            if(list.get(i).getTenSP().contains(name)){
                return list.get(i);
            }
        }
        return null;
    }
    public void openDialog(FoodObject food){
        DialogCustom dialogCustom = new DialogCustom(food);
        dialogCustom.show(getSupportFragmentManager(), "Chi tiết");
    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.frag_home:
                    getFragment(Fragment_Home.newInstance());
                    break;
                case R.id.frag_location:
                    getFragment(Location_Fragment.newInstance());
                    break;
                case R.id.frag_love:
                    getFragment(Vouchers.newInstance());

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

                frag.setArguments(bundle);
        }

    }

    @Override
    public void onDataPass(int data) {
        dem = getSizeList();
        binding.tvCount.setText(dem+"");

    }
    public int getSizeList(){
        int count_bill = 0;
        if(sqlHelper.getList() != null){
            List<ItemBill> bills = sqlHelper.getList();
            for(int i = 0; i<bills.size(); i++){
                count_bill+=bills.get(i).getCount();
            }
        }
        return count_bill;
    }

    @Override
    public void changeFragment(int code) {

        switch (code){
            case 999 :
                getFragment(fragment_menuFood.newInstance());
                break;
            case 111:
                getFragment(Fragment_Home.newInstance());
                binding.tvCount.setText("0");
                break;
            case 222:
                getFragment(Location_Fragment.newInstance());
                break;
            case 333:
                getFragment(Fragment_History_Bill.newInstance());
                break;
            case 010:
                this.finishAffinity();
            case 444:
                getFragment(Bill_Order.newInstance());
                break;
            case 555:
                getFragment(fragment_personal.newInstance());
                break;

        }
    }




}
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

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import FoodOject.Food;
import FoodOject.fragment_menuFood;
import data.FoodObject;
import location_file.Location_Fragment;
import payMent.Bill_Order;
import payMent.ItemBill;
import payMent.SqlHelper;
import personal.fragment_personal;
import voucher.Vouchers;

public class Home extends AppCompatActivity implements Fragment_Home.OnDataPass {
    ActivityHomeBinding binding;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    int dem ;
    int code_defile = DeFile.CODE_LISTFOOD;
    List<Food> list;
    List<String> name_food;
    Fragment_Home frag;
    SqlHelper sqlHelper;
    int code_fragment_home = DeFile.FRAGMENT_HOME_CODE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(Home.this, R.layout.activity_home);
//        new getAPI().execute();
        binding.designNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        sqlHelper = new SqlHelper(getBaseContext());
        //getSoLuong();
        dem = sqlHelper.getList().size();
        binding.tvCount.setText(dem+"");

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

    public void getSoLuong(){
        int dem = 0;
        List<ItemBill> bills = sqlHelper.getList();
        for(int i = 0; i<bills.size(); i++){
            dem+=bills.get(i).getCount();
        }
        binding.tvCount.setText(dem +"");

    }

    public void getSearch(){
        name_food = new ArrayList<>();
        for(int i = 0; i< list.size(); i++){
            String name = list.get(i).getTenSP();
            name_food.add(name);
        }
    }

    public Food isFood(String name){
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
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.frag_home:

                    getFragment(Fragment_Home.newInstance());
                    break;
                case R.id.frag_location:
                    getFragment(Location_Fragment.newInstance());

                    break;
                case R.id.frag_love:
                    //getFragment(fragment_menuFood.newInstance());
                    //getFragment(fragment_weather.newInstance());
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
        if(data == 0)
        {
            dem+=data;
        }else
            dem = data;
        binding.tvCount.setText(dem+"");
        int count = dem;
        if(count == 0)
            getFragment(Fragment_Home.newInstance());
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

        }
    }

//    class getAPI extends AsyncTask<Void, Void, Void>{
//
//        String url_main_meals = DeFile.URL_MAIN_MEALS;
//        String result_main_meal = "";
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            binding.prHome.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                URL url = new URL(url_main_meals);
//                URLConnection connection = url.openConnection();
//                InputStream is = connection.getInputStream();
//
//                int byteCharacter;
//                while ((byteCharacter = is.read()) != -1) {
//                    // trả về chuỗi ở link
//                    result_main_meal += (char) byteCharacter;
//                }
////                binding.tvShow.setText(result);
////                Toast.makeText(getBaseContext(),result,Toast.LENGTH_LONG).show();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            binding.prHome.setVisibility(View.GONE);
//            getJSON();
//
//
//
//
//
//            Bundle bundle = new Bundle();
//            int dem = Integer.valueOf(binding.tvCount.getText().toString());
//            bundle.putInt("_soLuong", dem);
//
//            getSearch();
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, name_food);
//            binding.attvSearch.setAdapter(adapter);
//            binding.attvSearch.setThreshold(2);
//            binding.btnSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String name = binding.attvSearch.getText().toString().trim();
//                    Food food = isFood(name);
//                    if(food == null){
//                        Toast.makeText(getBaseContext(), "Không tìm thấy!", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    openDialog(food);
//                }
//            });
//
//        }
//        public void getJSON(){
//            list = new ArrayList<Food>();
//            JSONArray jsonArray_main_meal = null;
//            try {
//                jsonArray_main_meal = new JSONArray(result_main_meal);
//                for(int i = 0; i<jsonArray_main_meal.length(); i++){
//                    JSONObject object = jsonArray_main_meal.getJSONObject(i);
//                    int id = object.getInt("id");
//                    String tenSP = object.getString("tenSP");
//                    String moTa = object.getString("moTa");
//                    int giaBan = object.getInt("giaBan");
//                    String src = object.getString("src");
//                    float rating = (float) object.getDouble("rating");
//                    list.add(new Food(id, tenSP, moTa, giaBan, src, rating));
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }



}
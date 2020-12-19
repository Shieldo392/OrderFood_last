package FoodOject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityFragmentMenuFoodBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import Home.Fragment_Home;
import Home.onItem_SanPhamClick;
import payMent.ItemBill;
import payMent.SqlHelper;

public class fragment_menuFood<fragment_menuFoodBinding> extends Fragment {
    Fragment_Home.OnDataPass dataPass;
    ActivityFragmentMenuFoodBinding binding;
    FoodAdapter adapter;
    List<Food> foodList;
    private int count = 0;
    int dem = 0;
    SqlHelper sqlHelper;

    private List<ItemBill> lst_bill = new ArrayList<ItemBill>();


    public static fragment_menuFood newInstance() {
        Bundle args = new Bundle();


        fragment_menuFood fragment = new fragment_menuFood();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getResources();
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_menu_food, container, false);

        new getFoodList().execute();


        return binding.getRoot();
    }

//    private void initData() {
//        foodList = new ArrayList<>();
//        foodList.add(new Food(1, "Thịt bò", "1 đĩa", 120, R.drawable.beef, 4));
//        foodList.add(new Food(2, "Nước lẩu", "1 bát", 50, R.drawable.soup_hot, 5));
//        foodList.add(new Food(3, "Kê gà", "1 đĩa", 150, R.drawable.ke, 4.5f));
//        foodList.add(new Food(4, "Sụn lợn", "1 đĩa", 120, R.drawable.sunrise, 4.5f));
//        foodList.add(new Food(5, "Tôm", "1 đĩa", 120, R.drawable.tom, 5));
//        foodList.add(new Food(6, "Rau lẩu", "1 đĩa", 50, R.drawable.rau, 5.0f));
//        foodList.add(new Food(7, "Tràng lợn", "1 đĩa", 120, R.drawable.trang, 4.5f));
//        foodList.add(new Food(8, "nấm", "1 đĩa", 30, R.drawable.nam, 3.5f));
//        foodList.add(new Food(9, "Ngô chiên", "1 đĩa", 50, R.drawable.ngo, 4));
//        foodList.add(new Food(10, "Khoai lang kén", "1 đĩa", 50, R.drawable.khoai, 4));
//        foodList.add(new Food(11, "Dưa chuột", "1 đĩa", 20, R.drawable.dua, 3.0f));
//        foodList.add(new Food(12, "Xôi chiên", "1 đĩa", 40, R.drawable.xoi, 4.5f));
//
//
//    }
    private void setRC_food_menu(){
        //initData();
        //foodList = DeFile.foodList;
        adapter = new FoodAdapter(foodList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL,false);
        binding.rcMainMeal.setLayoutManager(layoutManager);
        binding.rcMainMeal.setAdapter(adapter);
    }
    public ItemBill checkSanPham(String tenSP){
        getList_Bill();
        for(int i = 0; i<lst_bill.size(); i++){
            if(lst_bill.get(i).getName().equals(tenSP))
                return lst_bill.get(i);
        }
        return null;
    }
    public void getList_Bill(){
        lst_bill = sqlHelper.getList();
    }
    public void PassData(int data, int code){
        dataPass.onDataPass(data);
        dataPass.changeFragment(code);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (Fragment_Home.OnDataPass) context;
    }

    class getFoodList extends AsyncTask<Void, Void, Void>{
        String url_list_food = DeFile.URL_LIST_FOOD;
        String result_list_food = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.prMenu.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(url_list_food);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    // trả về chuỗi ở link
                    result_list_food += (char) byteCharacter;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            binding.prMenu.setVisibility(View.GONE);
            getJSON();
            setRC_food_menu();
            Bundle bundle = getArguments();
            dem = bundle.getInt("_soLuong", 0);
            sqlHelper = new SqlHelper(getContext());

            adapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
                @Override
                public void onButtonClick(Food food) {
                    ItemBill bill = new ItemBill(1,food.getTenSP(), food.getGiaBan(),count, dem);


                    if(checkSanPham(bill.getName()) == null){
                        count = 0;
                        count++;
                        bill.setCount(count);
                        sqlHelper.insert_bill(bill);
                        Toast.makeText(getContext(), "Đã thêm", Toast.LENGTH_LONG).show();

                    }
                    else{
                        int sl = checkSanPham(bill.getName()).getCount();
                        sl++;
                        bill.setCount(sl);

                        sqlHelper.update_bill(bill);
                        Toast.makeText(getContext(), "Đã thêm", Toast.LENGTH_LONG).show();
                    }

                    dem++;
                    PassData(dem, 0);
                }

                @Override
                public void onSanPhamClick(Food food) {

                }
            });



        }
        public void getJSON(){
            foodList = new ArrayList<>();
            JSONArray jsonArray_menuFood = null;
            try {
                jsonArray_menuFood = new JSONArray(result_list_food);
                for (int i = 0; i < jsonArray_menuFood.length(); i++) {
                    JSONObject object = jsonArray_menuFood.getJSONObject(i);
                    int id = object.getInt("id");
                    String tenSP = object.getString("tenSP");
                    String moTa = object.getString("moTa");
                    int giaBan = object.getInt("giaBan");
                    String src = object.getString("src");
                    float rating = (float) object.getDouble("rating");
                    foodList.add(new Food(id, tenSP, moTa, giaBan, src, rating));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
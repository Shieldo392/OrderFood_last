package Home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityFragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import FoodOject.Food;
import payMent.ItemBill;
import payMent.SqlHelper;

public class Fragment_Home extends Fragment {

    OnDataPass dataPass;
    private ActivityFragmentHomeBinding binding;
    private List<ImagesSlide> imageList;
    private List<Food> mainMealList;
    private List<Food> drinktea;
    private SanPhamAdapter sp_adapter;
    private DrinkTeaAdapter teaAdapter;
    private List<Food> foodList;
    private Timer timer;
    private int postion_current;
    private int dem = 0;
    private List<ItemBill> lst_bill = new ArrayList<ItemBill>();
    private int count = 0; // đếm số sản phẩm được đặt
    SqlHelper sqlHelper;



    public static Fragment_Home newInstance() {

        Bundle args = new Bundle();

        Fragment_Home fragment = new Fragment_Home();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment__home, container, false);
        sqlHelper = new SqlHelper(getContext());
        getData p = new getData();
        p.execute();

        binding.tvSeeAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!p.isCancelled())
                    p.cancel(true);
                PassData(dem, DeFile.CODE_LISTFOOD);

            }
        });


        return binding.getRoot();
    }


    public class getData extends AsyncTask<Void, Void, Void> {

        String url_main_meals = DeFile.URL_MAIN_MEALS;
        String result_main_meal = "";
        String url_drink_tea = DeFile.URL_DRINK_TEA;
        String result_drink_tea = "";
        String url_list_food = DeFile.URL_LIST_FOOD;
        String result_list_food = "";
        String url_quangCao = DeFile.URL_ADVISETED;
        String result_quangCao = "";

        @Override
        protected void onPreExecute() {
            binding.prHome.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(isCancelled())
                return null;

            try {
                URL url = new URL(url_main_meals);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    // trả về chuỗi ở link
                    result_main_meal += (char) byteCharacter;
                }
//                binding.tvShow.setText(result);
//                Toast.makeText(getBaseContext(),result,Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(url_drink_tea);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    // trả về chuỗi ở link
                    result_drink_tea += (char) byteCharacter;
                }
//                binding.tvShow.setText(result);
//                Toast.makeText(getBaseContext(),result,Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            try {
                URL url = new URL(url_quangCao);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    // trả về chuỗi ở link
                    result_quangCao += (char) byteCharacter;
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
            super.onPostExecute(aVoid);

            binding.prHome.setVisibility(View.GONE);
            getJSONArray();
            //DeFile.setListFood(foodList);


            initView();
            createSlideShow();
            setRcPopular();
            setRcMilktea();

            sp_adapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
                @Override
                public void onButtonClick(Food food) {

                    ItemBill bill = new ItemBill(1, food.getTenSP(), food.getGiaBan(), count, dem);


                    if (checkSanPham(bill.getName()) == null) {
                        count = 0;
                        count++;
                        bill.setCount(count);
                        sqlHelper.insert_bill(bill);
                        Toast.makeText(getContext(), "Đã thêm", Toast.LENGTH_LONG).show();

                    } else {
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
                    openDialog(food);
                }
            });
            teaAdapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
                @Override
                public void onButtonClick(Food food) {
                    ItemBill bill = new ItemBill(1, food.getTenSP(), food.getGiaBan(), count, dem);

                    if (checkSanPham(bill.getName()) == null) {
                        count = 0;
                        count++;
                        bill.setCount(count);
                        sqlHelper.insert_bill(bill);
                        Toast.makeText(getContext(), "Đã thêm", Toast.LENGTH_LONG).show();
                    } else {
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
                    openDialog(food);
                }
            });




        }

        public void getJSONArray() {
            imageList = new ArrayList<ImagesSlide>();
            JSONArray jsonArray_QuangCao = null;
            try {
                jsonArray_QuangCao = new JSONArray(result_quangCao);
                for (int i = 0; i < jsonArray_QuangCao.length(); i++) {
                    JSONObject object = jsonArray_QuangCao.getJSONObject(i);
                    String src = object.getString("src");
                    int id = object.getInt("id");
                    imageList.add(new ImagesSlide(src, id));


                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            mainMealList = new ArrayList<Food>();
            JSONArray jsonArray_main_meal = null;
            try {
                result_main_meal = decodeUTF8(result_main_meal);
                jsonArray_main_meal = new JSONArray(result_main_meal);
                for (int i = 0; i < jsonArray_main_meal.length(); i++) {
                    JSONObject object = jsonArray_main_meal.getJSONObject(i);
                    int id = object.getInt("id");
                    String tenSP = object.getString("tenSP");
                    String moTa = object.getString("moTa");
                    int giaBan = object.getInt("giaBan");
                    String src = object.getString("src");
                    float rating = (float) object.getDouble("rating");
                    mainMealList.add(new Food(id, tenSP, moTa, giaBan, src, rating));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            drinktea = new ArrayList<>();
            JSONArray jsonArray_drink_tea = null;
            try {
                result_drink_tea = decodeUTF8(result_drink_tea);
                jsonArray_drink_tea = new JSONArray(result_drink_tea);
                for (int i = 0; i < jsonArray_drink_tea.length(); i++) {
                    JSONObject object = jsonArray_drink_tea.getJSONObject(i);
                    int id = object.getInt("id");
                    String tenSP = object.getString("tenSP");
                    String moTa = object.getString("MoTa");
                    int giaBan = object.getInt("giaBan");
                    String src = object.getString("src");
                    float rating = (float) object.getDouble("rating");
                    drinktea.add(new Food(id, tenSP, moTa, giaBan, src, rating));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            foodList = new ArrayList<>();
            JSONArray jsonArray_menuFood = null;
            try {
                result_list_food = decodeUTF8(result_list_food);
                jsonArray_menuFood = new JSONArray(result_list_food);
                for (int i = 0; i < jsonArray_menuFood.length(); i++) {
                    JSONObject object = jsonArray_menuFood.getJSONObject(i);
                    int id = object.getInt("id");
                    String tenSP = object.getString("tenSP");
                    String moTa = object.getString("MoTa");
                    int giaBan = object.getInt("giaBan");
                    String src = object.getString("src");
                    float rating = (float) object.getDouble("rating");
                    foodList.add(new Food(id, tenSP, moTa, giaBan, src, rating));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            DeFile.foodList = foodList;


        }
    }

    public void openDialog(Food food) {
        DialogCustom dialogCustom = new DialogCustom(food);
        dialogCustom.show(getFragmentManager(), "Chi tiết");

    }

    public void setRcPopular() {

        sp_adapter = new SanPhamAdapter(mainMealList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcSanPham.setLayoutManager(layoutManager);
        binding.rcSanPham.setAdapter(sp_adapter);
    }

    public void setRcMilktea() {

        teaAdapter = new DrinkTeaAdapter(drinktea);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcDrink.setLayoutManager(layoutManager);
        binding.rcDrink.setAdapter(teaAdapter);
    }


    private void initView() {
        if(getContext() == null){
            return;
        }
        ImageAdapter imageAdapter = new ImageAdapter(getContext(), imageList);
        binding.vpgImage.setAdapter(imageAdapter);
    }

    private void createSlideShow() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (postion_current == imageList.size())
                    postion_current = 0;
                binding.vpgImage.setCurrentItem(postion_current++, true);
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

    public void getList_Bill() {
        lst_bill = sqlHelper.getList();
    }

    public void PassData(int data, int code) {
        dataPass.onDataPass(data);
        dataPass.changeFragment(code);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (OnDataPass) context;
    }

    public ItemBill checkSanPham(String tenSP) {
        getList_Bill();
        for (int i = 0; i < lst_bill.size(); i++) {
            if (lst_bill.get(i).getName().equals(tenSP))
                return lst_bill.get(i);
        }
        return null;
    }

    public interface OnDataPass {
        void onDataPass(int data);

        void changeFragment(int code);

    }
    public String decodeUTF8(String input){
        try {
            return new String(input.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
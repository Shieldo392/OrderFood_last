package Home;

import android.content.Intent;
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

import com.example.project_ad41_qlnh.InitData;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityFragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Bill.ItemBill;
import Bill.SqlHelper;
import FoodOject.Food;
import payMent.Bill_Order;

public class Fragment_Home extends Fragment {

    private ActivityFragmentHomeBinding binding;
    private  List<ImagesSlide> imageList;
    private  List<Food> mainMealList;
    private  List<Food> drinktea;
    private  SanPhamAdapter sp_adapter;
    private  DrinkTeaAdapter teaAdapter;
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

        initData();

        createSlideShow();


        setRcPopular();
        setRcMilktea();

        sp_adapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
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
                binding.tvCount.setText(dem+"");
            }

            @Override
            public void onSanPhamClick(Food food) {
                openDialog(food);
            }
        });
        teaAdapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
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
                binding.tvCount.setText(dem+"");
            }

            @Override
            public void onSanPhamClick(Food food) {
                openDialog(food);
            }
        });
        binding.btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Bill_Order.class);
                startActivity(intent);
            }
        });



        return binding.getRoot();
    }
    public void openDialog(Food food){
        DialogCustom dialogCustom = new DialogCustom(food);
        dialogCustom.show(getFragmentManager(), "Chi tiết");
    }
    public void setRcPopular(){
        mainMealList = new ArrayList<Food>();
        mainMealList = InitData.MAIN_MEALS();


        sp_adapter = new SanPhamAdapter(mainMealList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcSanPham.setLayoutManager(layoutManager);
        binding.rcSanPham.setAdapter(sp_adapter);
    }
    public void setRcMilktea(){
        drinktea = new ArrayList<Food>();
        drinktea = InitData.DRINKS_TEA();
        teaAdapter= new DrinkTeaAdapter(drinktea);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcDrink.setLayoutManager(layoutManager);
        binding.rcDrink.setAdapter(teaAdapter);
    }

    private void initData() {
        imageList = new ArrayList<ImagesSlide>();
        imageList = InitData.LIST_QUANG_CAO();
        initView();
    }

    private void initView() {
        ImageAdapter imageAdapter = new ImageAdapter(getContext(), imageList);
        binding.vpgImage.setAdapter(imageAdapter);
    }
    private void createSlideShow(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(postion_current == imageList.size())
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

    public void getList_Bill(){
        lst_bill = sqlHelper.getList();
    }

    public ItemBill checkSanPham(String tenSP){
        getList_Bill();
        for(int i = 0; i<lst_bill.size(); i++){
            if(lst_bill.get(i).getName().equals(tenSP))
                return lst_bill.get(i);
        }
        return null;
    }

    public void getFragment(Fragment fragment){

    }

}
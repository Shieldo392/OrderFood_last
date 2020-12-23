package FoodOject;

import android.content.Context;
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

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityFragmentMenuFoodBinding;

import java.util.ArrayList;
import java.util.List;

import Home.DialogCustom;
import Home.Fragment_Home;
import Home.onItem_SanPhamClick;
import data.FoodObject;
import data.remote.ApiUtils;
import data.remote.SOService;
import payMent.ItemBill;
import payMent.SqlHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_menuFood<fragment_menuFoodBinding> extends Fragment {
    Fragment_Home.OnDataPass dataPass;
    ActivityFragmentMenuFoodBinding binding;
    FoodAdapter adapter;
    List<FoodObject> foodList;
    private int count = 0;
    int dem = 0;
    SqlHelper sqlHelper;
    private SOService mService;

    private List<ItemBill> lst_bill;


    public static fragment_menuFood newInstance() {
        Bundle args = new Bundle();


        fragment_menuFood fragment = new fragment_menuFood();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_menu_food, container, false);

        sqlHelper = new SqlHelper(getContext());
        mService = ApiUtils.getSOService();

        loadAnswer();


        return binding.getRoot();
    }

    private void loadAnswer() {
        mService.getAnswers_listfood().enqueue(new Callback<List<FoodObject>>() {
            @Override
            public void onResponse(Call<List<FoodObject>> call, Response<List<FoodObject>> response) {
                if (response.isSuccessful()) {
                    foodList = response.body();
                    setRC_food_menu();
                    adapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
                        @Override
                        public void onButtonClick(FoodObject food) {
                            ItemBill bill = new ItemBill(1, food.getTenSP(), Integer.parseInt(food.getGiaBan()), count, dem);

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
                        public void onSanPhamClick(FoodObject food) {
                            openDialog(food);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<FoodObject>> call, Throwable t) {

            }
        });
    }

    private void openDialog(FoodObject food) {
        DialogCustom dialogCustom = new DialogCustom(food);
        dialogCustom.show(getFragmentManager(), "Chi tiết");
    }


    private void setRC_food_menu() {

        adapter = new FoodAdapter(foodList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.rcMainMeal.setLayoutManager(layoutManager);
        binding.rcMainMeal.setAdapter(adapter);
    }

    public ItemBill checkSanPham(String tenSP) {
        getList_Bill();
        for (int i = 0; i < lst_bill.size(); i++) {
            if (lst_bill.get(i).getName().equals(tenSP))
                return lst_bill.get(i);
        }
        return null;
    }

    public void getList_Bill() {
        lst_bill = new ArrayList<>();
        lst_bill = sqlHelper.getList();
    }

    public void PassData(int data, int code) {
        dataPass.onDataPass(data);
        dataPass.changeFragment(code);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (Fragment_Home.OnDataPass) context;
    }


}
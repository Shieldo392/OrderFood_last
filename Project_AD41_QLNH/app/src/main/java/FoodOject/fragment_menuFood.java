package FoodOject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class fragment_menuFood<fragment_menuFoodBinding> extends Fragment {
    ActivityFragmentMenuFoodBinding binding;
    FoodAdapter adapter;
    List<Food> foodList;


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
        setRC_food_menu();

        return binding.getRoot();
    }

    private void initData() {
        foodList = new ArrayList<>();
        foodList.add(new Food(1, "Thịt bò", "1 đĩa", 120, R.drawable.beef));
        foodList.add(new Food(2, "Nước lẩu", "1 bát", 50, R.drawable.soup_hot));
        foodList.add(new Food(3, "Kê gà", "1 đĩa", 150, R.drawable.ke));
        foodList.add(new Food(4, "Sụn lợn", "1 đĩa", 120, R.drawable.sun));
        foodList.add(new Food(5, "Tôm", "1 đĩa", 120, R.drawable.tom));
        foodList.add(new Food(6, "Rau lẩu", "1 đĩa", 50, R.drawable.rau));
        foodList.add(new Food(7, "Tràng lợn", "1 đĩa", 120, R.drawable.trang));
        foodList.add(new Food(8, "nấm", "1 đĩa", 30, R.drawable.nam));
        foodList.add(new Food(9, "Ngô chiên", "1 đĩa", 50, R.drawable.ngo));
        foodList.add(new Food(10, "Khoai lang kén", "1 đĩa", 50, R.drawable.khoai));
        foodList.add(new Food(11, "Dưa chuột", "1 đĩa", 20, R.drawable.dua));
        foodList.add(new Food(12, "Xôi chiên", "1 đĩa", 40, R.drawable.xoi));


    }
    private void setRC_food_menu(){
        initData();
        adapter = new FoodAdapter(foodList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL,false);
        binding.rcMainMeal.setLayoutManager(layoutManager);
        binding.rcMainMeal.setAdapter(adapter);
    }
}
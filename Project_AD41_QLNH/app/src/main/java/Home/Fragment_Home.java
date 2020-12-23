package Home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import data.AdversitedObject;
import data.FoodObject;
import data.remote.ApiUtils;
import data.remote.SOService;
import payMent.ItemBill;
import payMent.SqlHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {

    OnDataPass dataPass;
    private ActivityFragmentHomeBinding binding;
    private List<AdversitedObject> imageList;
    private List<FoodObject> mainMealList;
    private List<FoodObject> drinktea;
    private SanPhamAdapter sp_adapter;
    private DrinkTeaAdapter teaAdapter;
    private List<FoodObject> foodList;
    private Timer timer;
    private int postion_current;
    private int dem = 0;
    private List<ItemBill> lst_bill = new ArrayList<ItemBill>();
    private int count = 0; // đếm số sản phẩm được đặt
    SqlHelper sqlHelper;
    SOService mService;



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
//        getData p = new getData();
//        p.execute();
        mService = ApiUtils.getSOService();

        binding.tvSeeAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PassData(dem, DeFile.CODE_LISTFOOD);
                onStop();
            }
        });
        loadAnswer();


        return binding.getRoot();
    }

    private void loadAnswer() {
        mService.getAnswers_drinktea().enqueue(new Callback<List<FoodObject>>() {

            @Override
            public void onResponse(Call<List<FoodObject>> call, Response<List<FoodObject>> response) {
                if(response.isSuccessful()){
                    teaAdapter = new DrinkTeaAdapter(response.body());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                    binding.rcDrink.setLayoutManager(layoutManager);
                    binding.rcDrink.setAdapter(teaAdapter);
                    teaAdapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
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
                Log.d("Home", "error loading from API");
            }
        });

        mService.getAnswers_mainmeals().enqueue(new Callback<List<FoodObject>>() {
            @Override
            public void onResponse(Call<List<FoodObject>> call, Response<List<FoodObject>> response) {
                if(response.isSuccessful()){
                    sp_adapter = new SanPhamAdapter(response.body());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                    binding.rcSanPham.setLayoutManager(layoutManager);
                    binding.rcSanPham.setAdapter(sp_adapter);
                    sp_adapter.setOnItem_sanPhamClick(new onItem_SanPhamClick() {
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
                Log.d("Home", "error loading from API");
            }
        });
        mService.getAnswers_adver().enqueue(new Callback<List<AdversitedObject>>() {
            @Override
            public void onResponse(Call<List<AdversitedObject>> call, Response<List<AdversitedObject>> response) {
                if(response.isSuccessful()){
                    if(getContext() == null){
                        return;
                    }
                    ImageAdapter imageAdapter = new ImageAdapter(getContext(), response.body());
                    imageList = response.body();
                    binding.vpgImage.setAdapter(imageAdapter);
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
            }

            @Override
            public void onFailure(Call<List<AdversitedObject>> call, Throwable t) {
                Log.d("Home", "error loading from API");
            }
        });

    }




    public void openDialog(FoodObject food) {
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

}
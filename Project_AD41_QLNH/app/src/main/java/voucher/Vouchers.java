package voucher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityVouchersBinding;

import java.util.List;

import data.VoucherObject;
import data.remote.ApiUtils;
import data.remote.SOService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vouchers extends Fragment {
    ActivityVouchersBinding binding;
    VoucherAdapter adapter;
    List<VoucherObject> vouchersList;
    private SOService mService;

    public static Vouchers newInstance() {
        
        Bundle args = new Bundle();
        
        Vouchers fragment = new Vouchers();
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_vouchers, container, false);
        mService = ApiUtils.getSOService();

        loadAnswer();


        return binding.getRoot();
    }

    private void loadAnswer() {
        mService.getAnswers_voucher().enqueue(new Callback<List<VoucherObject>>() {
            @Override
            public void onResponse(Call<List<VoucherObject>> call, Response<List<VoucherObject>> response) {
                if(response.isSuccessful()){
                    vouchersList = response.body();

                    adapter = new VoucherAdapter(vouchersList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    binding.rcVou.setLayoutManager(layoutManager);
                    binding.rcVou.setAdapter(adapter);

                    adapter.setOnItem_VoucherClick(new OnItem_VoucherClick() {
                        @Override
                        public void onButtonClick(VoucherObject voucherObject) {
                            openDialog_code(voucherObject);
                        }

                        @Override
                        public void onBackGroundClick(VoucherObject voucherObject) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<VoucherObject>> call, Throwable t) {

            }
        });
    }

    public void openDialog_code(VoucherObject object){
        Dialog_Voucher_code voucher_code = new Dialog_Voucher_code(object);
        voucher_code.show(getFragmentManager(), "Mã Giảm giá");
    }



}
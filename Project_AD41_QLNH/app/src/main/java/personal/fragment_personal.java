package personal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.FragmentPersonalBinding;

import Home.Fragment_Home;
import payMent.SqlHelper;
import payMent.User_pro;

public class fragment_personal extends Fragment {
    Fragment_Home.OnDataPass onDataPass;
    User_pro user;
    FragmentPersonalBinding binding;
    SqlHelper sqlHelper;
    int FRAGMENT_LOCATION = DeFile.FRAGMENT_LOCATION_CODE;
    int FRAGMENT_HISBILL = DeFile.FRAGMENT_HIS_BILL;


    public static fragment_personal newInstance() {

        Bundle args = new Bundle();

        fragment_personal fragment = new fragment_personal();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal, container, false);
        sqlHelper = new SqlHelper(getContext());

        binding.imgvAvatar.setImageResource(R.drawable.fast_food);
        get_info();

        binding.updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(user);
            }
        });
        binding.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData(FRAGMENT_LOCATION);
            }
        });

        binding.tvHisOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData(FRAGMENT_HISBILL);
            }
        });

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData(010);
            }
        });

        return binding.getRoot();
    }

    public void get_info(){
        user = sqlHelper.getList_user().get(0);
        binding.tvName.setText(user.getName());
        binding.tvbirthday.setText(user.getBirthday());

    }

    public void openDialog(User_pro user_pro){
        Custom_Info dialogCustom = new Custom_Info(user_pro);
        dialogCustom.show(getFragmentManager(), "Nhập thông tin");

    }
    public void PassData(int code){
        onDataPass.changeFragment(code);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onDataPass = (Fragment_Home.OnDataPass) context;
    }
}

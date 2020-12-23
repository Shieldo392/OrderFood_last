package personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.FragmentPersonalBinding;

import payMent.Custom_User_Information;
import payMent.SqlHelper;
import payMent.User_pro;

public class fragment_personal extends Fragment {
    User_pro user;
    FragmentPersonalBinding binding;
    SqlHelper sqlHelper;


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
//        binding.tvUser.setText("shieldo392");
//        binding.tvName.setText("Trần Đạt");

        binding.updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(user);
            }
        });



        return binding.getRoot();
    }

    public void get_info(){
        user = sqlHelper.getList_user().get(0);
        binding.tvName.setText(user.getName());

    }

    public void openDialog(User_pro user_pro){
        Custom_Info dialogCustom = new Custom_Info(user_pro);
        dialogCustom.show(getFragmentManager(), "Nhập thông tin");

    }
}

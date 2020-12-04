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

import Login.User;

public class fragment_personal extends Fragment {
    User user;
    FragmentPersonalBinding binding;


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

        binding.imgvAvatar.setImageResource(R.drawable.fast_food);
        binding.tvUser.setText("shieldo392");
        binding.tvName.setText("Trần Đạt");


        return binding.getRoot();
    }
}

package payMent;

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
import com.example.project_ad41_qlnh.databinding.ActivityFragmentHistoryBillBinding;

import java.util.ArrayList;
import java.util.List;

public class Fragment_History_Bill extends Fragment {

    List<His_bill> his_bills;
    SqlHelper sqlHelper;
    His_bill_Adapter adapter;
    ActivityFragmentHistoryBillBinding binding;

    public static Fragment_History_Bill newInstance() {
        
        Bundle args = new Bundle();
        
        Fragment_History_Bill fragment = new Fragment_History_Bill();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment__history__bill, container, false);
        sqlHelper = new SqlHelper(getContext());

        getList();

        adapter = new His_bill_Adapter(his_bills);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.hisBills.setLayoutManager(layoutManager);
        binding.hisBills.setAdapter(adapter);

        return binding.getRoot();
    }
    public void getList(){
        his_bills = new ArrayList<>();
        his_bills = sqlHelper.getList_his();
    }
}
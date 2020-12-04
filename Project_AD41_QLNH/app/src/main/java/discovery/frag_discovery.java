package discovery;

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
import com.example.project_ad41_qlnh.databinding.FragmentDiscoveryBinding;

import java.util.ArrayList;
import java.util.List;

public class frag_discovery extends Fragment {

    FragmentDiscoveryBinding binding;
    List<menuItem> menuItemList;
    DisAdapter adapter;

    public static frag_discovery newInstance() {

        Bundle args = new Bundle();

        frag_discovery fragment = new frag_discovery();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discovery, container, false);
        setRCMenu();

        return binding.getRoot();
    }
    public void setRCMenu(){
        initData();
        adapter = new DisAdapter(menuItemList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false );
        binding.rcDiscovery.setLayoutManager(layoutManager);
        binding.rcDiscovery.setAdapter(adapter);
    }
    public void initData(){
        menuItemList = new ArrayList<>();
        menuItemList.add(new menuItem(R.drawable.beer, "Beer and Ancohol"));
        menuItemList.add(new menuItem(R.drawable.serving_dish, "Main Meal"));
        menuItemList.add(new menuItem(R.drawable.fast_food, "Fast Foods"));
        menuItemList.add(new menuItem(R.drawable.distance, "Distance"));
        menuItemList.add(new menuItem(R.drawable.drink, "Milktea"));
        menuItemList.add(new menuItem(R.drawable.bill, "Pay"));

    }
}

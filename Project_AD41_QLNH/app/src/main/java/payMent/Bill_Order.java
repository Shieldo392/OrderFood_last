package payMent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityBillOrderBinding;

import java.util.ArrayList;
import java.util.List;

import Home.Fragment_Home;

public class Bill_Order extends Fragment  {

    Fragment_Home.OnDataPass dataPass;
    int count ;
    List<ItemBill> billList;
    SqlHelper sqlHelper;
    BillAdapter adapter;
    SharedPreferences sharedPreferences;

    ActivityBillOrderBinding binding;

    public static Bill_Order newInstance() {
        
        Bundle args = new Bundle();
        
        Bill_Order fragment = new Bill_Order();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_bill__order, container, false);
        sharedPreferences = getActivity().getSharedPreferences("ORDER", Context.MODE_PRIVATE);
        sqlHelper = new SqlHelper(getContext());
        readData();
        adapter.setOnItemBillClick(new OnItemBillClick() {
            @Override
            public void onButtonAddClick(ItemBill bill) {

                sqlHelper.update_bill(bill);
                count = getSizeList();
                PassData(count);
            }

            @Override
            public void onButtonMinusClick(ItemBill bill) {
                sqlHelper.update_bill(bill);
                count = getSizeList();
                PassData(count);
            }
        });


        binding.btnDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID_BILL = sharedPreferences.getInt("ORDER_ID", -1);
                ID_BILL++;
                setBillList_BillID(ID_BILL);
                sqlHelper.insert_list_bill_his(billList);
                sqlHelper.deleteAll();
                sharedPreferences.edit().putInt("ORDER_ID", ID_BILL).apply();
                Toast.makeText(getContext(), "Hóa đơn của bạn đã được gửi đến nhà hàng, Vui lòng đợi nhà hàng phản hồi", Toast.LENGTH_LONG).show();
                count = getSizeList();
                PassData(count);
            }
        });
        
        return binding.getRoot();
    }
    
  

    public void readData(){
        billList = new ArrayList<>();
        billList = sqlHelper.getList();
        adapter= new BillAdapter(billList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rcBill.setLayoutManager(layoutManager);
        binding.rcBill.setAdapter(adapter);

    }
    public void setBillList_BillID(int bill_id){
        for (int i = 0; i<billList.size(); i++){
            billList.get(i).setId_bill(bill_id);
        }
    }

    public int getSizeList(){
        billList = new ArrayList<>();
        billList = sqlHelper.getList();
        int dem= billList.size();
        return dem;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (Fragment_Home.OnDataPass) context;
    }

    public void PassData(int count){
        dataPass.onDataPass(count);
    }

}
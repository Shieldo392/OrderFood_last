package payMent;

import android.content.Context;
import android.content.SharedPreferences;
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
    List<User_pro> user_pros;

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
//                int soLuong = bill.getCount();
//                soLuong++;
//                bill.setCount(soLuong);

                sqlHelper.update_bill(bill);
                count = getSizeList();

                PassData(count);
            }

            @Override
            public void onButtonMinusClick(ItemBill bill) {
//                int soLuong = bill.getCount();
//                soLuong--;
//                bill.setCount(soLuong);

                sqlHelper.update_bill(bill);
                count = getSizeList();
                PassData(count);
            }
        });


        binding.btnDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_pro user_pro;
                if(user_pros.size()<=0){
                    user_pro = null;
                    openDialog(user_pro);


                }else {
                    user_pro = user_pros.get(0);
                    openDialog(user_pro);
                }
                int ID_BILL = sharedPreferences.getInt("ORDER_ID", -1);
                ID_BILL++;
                setBillList_BillID(ID_BILL);
                sqlHelper.insert_list_bill_his(billList);
                sqlHelper.deleteAll();
                sharedPreferences.edit().putInt("ORDER_ID", ID_BILL).apply();
                count = getSizeList();


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
        user_pros = sqlHelper.getList_user();

    }
    public void setBillList_BillID(int bill_id){
        for (int i = 0; i<billList.size(); i++){
            billList.get(i).setId_bill(bill_id);
        }
    }

    public int getSizeList(){
        int dem = 0;
        billList = new ArrayList<>();
        billList = sqlHelper.getList();
        for(int i = 0; i<billList.size();i++){
            dem+= billList.get(i).getCount();
        }
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
    public void openDialog(User_pro user_pro){
        Custom_User_Information dialogCustom = new Custom_User_Information(user_pro);
        dialogCustom.show(getFragmentManager(), "Nhập thông tin");

    }

}
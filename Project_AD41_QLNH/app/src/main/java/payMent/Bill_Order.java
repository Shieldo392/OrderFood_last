package payMent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityBillOrderBinding;

import java.util.ArrayList;
import java.util.List;

import Bill.ItemBill;
import Bill.SqlHelper;
import Home.Home;

public class Bill_Order extends AppCompatActivity {

    List<ItemBill> billList;
    SqlHelper sqlHelper;
    BillAdapter adapter;
    SharedPreferences sharedPreferences;

    ActivityBillOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill__order);
        sharedPreferences = getSharedPreferences("ORDER", MODE_PRIVATE);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sqlHelper = new SqlHelper(getBaseContext());

        readData();

        adapter.setOnItemBillClick(new OnItemBillClick() {
            @Override
            public void onButtonAddClick(ItemBill bill) {

                sqlHelper.update_bill(bill);

            }

            @Override
            public void onButtonMinusClick(ItemBill bill) {
                sqlHelper.update_bill(bill);
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Home.class);
                startActivity(intent);
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
                Toast.makeText(getBaseContext(), "Hóa đơn của bạn đã được gửi đến nhà hàng, Vui lòng đợi nhà hàng phản hồi", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), Home.class);
                startActivity(intent);


            }
        });

    }

    public void readData(){
        billList = new ArrayList<>();
        billList = sqlHelper.getList();
        adapter= new BillAdapter(billList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rcBill.setLayoutManager(layoutManager);
        binding.rcBill.setAdapter(adapter);

    }
    public void setBillList_BillID(int bill_id){
        for (int i = 0; i<billList.size(); i++){
            billList.get(i).setId_bill(bill_id);
        }
    }
}
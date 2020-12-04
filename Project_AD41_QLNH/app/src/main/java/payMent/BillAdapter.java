package payMent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;

import java.util.List;

import Bill.ItemBill;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewhoderBill> {
    List<ItemBill> billList;
    OnItemBillClick onItemBillClick;
    public void setOnItemBillClick(OnItemBillClick onItemBillClick){
        this.onItemBillClick = onItemBillClick;
    }

    public BillAdapter(List<ItemBill> billList) {
        this.billList = billList;
    }


    @NonNull
    @Override
    public ViewhoderBill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.item_list_bill, parent, false);

        ViewhoderBill viewhoderBill = new ViewhoderBill(view);

        return viewhoderBill;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewhoderBill holder, int position) {
        ItemBill bill = billList.get(position);
        holder.tvTen.setText(bill.getName());
        holder.tvGia.setText(bill.getPrice()+"");
        holder.edtSoLuong.setText(bill.getCount()+"");

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sl = bill.getCount();
                sl ++;
                bill.setCount(sl);
                holder.edtSoLuong.setText(sl+"");
                onItemBillClick.onButtonAddClick(bill);
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sl = bill.getCount();
                sl --;
                bill.setCount(sl);
                holder.edtSoLuong.setText(sl+"");
                onItemBillClick.onButtonMinusClick(bill);
            }
        });


    }

    @Override
    public int getItemCount() {
        return billList.size();
    }


    public class ViewhoderBill extends RecyclerView.ViewHolder {
        TextView tvTen;
        TextView tvGia;
        EditText edtSoLuong;
        ImageButton btnAdd, btnMinus;

        public ViewhoderBill(@NonNull View itemView) {
            super(itemView);
            tvGia = itemView.findViewById(R.id.tvGiaSP);
            tvTen = itemView.findViewById(R.id.tvName);
            edtSoLuong = itemView.findViewById(R.id.edtSL);

            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnMinus = itemView.findViewById(R.id.btnMinus);

        }
    }
}

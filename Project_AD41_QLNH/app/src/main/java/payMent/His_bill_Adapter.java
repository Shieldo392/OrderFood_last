package payMent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;

import java.util.List;

public class His_bill_Adapter extends RecyclerView.Adapter<His_bill_Adapter.ViewHoder> {
    List<His_bill> his_bills;

    public His_bill_Adapter(List<His_bill> his_bills) {
        this.his_bills = his_bills;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.item_history_bill, parent, false);

        ViewHoder viewHoder = new ViewHoder(view);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        His_bill his_bill = his_bills.get(position);

        holder.tvTenHD.setText(his_bill.getMaHD()+"");
        holder.tvNgayNhap.setText(his_bill.getNgayNhap());
        holder.tvTongTien.setText(his_bill.TongTien()+" K vnđ");


    }

    @Override
    public int getItemCount() {
        return his_bills.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        TextView tvTenHD;
        TextView tvNgayNhap;
        TextView tvTongTien;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvNgayNhap = itemView.findViewById(R.id.tvNgayNhap);
            tvTenHD = itemView.findViewById(R.id.tvTenHD);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);

        }
    }
}

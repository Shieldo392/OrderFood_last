package voucher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHoder> {
    List<VoucherObject> list;
    OnItem_VoucherClick onItem_VoucherClick;

    public void setOnItem_VoucherClick(OnItem_VoucherClick onItemVoucherClick){
        this.onItem_VoucherClick = onItemVoucherClick;
    }

    public VoucherAdapter(List<VoucherObject> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VoucherAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.item_voucher, parent, false );

        ViewHoder viewHoder = new ViewHoder(view);

        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.ViewHoder holder, int position) {
        VoucherObject object = list.get(position);

        Picasso.get().load(object.getSrc()).placeholder(R.drawable.server_1).into(holder.img_voucher);
        holder.tvVoucher.setText(object.getDescr());

        holder.btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem_VoucherClick.onButtonClick(object);
            }
        });
        holder.rnBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem_VoucherClick.onBackGroundClick(object);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHoder  extends RecyclerView.ViewHolder{
        ImageView img_voucher;
        TextView tvVoucher;
        Button btnGetCode;
        LinearLayout rnBackground;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            img_voucher = itemView.findViewById(R.id.image_voucher);
            tvVoucher = itemView.findViewById(R.id.tvNameVoucher);
            btnGetCode = itemView.findViewById(R.id.btnGetCode);
            rnBackground = itemView.findViewById(R.id.backGround_voucher);
        }
    }
}

package Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import FoodOject.Food;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.Viewhoder> {
    List<Food> foodList;
    onItem_SanPhamClick onItem_sanPhamClick;
    public void setOnItem_sanPhamClick(onItem_SanPhamClick onItem_sanPhamClick){
        this.onItem_sanPhamClick = onItem_sanPhamClick;
    }


    public SanPhamAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public SanPhamAdapter.Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sanpham_recycler, parent, false);

        Viewhoder viewhoder = new Viewhoder(view);
        return viewhoder;

    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.Viewhoder holder, int position) {

        Food food = foodList.get(position);
        Picasso.get().load(food.getImgSrc()).placeholder(R.drawable.server_1).into(holder.imvSP);
        //holder.imvSP.setImageResource(food.getImgSrc());
        holder.tvTenSP.setText(food.getTenSP());
        holder.tvGiaSP.setText(food.getGiaBan()+"K VND");
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem_sanPhamClick.onButtonClick(food);
            }
        });
        holder.lnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem_sanPhamClick.onSanPhamClick(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public class Viewhoder extends RecyclerView.ViewHolder {
        ImageView imvSP;
        TextView tvTenSP;
        TextView tvGiaSP;
        ImageButton btnAdd;
        LinearLayout lnSanPham;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            imvSP = itemView.findViewById(R.id.imvSp);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            lnSanPham = itemView.findViewById(R.id.lnSanPham);
        }
    }
}

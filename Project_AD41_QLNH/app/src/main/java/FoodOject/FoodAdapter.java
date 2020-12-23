package FoodOject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Home.onItem_SanPhamClick;
import data.FoodObject;
import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.Viewhoder>  {
    List<FoodObject> foodList;
    onItem_SanPhamClick onItem_sanPhamClick;
    public void setOnItem_sanPhamClick(onItem_SanPhamClick onItem_sanPhamClick){
        this.onItem_sanPhamClick = onItem_sanPhamClick;
    }

    public FoodAdapter(List<FoodObject> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodAdapter.Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_listfood, parent, false);

        Viewhoder viewhoder = new Viewhoder(view);




        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.Viewhoder holder, int position) {

        FoodObject food= foodList.get(position);



        Picasso.get().load(food.getSrc())
                .placeholder(R.drawable.server_1)
                .into(holder.img_anh);


        holder.tvTenSP.setText(food.getTenSP());
        holder.tvMoTa.setText(food.getMoTa());
        holder.tvGiaSP.setText(food.getGiaBan()+"k VND");
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem_sanPhamClick.onButtonClick(food);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }



    class Viewhoder extends RecyclerView.ViewHolder{
        TextView tvTenSP;
        TextView tvGiaSP;
        TextView tvMoTa;
        CircleImageView img_anh;
        ImageButton btnAdd;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            img_anh = itemView.findViewById(R.id.img_anh);
            btnAdd = itemView.findViewById(R.id.btn_item_add);

        }
    }
}

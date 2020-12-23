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
import data.FoodObject;

public class DrinkTeaAdapter extends RecyclerView.Adapter<DrinkTeaAdapter.Viewhohder1> {
    List<FoodObject> sanPhamList;
    onItem_SanPhamClick onItem_sanPhamClick;
    public void setOnItem_sanPhamClick(onItem_SanPhamClick onItem_sanPhamClick){
        this.onItem_sanPhamClick = onItem_sanPhamClick;
    }

    public DrinkTeaAdapter(List<FoodObject> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public Viewhohder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_drink_home, parent, false);
        Viewhohder1 viewhoder = new Viewhohder1(view);

        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhohder1 holder, int position) {
        FoodObject sanPham = sanPhamList.get(position);
        Picasso.get().load(sanPham.getSrc()).placeholder(R.drawable.server_1).into(holder.img_drink);
        //holder.img_drink.setImageResource(sanPham.getImgSrc());
        holder.tvDrinkName.setText(sanPham.getTenSP());
        holder.tvDrinkPrice.setText(sanPham.getGiaBan()+"k VND");

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem_sanPhamClick.onButtonClick(sanPham);
            }
        });
        holder.lnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem_sanPhamClick.onSanPhamClick(sanPham);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }


    public class Viewhohder1 extends RecyclerView.ViewHolder {
        ImageView img_drink;
        TextView tvDrinkName;
        TextView tvDrinkPrice;
        ImageButton btnAdd;
        LinearLayout lnDrink;

        public Viewhohder1(@NonNull View itemView) {
            super(itemView);
            img_drink = itemView.findViewById(R.id.img_drink);
            tvDrinkName = itemView.findViewById(R.id.tvDrinkName);
            tvDrinkPrice = itemView.findViewById(R.id.tvDrinkPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            lnDrink = itemView.findViewById(R.id.lnDrink);
        }
    }
}

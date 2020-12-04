package FoodOject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.Viewhoder> {
    List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
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

        Food food = foodList.get(position);


//        Bitmap bitmap;
//        bitmap = BitmapFactory.decodeResource(Resources.getSystem(), food.getImgSrc());
//        Bitmap circularBitmap = ImageBorder.getRound(bitmap, 100);
//        holder.img_anh.setImageBitmap(circularBitmap);
        holder.img_anh.setImageResource(food.getImgSrc());


        holder.tvTenSP.setText(food.getTenSP());
        holder.tvMoTa.setText(food.getMoTa());
        holder.tvGiaSP.setText(food.getGiaBan()+"k VND");
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

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            img_anh = itemView.findViewById(R.id.img_anh);

        }
    }
}

package discovery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.R;

import java.util.List;

public class DisAdapter extends RecyclerView.Adapter<DisAdapter.Viewhoder> {
    List<menuItem> menuItemList;

    public DisAdapter(List<menuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    @NonNull
    @Override
    public DisAdapter.Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_discovery, parent, false);
        Viewhoder viewhoder = new Viewhoder(view);


        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisAdapter.Viewhoder holder, int position) {
        menuItem item = menuItemList.get(position);
        holder.tvDis.setText(item.getTen());
        holder.imvDis.setImageResource(item.getImvSrc());

    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }
    class Viewhoder extends RecyclerView.ViewHolder{
        ImageView imvDis;
        TextView tvDis;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            imvDis = itemView.findViewById(R.id.imvDis);
            tvDis = itemView.findViewById(R.id.tvDis);
        }
    }
}

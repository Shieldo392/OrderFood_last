package Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.project_ad41_qlnh.R;

import FoodOject.Food;

public class DialogCustom extends AppCompatDialogFragment {
    Food food;
    ImageView img;
    TextView tvTenSP;
    TextView tvMoTa;
    TextView tvGiaBan;

    public DialogCustom(Food food) {
        this.food = food;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.chitiet_sanpham, null);

        builder.setView(view).setTitle("Chi tiết").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        img = view.findViewById(R.id.img_anh);
        tvTenSP = view.findViewById(R.id.tvTenSP);
        tvMoTa = view.findViewById(R.id.tvMoTa);
        tvGiaBan = view.findViewById(R.id.tvGiaSP);

        img.setImageResource(food.getImgSrc());
        tvTenSP.setText(food.getTenSP());
        tvMoTa.setText(food.getMoTa());
        tvGiaBan.setText(food.getGiaBan()+"");

        return builder.create();
    }
}

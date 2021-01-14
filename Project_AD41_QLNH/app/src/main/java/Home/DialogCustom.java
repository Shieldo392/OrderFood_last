package Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.project_ad41_qlnh.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import data.FoodObject;
import payMent.ItemBill;
import payMent.SqlHelper;

public class DialogCustom extends AppCompatDialogFragment {
    FoodObject food;
    ImageView img;
    TextView tvTenSP;
    TextView tvMoTa;
    TextView tvGiaBan;
    RatingBar rtbRating;
    ImageButton imbAdd;
    private List<ItemBill> lst_bill = new ArrayList<ItemBill>();
    SqlHelper sqlHelper;
    Fragment_Home.OnDataPass dataPass;
    int count = 0;
    int dem = 0;

    public DialogCustom(FoodObject food) {
        this.food = food;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.chitiet_sanpham, null);
        sqlHelper = new SqlHelper(getContext());
        builder.setView(view).setTitle("Chi tiết").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                food.setRating(rtbRating.getRating() + "");
            }
        });
        img = view.findViewById(R.id.img_anh);
        tvTenSP = view.findViewById(R.id.tvTenSP);
        tvMoTa = view.findViewById(R.id.tvMoTa);
        tvGiaBan = view.findViewById(R.id.tvGiaSP);
        rtbRating = view.findViewById(R.id.rtbRating);
        imbAdd = view.findViewById(R.id.btnAdd);

        Picasso.get().load(food.getSrc()).placeholder(R.mipmap.ic_launcher).into(img);
        //img.setImageResource(food.getImgSrc());
        tvTenSP.setText(food.getTenSP());
        tvMoTa.setText(food.getMoTa());
        tvGiaBan.setText(food.getGiaBan()+"K VND");
        rtbRating.setRating(Float.parseFloat(food.getRating()));
        imbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemBill bill = new ItemBill(1, food.getTenSP(), Integer.parseInt(food.getGiaBan()), count, dem, food.getSrc(), food.getMoTa());


                if (checkSanPham(bill.getName()) == null) {
                    count = 0;
                    count++;
                    bill.setCount(count);
                    sqlHelper.insert_bill(bill);
                    Toast.makeText(getContext(), "Đã thêm", Toast.LENGTH_LONG).show();

                } else {
                    int sl = checkSanPham(bill.getName()).getCount();
                    sl++;
                    bill.setCount(sl);

                    sqlHelper.update_bill(bill);
                    Toast.makeText(getContext(), "Đã thêm", Toast.LENGTH_LONG).show();
                }
                dem = getCount();
                PassData(dem, 0);
            }
        });


        return builder.create();
    }
    public ItemBill checkSanPham(String tenSP) {
        getList_Bill();
        for (int i = 0; i < lst_bill.size(); i++) {
            if (lst_bill.get(i).getName().equals(tenSP))
                return lst_bill.get(i);
        }
        return null;
    }


    public void PassData(int data, int code) {
        dataPass.onDataPass(data);
        dataPass.changeFragment(code);
    }
    public void getList_Bill() {
        lst_bill = sqlHelper.getList();
    }

    public int getCount(){
        getList_Bill();
        int count = 0;
        for(int i = 0; i<lst_bill.size(); i++){
            count += lst_bill.get(i).getCount();
        }
        return count;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (Fragment_Home.OnDataPass) context;
    }
}

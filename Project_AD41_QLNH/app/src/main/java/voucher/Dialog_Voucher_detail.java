package voucher;

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
import com.squareup.picasso.Picasso;

import data.VoucherObject;

public class Dialog_Voucher_detail extends AppCompatDialogFragment {
    VoucherObject object;
    ImageView imgVoucher;
    TextView tvContent;

    public Dialog_Voucher_detail(VoucherObject object) {
        this.object = object;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.detail_voucher, null);
        imgVoucher = view.findViewById(R.id.image_voucher);
        tvContent = view.findViewById(R.id.voucher_content);

        builder.setView(view).setTitle("Chi tiết").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Picasso.get().load(object.getSrc()).placeholder(R.drawable.server_1).into(imgVoucher);
        tvContent.setText(object.getContent());



        return builder.create();
    }
}

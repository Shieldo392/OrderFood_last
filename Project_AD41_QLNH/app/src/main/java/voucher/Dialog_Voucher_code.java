package voucher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.project_ad41_qlnh.R;

import data.VoucherObject;

public class Dialog_Voucher_code extends AppCompatDialogFragment {
    EditText tvCode;
    public VoucherObject object;

    public Dialog_Voucher_code(VoucherObject object) {
        this.object = object;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_code_voucher, null);
        tvCode = view.findViewById(R.id.tvCode_Voucher);
        builder.setView(view).setTitle("Mã Code").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        tvCode.setText(object.getCode());


        return builder.create();
    }
}

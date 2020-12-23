package payMent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;

import java.util.ArrayList;
import java.util.List;

import Home.Fragment_Home;

public class Custom_User_Information extends AppCompatDialogFragment {
    User_pro user;
    EditText edtName, edtPhone, edtBirth, edtAddr;
    SqlHelper sqlHelper;
    Fragment_Home.OnDataPass dataPass;
    SharedPreferences sharedPreferences;
    List<ItemBill> billList;
    int count;


    public Custom_User_Information(User_pro user) {
        this.user = user;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("ORDER", Context.MODE_PRIVATE);
        sqlHelper = new SqlHelper(getContext());
        billList = new ArrayList<>();
        billList = sqlHelper.getList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        sqlHelper = new SqlHelper(getContext());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.thong_tin_user, null);
        edtName = view.findViewById(R.id.edt_name);
        edtBirth = view.findViewById(R.id.edt_birth);
        edtPhone = view.findViewById(R.id.edt_phone);
        edtAddr = view.findViewById(R.id.edt_address);

        if (user != null) {
            edtName.setText(user.getName());
            edtPhone.setText(user.getPhone());
            edtBirth.setText(user.getBirthday());
            edtAddr.setText(user.getAddress());
        }

        builder.setView(view).setTitle("Thông tin").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String birth = edtBirth.getText().toString().trim();
                String addr = edtAddr.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Họ tên không được để trống", Toast.LENGTH_LONG).show();
                    //edtName.findFocus();
                    return;
                }else
                if (phone.isEmpty()) {
                    Toast.makeText(getContext(), "Số điện thoại không được để trống", Toast.LENGTH_LONG).show();
                    //edtName.findFocus();
                    return;
                }else
                if (birth.isEmpty()) {
                    Toast.makeText(getContext(), "Ngày sinh không được để trống", Toast.LENGTH_LONG).show();
                    //edtName.findFocus();
                    return;
                }else
                if (addr.isEmpty()) {
                    Toast.makeText(getContext(), "Địa chỉ không được để trống", Toast.LENGTH_LONG).show();
                    //edtName.findFocus();
                    return;
                }

                if (user == null) {
                    user = new User_pro(1, name, birth, phone, addr);
                    sqlHelper.insert_user(user);
                } else {
                    user.setName(name);
                    user.setPhone(phone);
                    user.setBirthday(birth);
                    user.setAddress(addr);
                    sqlHelper.update_user(user);
                }
                PassData(0, DeFile.FRAGMENT_HOME_CODE);
                Toast.makeText(getContext(), "Hóa đơn của bạn đã được gửi đến nhà hàng, Vui lòng đợi nhà hàng phản hồi", Toast.LENGTH_LONG).show();




            }
        });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (Fragment_Home.OnDataPass) context;
    }

    public void PassData(int count, int code) {
        dataPass.onDataPass(count);
        dataPass.changeFragment(code);
    }

    public void setBillList_BillID(int bill_id) {
        for (int i = 0; i < billList.size(); i++) {
            billList.get(i).setId_bill(bill_id);
        }
    }

    public int getSizeList() {
        billList = new ArrayList<>();
        billList = sqlHelper.getList();
        int dem = billList.size();
        return dem;
    }
}

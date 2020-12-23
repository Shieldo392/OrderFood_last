package personal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.project_ad41_qlnh.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Home.Fragment_Home;
import payMent.ItemBill;
import payMent.SqlHelper;
import payMent.User_pro;

public class Custom_Info extends AppCompatDialogFragment {
    User_pro user;
    TextInputLayout edtName, edtPhone, edtBirth, edtAddr;
    SqlHelper sqlHelper;
    Fragment_Home.OnDataPass dataPass;
    SharedPreferences sharedPreferences;
    List<ItemBill> billList;
    int count;
    final Calendar myCalendar = Calendar.getInstance();


    public Custom_Info(User_pro user) {
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
        edtName = view.findViewById(R.id.name_error);
        edtBirth = view.findViewById(R.id.birt_error);
        edtPhone = view.findViewById(R.id.phone_error);
        edtAddr = view.findViewById(R.id.addr_error);

        if (user != null) {
            edtName.getEditText().setText(user.getName());
            edtPhone.getEditText().setText(user.getPhone());
            edtBirth.getEditText().setText(user.getBirthday());
            edtAddr.getEditText().setText(user.getAddress());
        }

        builder.setView(view).setTitle("Thông tin").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmInput(view);
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edtBirth.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (Fragment_Home.OnDataPass) context;
    }

    public boolean validateName(){
        String name = edtName.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            edtName.setError("Field can't be empty!");
            return false;
        }
        else {
            edtName.setError(null);
            edtName.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateBirth(){
        String birth = edtBirth.getEditText().getText().toString().trim();
        if(birth.isEmpty()){
            edtBirth.setError("Field can't be empty!");
            return false;
        }
        else {
            edtBirth.setError(null);
            edtBirth.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePhone() {
        String phone = edtPhone.getEditText().getText().toString().trim();
        if (phone.isEmpty()) {
            edtPhone.setError("Field can't be empty!");
            return false;
        } else {
            edtPhone.setError(null);
            edtPhone.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateAddress() {
        String adrr = edtAddr.getEditText().getText().toString().trim();
        if (adrr.isEmpty()) {
            edtAddr.setError("Field can't be empty!");
            return false;
        } else {
            edtAddr.setError(null);
            edtAddr.setErrorEnabled(false);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void confirmInput(View v){
        if(!validateName() | !validateBirth() | !validatePhone() |  !validateAddress()){
            v.cancelPendingInputEvents();
            return;
        }
        String name = edtName.getEditText().getText().toString().trim();
        String phone = edtPhone.getEditText().getText().toString().trim();
        String birth = edtBirth.getEditText().getText().toString().trim();
        String addr = edtAddr.getEditText().getText().toString().trim();


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
        Toast.makeText(getContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_LONG).show();

    }



    public int getSizeList() {
        billList = new ArrayList<>();
        billList = sqlHelper.getList();
        int dem = billList.size();
        return dem;
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtBirth.getEditText().setText(sdf.format(myCalendar.getTime()));
    }
}

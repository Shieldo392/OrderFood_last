package personal;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Home.Fragment_Home;
import payMent.ItemBill;
import payMent.SqlHelper;
import payMent.User_pro;

public class Custom_Info extends AppCompatDialogFragment {
    DatePickerDialog datePickerDialog;
    Fragment_Home.OnDataPass onDataPass;
    User_pro user;
    EditText edtName, edtPhone, edtBirth, edtAddr;
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
        edtName = view.findViewById(R.id.edtName);
        edtBirth = view.findViewById(R.id.edtBirth);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddr = view.findViewById(R.id.edtAdrr);

        if (user != null) {
            edtName.setText(user.getName());
            edtPhone.setText(user.getPhone());
            edtBirth.setText(user.getBirthday());
            edtAddr.setText(user.getAddress());
        }

        builder.setView(view).setTitle("Thông tin").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmInput(view);
                PassData(DeFile.FRAGMENT_PERSONAL);
            }
        });

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtBirth.setText(dayOfMonth +"/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        edtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPass = (Fragment_Home.OnDataPass) context;
    }
    public void PassData(int code){
        onDataPass.changeFragment(code);
    }

    public boolean validateName(){
        String name = edtName.getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(getContext(), "Họ tên không để trống!", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            edtName.setError(null);
            return true;
        }
    }

    public boolean validateBirth(){
        String birth = edtBirth.getText().toString().trim();
        if(birth.isEmpty()){
            edtBirth.setError("Field can't be empty!");
            return false;
        }
        else {
            edtBirth.setError(null);
            return true;
        }
    }

    public boolean checkNumBer(String phone){
        Pattern pattern = Pattern.compile(" ((?=.*\\\\D).{10})");
        Matcher matcher = pattern.matcher(phone);
        boolean check = matcher.matches();
        if(phone.length()!= 10 && check == false)
            return false;
        return true;

    }
    public boolean validatePhone() {

        String phone = edtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            Toast.makeText(getContext(), "Số điện thoại không được để trống!", Toast.LENGTH_LONG).show();
            return false;
        } else if(checkNumBer(phone) == false){
            Toast.makeText(getContext(), "Số điện thoại không phù hợp!", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            edtPhone.setError(null);
            return true;
        }
    }

    public boolean validateAddress() {
        String adrr = edtAddr.getText().toString().trim();
        if (adrr.isEmpty()) {
            Toast.makeText(getContext(), "Địa chỉ không được để trống!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            edtAddr.setError(null);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void confirmInput(View v){
        if(!validateName() | !validateBirth() | !validatePhone() |  !validateAddress()){
            Toast.makeText(getContext(), "Thông tin không phù hợp!", Toast.LENGTH_LONG).show();
            return;
        }
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String birth = edtBirth.getText().toString().trim();
        String addr = edtAddr.getText().toString().trim();


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

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        onDataPass = (Fragment_Home.OnDataPass) activity;
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

        edtBirth.setText(sdf.format(myCalendar.getTime()));
    }

}

package voucher;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityVouchersBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import payMent.ItemBill;
import payMent.OnItemBillClick;

public class Vouchers extends Fragment implements OnItemBillClick {
    ActivityVouchersBinding binding;
    VoucherAdapter adapter;
    List<VoucherObject> vouchersList;

    public static Vouchers newInstance() {
        
        Bundle args = new Bundle();
        
        Vouchers fragment = new Vouchers();
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_vouchers, container, false);
        new getVoucher().execute();




        return binding.getRoot();
    }

    @Override
    public void onButtonAddClick(ItemBill bill) {

    }

    @Override
    public void onButtonMinusClick(ItemBill bill) {

    }
    class getVoucher extends AsyncTask<Void, Void, Void>{
        String url_vou = DeFile.URL_VOUCHER;
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.prVoucher.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(url_vou);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                int byteCharacter;
                while ((byteCharacter = is.read()) != -1){
                    result+= (char) byteCharacter;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            binding.prVoucher.setVisibility(View.GONE);
            getJSON();
            adapter = new VoucherAdapter(vouchersList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.rcVou.setLayoutManager(layoutManager);
            binding.rcVou.setAdapter(adapter);

            adapter.setOnItem_VoucherClick(new OnItem_VoucherClick() {
                @Override
                public void onButtonClick(VoucherObject voucherObject) {

                }

                @Override
                public void onBackGroundClick(VoucherObject voucherObject) {

                }
            });
        }


        public void getJSON(){
            JSONArray jsonArray = null;
            vouchersList = new ArrayList<>();
            try {
                jsonArray = new JSONArray(result);
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    int id = object.getInt("id");
                    String src = object.getString("src");
                    String descr = object.getString("descr");
                    String content = object.getString("content");
                    String code = object.getString("code");
                    vouchersList.add(new VoucherObject(id, src, descr, content, code));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
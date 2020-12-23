package Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.project_ad41_qlnh.R;
import com.example.project_ad41_qlnh.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

import Home.Home;


public class Login extends AppCompatActivity implements iLogin {

    ActivityLoginBinding binding;
    String linkUrl = "";
    List<User> userList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(Login.this, R.layout.activity_login);

        //new getData().execute();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void LoginDone(String mess) {

    }

    @Override
    public void LoginFail(String mess) {

    }

//    class getData extends AsyncTask<Void, Void, Void> {
//        String result = "";
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            binding.processBar.setVisibility(View.VISIBLE);
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//                URL url = new URL(linkUrl);
//                URLConnection connection = url.openConnection();
//                InputStream is = connection.getInputStream();
//                int byteCharacter;
//                while ((byteCharacter = is.read()) != -1) {
//                    result += (char) byteCharacter;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            binding.processBar.setVisibility(View.GONE);
//            String user = binding.edtUser.getText().toString();
//            String pass = binding.edtPass.getText().toString();
//            if (check(user, pass)) {
//                Intent intent = new Intent(Login.this, SignUp.class);
//                intent.putExtra("_user", user);
//                intent.putExtra("_pass", pass);
//                startActivity(intent);
//            }
//        }
//
//        public boolean check(String user, String pass) {
//            for (int i = 0; i < userList.size(); i++)
//                if (userList.get(i).getUser().equals(user) && userList.get(i).getPass().equals(pass)) {
//                    return true;
//                }
//
//
//            return false;
//        }
//
//        public void getJsonArray() {
//            try {
//                JSONArray jsonArray = new JSONArray(result);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject object = jsonArray.getJSONObject(i);
//                    String user = object.getString("user");
//                    String pass = object.getString("pass");
//                    userList.add(new User(user, pass));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}